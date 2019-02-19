package qcspider.spider.sitedefinition;

import qcspider.spider.components.Site;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A text configuration file contains a simple list of URLS to scan. These files are much simpler than the XML
 * configuration files.
 */
public class SiteDefinitionTextFile extends AbstractSiteDefinitionFile
{
    /**
     * Constructor
     * @param file The file this object represents
     */
    public SiteDefinitionTextFile(File file)
    {
        super(file);
    }

    public ArrayList<Site> getConfiguration()
    {
        ArrayList<Site> results = new ArrayList<>();
        try {
            String urls = new Scanner(this.file).useDelimiter("\\Z").next();
            String[] sites = urls.split(",");
            for (String site : sites) {
                site = site.trim();
                try {
                    URL url = new URL(site);
                    results.add(new Site(url));
                }
                catch (MalformedURLException e) {
                    this.errors.add(site + " is not a valid URL.");
                }
            }
        }
        catch (FileNotFoundException e) {
            this.errors.add(this.file.getPath() + " could not be opened.");
        }
        return results;
    }
}
