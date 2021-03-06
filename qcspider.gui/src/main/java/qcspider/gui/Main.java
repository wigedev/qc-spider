package qcspider.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;

public class Main extends Application
{
    private static final String APPLICATION_NAME = "QC Spider";
    private static final String APPLICATION_VERSION = "0.0.1";
    private static Main app;
    private Stage stage;

    @Contract(pure = true)
    public static Main i()
    {
        return app;
    }

    public Main()
    {
        app = this;
    }

    public void start(Stage primaryStage)
    {
        stage = primaryStage;
        stage.setTitle(APPLICATION_NAME + " v" + APPLICATION_VERSION);
    }

    /**
     * Show the prompt for the settings for the spider.
     */
    public void showSettings()
    {
        //TODO: implement
    }

    /**
     * Show the output of the operation.
     */
    public void showOutput()
    {
        //TODO: implement
    }
}
