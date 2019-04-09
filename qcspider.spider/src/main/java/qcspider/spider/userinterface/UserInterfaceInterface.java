package qcspider.spider.userinterface;

/**
 * The User Interface interface defines a basic interface for user interfaces that will be interacting with the spider.
 * All user interfaces will need to provide similar functionality, from getting the crawl settings, to displaying output
 * to the user.
 */
public interface UserInterfaceInterface
{
    /**
     * Add a message to the UI
     */
    void addMessage(String message);

    /**
     * Add a message to the UI with a specific severity level
     */
    void addMessage(String message, LogLevel level);
}
