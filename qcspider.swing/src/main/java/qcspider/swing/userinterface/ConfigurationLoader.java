package qcspider.swing.userinterface;

import qcspider.spider.sitedefinition.SiteDefinitionException;
import qcspider.spider.sitedefinition.AbstractSiteDefinitionFile;
import qcspider.spider.sitedefinition.SiteDefinitionInterface;
import qcspider.spider.sitedefinition.SiteDefinitionTextFile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Handles retrieving the configuration file from the file system
 */
public class ConfigurationLoader
{
    private final UserInterface ui;

    public ConfigurationLoader(UserInterface ui)
    {
        this.ui = ui;
    }

    public SiteDefinitionInterface getConfigurationResource() throws SiteDefinitionException
    {
        return this.promptForConfigurationFile();
    }

    public AbstractSiteDefinitionFile promptForConfigurationFile() throws SiteDefinitionException
    {
        File is;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filenameFilter = new FileNameExtensionFilter("Text config file", "txt");
        chooser.setFileFilter(filenameFilter);
        int return_val = chooser.showOpenDialog(this.ui);
        if (JFileChooser.APPROVE_OPTION == return_val) {
            is = chooser.getSelectedFile();
        } else {
            throw new SiteDefinitionException("No configuration file was specified.");
        }
        return new SiteDefinitionTextFile(is);
    }
}
