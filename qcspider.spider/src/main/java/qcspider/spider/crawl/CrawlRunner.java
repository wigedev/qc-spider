package qcspider.spider.crawl;

import qcspider.spider.components.Site;
import qcspider.spider.userinterface.UserInterfaceInterface;

import java.io.IOException;

/**
 * The crawl runner maintains the thread that the crawl operation runs in. It starts the test process and completion, as
 * well as managing creation of the report.
 */
public class CrawlRunner implements Runnable
{
    private final UserInterfaceInterface app;
    private final Site site;

    public CrawlRunner(Site site, UserInterfaceInterface app)
    {
        this.site = site;
        this.app = app;
    }

    /**
     * Get the name of the site. Generally this will be the root domain.
     * @return The root domain of the site.
     */
    public String getSiteName()
    {
        return site.getRootDomain();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    public void run()
    {
        try {
            CrawlInstance crawl = new CrawlInstance(site);
            crawl.run();
            // TODO: Create report
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
