package qcspider.swing.userinterface;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import qcspider.spider.sitedefinition.AbstractSiteDefinitionFile;
import qcspider.spider.sitedefinition.SiteDefinitionException;
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

    /**
     * Constructor
     *
     * @param ui The user interface
     */
    @Contract(pure = true)
    public ConfigurationLoader(UserInterface ui)
    {
        this.ui = ui;
    }

    /**
     * Get the configuration file from the file system
     *
     * @return Reference to the configuration file
     *
     * @throws SiteDefinitionException If the configuration file can not be loaded/read/processed
     */
    public SiteDefinitionInterface getConfigurationResource() throws SiteDefinitionException
    {
        return this.promptForConfigurationFile();
    }

    /**
     * Displays the prompt for the configuration file.
     *
     * @return Reference to the site definitions
     *
     * @throws SiteDefinitionException if the definitions do not get loaded properly
     */
    @NotNull
    @Contract(" -> new")
    private AbstractSiteDefinitionFile promptForConfigurationFile() throws SiteDefinitionException
    {
        File is;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filenameFilter = new FileNameExtensionFilter("Text config file", "txt");
        chooser.setFileFilter(filenameFilter);
        int return_val = chooser.showOpenDialog(this.ui);
        if (JFileChooser.APPROVE_OPTION == return_val) {
            is = chooser.getSelectedFile();
        }
        else {
            throw new SiteDefinitionException("No configuration file was specified.");
        }
        return new SiteDefinitionTextFile(is);
    }
}
