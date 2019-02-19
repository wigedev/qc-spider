package qcspider.spider;

import qcspider.spider.userinterface.UserInterfaceInterface;

/**
 * The controller runs the scans, managing any IO necessary to starting the scans and providing output to the user
 * during the scan process.
 */
public class Controller
{
    private final ConfigurationSettings  settings;
    private final UserInterfaceInterface ui;

    public Controller(UserInterfaceInterface ui, ConfigurationSettings settings)
    {
        this.ui = ui;
        this.settings = settings;
    }
    /**
     * Starts the execution of a scan
     */
    public void runScan()
    {

    }
}
