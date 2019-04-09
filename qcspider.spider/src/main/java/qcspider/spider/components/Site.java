package qcspider.spider.components;

import java.net.URL;

/**
 * The site object represents a single website that is being scanned.
 */
public class Site
{
    /**
     * This is the URL the scan is starting from
     */
    private final URL startURL;
    /**
     * This is the domain of the site. Only links within this domain will be considered valid for crawling.
     */
    private final String rootDomain;
    /**
     * This is the URL the starting request was redirected to.
     */
    private URL redirectURL;

    public Site(URL startURL)
    {
        this.startURL = startURL;
        this.redirectURL = startURL;
        this.rootDomain = this.startURL.getHost();
    }

    /**
     * Get the URL the initial request was sent to
     *
     * @return The redirection URL
     */
    public URL getRedirectURL()
    {
        return redirectURL;
    }

    /**
     * Update the redirect URL with a new URL.
     *
     * @param redirectURL The new URL
     */
    public void setRedirectURL(URL redirectURL)
    {
        if (!this.startURL.toString().equals(redirectURL.toString())) {
            this.redirectURL = redirectURL;
        }
    }

    /**
     * Check if the request was redirected
     *
     * @return True if the request was redirected
     */
    public boolean wasRedirected()
    {
        return !startURL.toString().equals(redirectURL.toString());
    }

    /**
     * Get the starting URL for the crawl
     *
     * @return The starting URL
     */
    public URL getStartURL()
    {
        return startURL;
    }

    /**
     * Get the root domain of the start URL
     *
     * @return The root domain
     */
    public String getRootDomain()
    {
        return rootDomain;
    }
}
