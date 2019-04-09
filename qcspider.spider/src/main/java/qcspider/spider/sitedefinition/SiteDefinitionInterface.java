package qcspider.spider.sitedefinition;

import qcspider.spider.components.Site;

import java.util.ArrayList;

public interface SiteDefinitionInterface
{
    /**
     * Check if the file can be read
     * @return True if the file can be read
     */
    boolean isAccessible();

    /**
     * Get the configuration settings stored in the configuration resource in the form of an ArrayList of Site objects.
     * @return An ArrayList of Site elements for processing;
     */
    ArrayList<Site> getConfiguration();

    /**
     * Get an arraylist of errors generated during the parsing of the configuaration file
     */
    ArrayList<String> getErrors();
}
