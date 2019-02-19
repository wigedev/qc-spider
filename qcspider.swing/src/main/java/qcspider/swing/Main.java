package qcspider.swing;

import qcspider.spider.sitedefinition.SiteDefinitionException;
import qcspider.spider.sitedefinition.SiteDefinitionInterface;
import qcspider.swing.userinterface.ConfigurationLoader;
import qcspider.spider.crawl.CrawlRunner;
import qcspider.spider.components.Site;
import qcspider.spider.userinterface.LogLevel;
import qcspider.swing.userinterface.UserInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class Main
{
    /**
     * A reference to the running application
     */
    private static Main                        app;
    private final UserInterface                userInterface;
    private final ArrayList<Site>              sites;
    private final HashMap<String, CrawlRunner> testThreads;

    /**
     * The entry point of the application
     * @param args Command line parameters
     */
    public static void main(String[] args)
    {
        app = new Main();
    }

    private Main()
    {
        this.userInterface = new UserInterface(this);
        this.sites = new ArrayList<>();
        this.testThreads = new HashMap<>(1);
    }

    public void loadConfiguration() throws SiteDefinitionException
    {
        ConfigurationLoader     cl     = new ConfigurationLoader(userInterface);
        SiteDefinitionInterface ci     = cl.getConfigurationResource();
        ArrayList<Site>         sites  = ci.getConfiguration();
        ArrayList<String>       errors = ci.getErrors();
        this.sites.addAll(sites);
        int site_count = sites.size();
        this.userInterface.setOutput("Configuration processed. " + site_count + " sites added.");
        if (errors.size() > 0) {
            this.userInterface.setOutput(errors.size() + " errors found.", LogLevel.ERROR);
            for (String error: errors) {
                this.userInterface.setOutput(error);
            }
        }
    }

    public void runScans()
    {
        this.userInterface.setOutput("Starting scanning.", LogLevel.DEBUG);
        if (null == this.sites) {
            this.userInterface.setOutput("No sites have been defined.");
            return;
        }

        this.userInterface.setOutput("Keep running is true. Starting iteration.", LogLevel.DEBUG);
        for (Site site : sites) {
            CrawlRunner runner = new CrawlRunner(site, this);
            Thread thread = new Thread(runner);
            thread.start();
            this.addThread(runner);
        }
    }

    /**
     * Add a test runner thread to the list
     * @param thread The test running thread
     */
    private synchronized void addThread(CrawlRunner thread)
    {
        this.testThreads.put(thread.getSiteName(), thread);
    }

    public synchronized void removeThread(CrawlRunner thread)
    {
        this.testThreads.remove(thread.getSiteName());
        if (this.testThreads.size() < 1) {
            System.exit(0);
        }
    }
}
