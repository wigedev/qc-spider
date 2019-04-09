package qcspider.spider.sitedefinition;

import qcspider.spider.components.Site;

import java.io.File;
import java.util.ArrayList;

abstract public class AbstractSiteDefinitionFile implements SiteDefinitionInterface
{
    final File file;
    final ArrayList<String> errors;

    AbstractSiteDefinitionFile(File file)
    {
        this.file = file;
        this.errors = new ArrayList<>();
    }

    public boolean isAccessible()
    {
        return this.file.canRead();
    }

    public abstract ArrayList<Site> getConfiguration();

    public ArrayList<String> getErrors()
    {
        return errors;
    }
}
