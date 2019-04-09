package qcspider.screenshotreport;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import qcspider.spider.components.Link;
import qcspider.spider.crawl.CrawlInstance;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ScreenshotReportBuilder
{
    private final String                domain;
    private final HashMap<String, Link> links;
    private final HashMap<String, Link> badLinks;
    private final HashMap<String, Link> externalLinks;
    private final int errorCount;
    private final Calendar startTime;
    private final Calendar endTime;
    private final SimpleDateFormat dateFormat;
    private final SimpleDateFormat fileDateFormat;

    /**
     * Constructor
     * @param instance The crawl instance
     */
    public ScreenshotReportBuilder(@NotNull CrawlInstance instance)
    {
        this.domain = instance.getSite().getRootDomain();
        this.links = instance.getLinks();
        this.badLinks = instance.getMalformedLinks();
        this.externalLinks = instance.getExternalLinks();
        this.errorCount = instance.getErrorCount();
        this.startTime = instance.getStartTime();
        this.endTime = instance.getEndTime();
        this.dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.fileDateFormat = new SimpleDateFormat("yyyyMNMdd-HHmmss");
    }

    /**
     * Generate the report
     * @throws IOException if file read or write operations fail
     */
    public void createReport() throws IOException
    {
        File tempFile = this.createTempFile();
        FileWriter fw = new FileWriter(tempFile, true);
        FileOutputStream fos = new FileOutputStream(this.getZipFileName(domain));
        ZipOutputStream zos = new ZipOutputStream(fos);
        fw.append(this.createReportHeader());
        fw.append(this.createSummary());
        fw.append("<h2 id=\"invalid\">Bad Links Report</h2>\n");
        fw.append("<p>The following links were found with invalid URLs.</p>");
        fw.append(this.createBadLinksReport());
        fw.append("<h2 id=\"internal\">Internal Links Report</h2>\n");
        fw.append(this.createLinkReport(zos, true));
        fw.append("<h2 id=\"external\">External Links Report</h2>\n");
        fw.append(this.createLinkReport(zos, false));
        fw.append(this.createReportFooter());
        fw.close();
        this.addToZipFile(tempFile, zos);
        //noinspection ResultOfMethodCallIgnored
        tempFile.delete();
        zos.close();
        fos.close();
    }

    /**
     * Create a temporary file for the report
     * @return Reference to the temporary file
     * @throws IOException if errors happen when creating the file
     */
    @NotNull
    private File createTempFile() throws IOException
    {
        return File.createTempFile("index", ".html");
    }

    /**
     * Generate a name for the zip file that will contain the report
     * @param domain The domain that was tested
     * @return The file name
     */
    private String getZipFileName(String domain)
    {
        domain = domain.replace('.', '_');
        String filename = System.getProperty("user.home") + "/QCS-" + domain + "-" +
                this.fileDateFormat.format(Calendar.getInstance().getTime()) + ".zip";
        System.out.println("Zip filename " + filename);
        return filename;
    }

    /**
     * Create the HTML header for the report
     * @return The html header
     */
    @NotNull
    @Contract(pure = true)
    private String createReportHeader()
    {
        String output = "";
        output += "<html><head><title>" + this.domain + "</title>" +
                "<style type=\"text/css\">" +
                ".linkdiv {margin: 5px; padding: 5px; background: #cccccc;} " +
                "div.alert {border:1px solid #ff0000;} " +
                "span.status {font-size: 90%;}" +
                "span.alert {color: #ff0000;} " +
                "img {padding:1px; border:1px solid #021a40;}" +
                "</style>" +
                "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js\"></script>" +
                "</head><body>";
        output += "<h1>Link Analysis Results for " + this.domain + "</h1>";
        return output + "\n";
    }

    /**
     * Create the summary section of the report
     * @return The summary section text
     */
    private String createSummary()
    {
        String output = "";
        output += "<h2>Summary</h2>\n";
        output += "<p>Testing began: " + dateFormat.format(this.startTime.getTime()) + "<br>\n";
        output += "Testing ended: " + dateFormat.format(this.endTime.getTime()) + "<br>\n";
        output += "Test found " + this.badLinks.size() + " <a href=\"#invalid\">invalid links</a>.<br>\n";
        output += "Test found " + this.links.size() + " <a href=\"#internal\">internal links</a>.<br>\n";
        output += "Test found " + this.externalLinks.size() + " <a href=\"#external\">external links</a>.<br>\n";
        output += this.errorCount + " potential errors were encountered.</p>\n";
        output += "<button id=\"showall\">Show All</button> &nbsp; <button id=\"showissues\">Show Possible Issues</button>";
        return output;
    }

    /**
     * Creates the report about the bad links
     * @return HTML text of the bad links report
     */
    @NotNull
    private String createBadLinksReport()
    {
        if (this.badLinks.size() < 1) {
            return "<p>No bad links were found in the site.</p>\n";
        }
        StringBuilder output = new StringBuilder();
        Collection<Link> links = this.badLinks.values();
        for (Link link : links) {
            output.append("<p><strong>").append(link.getURL()).append("</strong> was seen on:</p>\n<ul>\n");
            for (URL url : link.getReferences()) {
                output.append("<li>").append(url.getPath()).append("</li>\n");
            }
            output.append("</ul>");
        }
        return output.toString();
    }

    /**
     * Create the report section with the information about individual links
     * @param zos The zip output stream
     * @param isInternal True if the report is on the internal links
     * @return The link report HTML code
     */
    @NotNull
    private String createLinkReport(ZipOutputStream zos, boolean isInternal)
    {
        StringBuilder            output = new StringBuilder();
        java.util.Iterator<Link> linkIterator;
        if (isInternal) {
            linkIterator = this.links.values().iterator();
        } else {
            linkIterator = this.externalLinks.values().iterator();
        }
        while (linkIterator.hasNext()) {
            Link link = linkIterator.next();
            String alertclass = "";
            if (200 != link.getStatusCode()) alertclass = " alert";
            output.append("<div class=\"linkdiv").append(alertclass).append("\"><p><strong>").append(
                    link.getURL());
            output.append("<br><span class=\"status").append(alertclass).append("\">");
            output.append(" Status: ").append(link.getStatusCode()).append(" (").append(link.getStatusMessage()).append(
                    ")");
            output.append("</span>");
            output.append("</strong></p>\n");
            Calendar accessTime = link.getAccessTime();
            if (null == accessTime) {
                output.append("<p>Retrieved on: <span style=\"color: red\">Not retrieved.</span></p>\n");
            } else {
                output.append("<p>Retrieved on: ").append(accessTime.getTime()).append("</p>\n");
            }
            if (link.wasRedirected()) {
                output.append("<p>This link URL auto redirects to: ").append(link.getRedirectURL().toString()).append(
                        "</p>\n");
            }
            output.append("<p>This page was linked to from:</p>\n<ul>\n");
            for (URL url : link.getReferences()) {
                output.append("<li>").append(url.toString()).append("</li>\n");
            }
            output.append("</ul>");
            if (null != link.getScreenshot()) {
                try {
                    this.addToZipFile(link.getScreenshot(), zos);
                    output.append("<a href=\"").append(link.getScreenshot().getName()).append("\"><img src=\"").append(
                            link.getScreenshot().getName()).append("\" width=\"50%\"></a>\n");
                } catch (IOException e) {
                    output.append("<p>Unable to save screenshot. ").append(e.getMessage()).append("</p>\n");
                }
                //noinspection ResultOfMethodCallIgnored
                link.getScreenshot().delete();
            }
            output.append("</div>\n");
        }
        return output.toString();
    }

    /**
     * Add the temp file to the zip file
     * @param file The file to be added
     * @param zos The zip output stream
     * @throws IOException If the output operation fails
     */
    private void addToZipFile(File file, @NotNull ZipOutputStream zos) throws IOException
    {
        FileInputStream fis      = new FileInputStream(file);
        ZipEntry        zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        zos.closeEntry();
        fis.close();
    }

    /**
     * Create the footer of the report
     * @return HTML footer string
     */
    @Contract(pure = true)
    private String createReportFooter()
    {
        String output = "";
        output += "<script>\n" +
                "$( \"#showall\" ).click(function() {\n" +
                "  $(\".linkdiv\").show();\n" +
                "});\n" +
                "$( \"#showissues\" ).click(function() {\n" +
                "  $(\".linkdiv\").each(function(index){if (false == $(this).hasClass('alert')) {$(this).hide();}});\n" +
                "});\n" +
                "</script>";
        output += "</body></html>\n";
        return output;
    }
}
