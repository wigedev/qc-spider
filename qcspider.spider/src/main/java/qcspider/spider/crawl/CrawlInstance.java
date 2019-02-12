package qcspider.spider.crawl;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.firefox.FirefoxDriver;
import qcspider.spider.components.Link;
import qcspider.spider.components.MalformedLink;
import qcspider.spider.components.Site;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;

/**
 * An instance of the crawl of a site being tested.
 */
public class CrawlInstance
{
    /**
     * Reference to the site object
     */
    private Site                  site;
    /**
     * Array of detected links
     */
    private HashMap<String, Link> links;
    /**
     * Array of external links
     */
    private HashMap<String, Link> externalLinks;
    /**
     * The number of errors encountered during the running of the test
     */
    private int                   errorCount;
    /**
     * Reference to the WebDriver
     */
    private WebDriver             driver;
    /**
     * The start time of the test
     */
    private Calendar              startTime;
    /**
     * The end time of the test
     */
    private Calendar              endTime;

    /**
     * @param site The Site being crawled
     * @throws IOException If the first page can't be crawled.
     */
    public CrawlInstance(@NotNull Site site) throws IOException
    {
        this.site = site;
        links = new HashMap<String, Link>(15);
        Link firstLink = new Link(site.getStartURL(), site.getStartURL());
        checkSiteRedirect();
        links.put(firstLink.getURL(), firstLink);
        externalLinks = new HashMap<String, Link>(2);
        this.errorCount = 0;
    }

    /**
     * Execute the testing process
     */
    public void run()
    {
        startTime = Calendar.getInstance();
        startWebDriver();
        //noinspection ForLoopReplaceableByForEach Done this way to prevent concurrent update exception
        for (int i = 0; i < links.size(); i++) {
            Link current_link = this.links.get(i); // TODO: Fix the retrieval method
            processLink(current_link);
            screenshotLink(current_link);
        }
        // Once the links are completed, do the external links
        Collection<Link> externalLinks = this.externalLinks.values();
        for (Link external: externalLinks) {
            processLink(external, false);
            screenshotLink(external);
        }
        // Finish and cleanup
        endTime = Calendar.getInstance();
        driver.quit();
        //TODO: Create the report here?
    }

    public int getErrorCount()
    {
        return errorCount;
    }

    public Site getSite()
    {
        return site;
    }

    public HashMap<String, Link> getLinks()
    {
        return links;
    }

    public HashMap<String, Link> getExternalLinks()
    {
        return externalLinks;
    }

    public Calendar getStartTime()
    {
        return startTime;
    }

    public Calendar getEndTime()
    {
        return endTime;
    }

    private void startWebDriver()
    {
        driver = new FirefoxDriver();
        driver.manage().deleteAllCookies();
        driver.get(site.getStartURL().toString());
        driver.manage().window().maximize();
    }

    private void processLink(Link link)
    {
        processLink(link, true);
    }

    private void processLink(Link link, boolean isInternal)
    {
        // Don't process bad links obviously
        if (link.getURLObject() == null) {
            return;
        }
        // Make sure the link is http or https
        if (!link.getURLObject().getProtocol().equals("http") && !link.getURLObject().getProtocol().equals("https")) {
            link.setStatusCode(1);
            link.setStatusMessage("Protocols other than HTTP and HTTPS are not currently supported.");
            return;
        }
        // Try to process
        try {
            Connection connection = Jsoup.connect(link.getURL());
            Document   doc        = connection.get();
            link.setStatusCode(connection.response().statusCode());
            link.setStatusMessage(connection.response().statusMessage());
            link.setRedirectURL(connection.response().url());
            if (link.getStatusCode() != 200) {
                errorCount ++;
            }
            if (isInternal) {
                // Get the links from the document and add them
                addElements(doc, "a[href]", link);
                // TODO: Add a way to disable/enable these
                //addElements(doc, "img[src]", link);
                //addElements(doc, "link[href]", link);
            }
        } catch (IOException e) {
            link.setStatusCode(1);
            link.setStatusMessage("Unable to connect");
            errorCount++;
        }
    }

    /**
     * Add the elements defined by the css selector provided to the list of links.i
     * @param doc The document containing the elements
     * @param cssSelector The CSS selector code
     * @param reference The current link where the elements are being found
     */
    private void addElements(Document doc, String cssSelector, Link reference)
    {
        Elements els = doc.select(cssSelector);
        for (Element el : els) {
            addLink(el, reference.getURLObject());
        }
    }

    /**
     * Add the link to the array if it hasn't already been added. If it has been added, add the reference to it.
     * @param element The element to add
     * @param reference The page where the link was found
     */
    private void addLink(Element element, URL reference)
    {
        Link link;
        try {
            URL url = new URL(element.attr("abs:href"));
            link = new Link(url, reference);
        } catch (MalformedURLException e) {
            if (element.attr("abs:href").equals("")) {
                return;
            }
            link = new MalformedLink(element.attr("abs:href"), reference);
        }
        if (linkIsSameDomain(link.getURLObject())) {
            addLinkToMap(links, link, reference);
        } else {
            addLinkToMap(externalLinks, link, reference);
        }
    }

    /**
     * Check if the URL passed is in the same domain as the site
     * @param url The URL to check
     * @return True if they are in the same domain
     */
    private boolean linkIsSameDomain(URL url)
    {
        return site.getRootDomain().equals(url.getHost());
    }

    private void addLinkToMap(HashMap<String, Link> map, Link link, URL reference)
    {
        if (map.containsKey(link.getURL())) {
            map.get(link.getURL()).addReference(reference);
        } else {
            map.put(link.getURL(), link);
        }
    }

    /**
     * To save time on future requests, check if the starting URL is being redirected.
     */
    private void checkSiteRedirect() throws IOException
    {
        URL baseURL = site.getStartURL();
        Connection.Response response = Jsoup.connect(baseURL.toString()).followRedirects(true).execute();
        URL redirectURL = response.url();
        if (!baseURL.toString().equals(redirectURL.toString())) {
            site.setRedirectURL(redirectURL);
        }
    }

    /**
     * Take a screenshot of the linked page. All screenshots are taken after navigating to the home page, then to the
     * link URL for consistency, in case the preceeding page affects dynamic content in the requested page.
     * @param link The link to be screenshotted
     */
    private void screenshotLink(Link link)
    {
        if (!link.getURLObject().getProtocol().equals("http") && !link.getURLObject().getProtocol().equals("https")) {
            return;
        }
        driver.get(site.getRedirectURL().toString()); // Go to the home page before navigating to the link page.
        String screenshotURL = link.getURL();
        int hashPos = screenshotURL.indexOf('#');
        if (hashPos > 0) {
            screenshotURL = screenshotURL.substring(0, hashPos);
        }
        driver.get(screenshotURL);
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        link.setScreenshot(scrFile);
    }
}
