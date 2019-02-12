package qcspider.cli;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class Main
{
    public static final String APPLICATION_NAME = "QC Spider";
    public static final String APPLICATION_VERSION = "0.0.1";

    public static void main(@NotNull String... args) throws IOException
    {
        if (args.length == 0) {
            System.out.println("Welcome to QC Spider.");
            return;
        }
        // TODO: if -template was passed with a path, output a settings template
        // TODO: if -crawl was passed with a path, try to load the settings and crawl.
    }
}
