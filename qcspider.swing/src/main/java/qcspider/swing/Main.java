package qcspider.swing;

import qcspider.spider.Controller;
import qcspider.spider.components.Site;
import qcspider.spider.sitedefinition.SiteDefinitionException;
import qcspider.spider.sitedefinition.SiteDefinitionInterface;
import qcspider.spider.userinterface.LogLevel;
import qcspider.swing.userinterface.ConfigurationLoader;
import qcspider.swing.userinterface.UserInterface;

import java.util.ArrayList;

public class Main
{
    /**
     * A reference to the running application
     */
    private static Main            app;
    private final  UserInterface   userInterface;
    private final  ArrayList<Site> sites;
    private        Controller      controller;

    /**
     * The entry point of the application
     *
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
    }

    public void loadConfiguration() throws SiteDefinitionException
    {
        ConfigurationLoader     cl     = new ConfigurationLoader(userInterface);
        SiteDefinitionInterface ci     = cl.getConfigurationResource();
        ArrayList<Site>         sites  = ci.getConfiguration();
        ArrayList<String>       errors = ci.getErrors();
        this.sites.addAll(sites);
        int site_count = sites.size();
        this.userInterface.addMessage("Configuration processed. " + site_count + " sites added.");
        if (errors.size() > 0) {
            this.userInterface.addMessage(errors.size() + " errors found.", LogLevel.ERROR);
            for (String error : errors) {
                this.userInterface.addMessage(error);
            }
        }
    }

    /**
     * Have the controller run the scans
     */
    public void runScans()
    {
        this.controller = new Controller(userInterface, this.sites);
    }
}
