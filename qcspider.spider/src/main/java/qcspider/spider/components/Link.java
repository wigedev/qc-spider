package qcspider.spider.components;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A link object represents the links in the web site.
 */
public class Link
{
    /**
     * The URL of the link
     */
    private URL            url;
    /**
     * The URL requests for the link are redirected to
     */
    private URL            redirectURL;
    /**
     * The status code returned when the link was requested
     * TODO: What code is stored when the link is redirected?
     */
    private int            statusCode;
    /**
     * The status message returned when the link was requested
     * TODO: What message is stored when the link is redirected
     */
    private String         statusMessage;
    /**
     * List of pages the link was found on
     */
    private ArrayList<URL> references;
    /**
     * Screenshot of the page
     */
    private File           screenshot;
    /**
     * The time the page was screenshotted
     */
    private Calendar       accessTime;

    /**
     *
     * @param url The link destination
     * @param reference The page where the link was discovered
     */
    public Link(URL url, URL reference)
    {
        this.url = url;
        this.statusCode = 0;
        this.statusMessage = "Not Requested";
        references = new ArrayList<>();
        references.add(reference);
    }

    /**
     * Add a new reference to the link.
     * @param reference The URL of the page linking here
     */
    public void addReference(URL reference)
    {
        for (URL testPage: references) {
            if (testPage.toString().equals(reference.toString())) {
                return;
            }
        }
        references.add(reference);
    }

    /**
     * Get a list of references to this link
     */
    public ArrayList<URL> getReferences() {
        return references;
    }

    /**
     * Check if the link has already been checked
     * @return True if the link has been checked
     */
    public boolean isChecked()
    {
        return 0 != statusCode;
    }

    /**
     * Set the status code
     * @param statusCode The status code
     */
    public void setStatusCode(int statusCode)
    {
        this.statusCode = statusCode;
    }

    /**
     * Get the status code
     * @return The status code
     */
    public int getStatusCode()
    {
        return statusCode;
    }

    /**
     * Set the status message
     * @param statusMessage The status message
     */
    public void setStatusMessage(String statusMessage)
    {
        this.statusMessage = statusMessage;
    }

    /**
     * Get the status message
     * @return The status message
     */
    public String getStatusMessage()
    {
        return statusMessage;
    }

    /**
     * Set the URL the request was redirected to
     * @param redirectURL The URL the request was redirected to
     */
    public void setRedirectURL(URL redirectURL)
    {
        if (!url.toString().equals(redirectURL.toString())) {
            this.redirectURL = redirectURL;
        }
    }

    /**
     * Get the URL the request was redirected to
     * @return The URL the request was redirected to
     */
    public URL getRedirectURL()
    {
        return redirectURL;
    }

    /**
     * Check if the link was redirected
     */
    public boolean wasRedirected()
    {
        return null != redirectURL;
    }

    /**
     * Get the URL the link points to
     * @return The URL the link pints to
     */
    public String getURL()
    {
        return url.toString();
    }

    public URL getURLObject()
    {
        return url;
    }

    public void setScreenshot(File screenshot)
    {
        this.screenshot = screenshot;
        accessTime = Calendar.getInstance();
    }

    public File getScreenshot()
    {
        return screenshot;
    }

    /**
     * Get the time when the page was accessed to generate the screenshot
     * @return The time the page was accessed
     */
    public Calendar getAccessTime()
    {
        return accessTime;
    }
}
