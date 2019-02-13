package qcspider.cli;

import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;
import qcspider.cli.options.OptionsBuilder;
import qcspider.spider.appinfo.ApplicationInfo;

public class Main
{
    public static void main(@NotNull String... args)
    {
        Options options = OptionsBuilder.getOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("template")) {
                //TODO: Generate the template in the specified folder
                System.out.println("Generating template in " + line.getOptionValue("template"));
                return;
            }
            if (line.hasOption("crawl")) {
                //TODO: Start the crawl according to the settings in the specified file
                System.out.println("Crawling using settings at " + line.getOptionValue("crawl"));
                return;
            }
            if (line.hasOption("v")) {
                System.out.println(ApplicationInfo.APPLICATION_NAME + " v" + ApplicationInfo.APPLICATION_VERSION);
                return;
            }
            if (line.hasOption("help")) {
                OptionsBuilder.showHelp(options);
                return;
            }
            System.out.println("No valid option was selected.");
            OptionsBuilder.showHelp(options);
        } catch (ParseException e) {
            System.err.println("Parsing of command line options failed. Reason: " + e.getMessage());
            OptionsBuilder.showHelp();
        }
    }
}
