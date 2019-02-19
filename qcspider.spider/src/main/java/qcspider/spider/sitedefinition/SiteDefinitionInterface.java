package qcspider.spider.sitedefinition;

import qcspider.spider.components.Site;

import java.util.ArrayList;

public interface SiteDefinitionInterface
{
    /**
     * Check if the file can be read
     * @return True if the file can be read
     */
    public boolean isAccessible();

    /**
     * Get the configuration settings stored in the configuration resource in the form of an ArrayList of Site objects.
     * @return An ArrayList of Site elements for processing;
     */
    public ArrayList<Site> getConfiguration();

    /**
     * Get an arraylist of errors generated during the parsing of the configuaration file
     */
    public ArrayList<String> getErrors();
}
