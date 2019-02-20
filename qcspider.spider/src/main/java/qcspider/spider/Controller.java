package qcspider.spider;

import qcspider.spider.components.Site;
import qcspider.spider.crawl.CrawlRunner;
import qcspider.spider.userinterface.LogLevel;
import qcspider.spider.userinterface.UserInterfaceInterface;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The controller runs the scans, managing any IO necessary to starting the scans and providing output to the user
 * during the scan process.
 */
public class Controller
{
    private final ArrayList<Site>              sites;
    private final UserInterfaceInterface       ui;
    private final HashMap<String, CrawlRunner> testThreads;

    public Controller(UserInterfaceInterface ui, ArrayList<Site> sites)
    {
        this.ui = ui;
        this.sites = sites;
        this.testThreads = new HashMap<>();
    }

    /**
     * Run the queued scans
     */
    public void runScans()
    {
        this.ui.addMessage("Starting scanning.", LogLevel.DEBUG);
        if (null == this.sites) {
            this.ui.addMessage("No sites have been defined.");
            return;
        }

        this.ui.addMessage("Keep running is true. Starting iteration.", LogLevel.DEBUG);
        for (Site site : sites) {
            CrawlRunner runner = new CrawlRunner(site, ui);
            Thread      thread = new Thread(runner);
            thread.start();
            this.addThread(runner);
        }
    }

    /**
     * Add a test runner runner to the list
     *
     * @param runner The test running runner
     */
    private synchronized void addThread(CrawlRunner runner)
    {
        this.testThreads.put(runner.getSiteName(), runner);
    }
}
