package qcspider.spider.components;

import java.net.URL;

/**
 * Malformed link objects represent links that are found which do not point to valid URLs
 */
public class MalformedLink extends Link
{
    /**
     * The "url" the link points to
     */
    private String url;

    /**
     * @param url       The link destination
     * @param reference The page where the link was discovered
     */
    public MalformedLink(String url, URL reference)
    {
        super(null, reference);
        super.setStatusMessage("Invalid URL");
        this.url = url;
    }

    /**
     * Check if the link has already been checked
     *
     * @return True if the link has been checked
     */
    @Override
    public boolean isChecked()
    {
        return true; // Always return true so the system doesn't attempt to follow the link
    }

    /**
     * Get the URL the link points to
     *
     * @return The URL the link pints to
     */
    @Override
    public String getURL()
    {
        return url;
    }

    /**
     * Don't allow the status message to be changed.
     *
     * @param statusMessage The status message
     */
    @Override
    public void setStatusMessage(String statusMessage)
    {
    }
}
