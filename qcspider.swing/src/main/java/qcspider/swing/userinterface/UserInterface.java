package qcspider.swing.userinterface;

import qcspider.spider.appinfo.ApplicationInfo;
import qcspider.spider.sitedefinition.SiteDefinitionException;
import qcspider.spider.userinterface.LogLevel;
import qcspider.spider.userinterface.UserInterfaceInterface;
import qcspider.swing.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInterface extends JFrame implements ActionListener, UserInterfaceInterface
{
    /**
     * Reference to the app itself
     */
    private Main app;
    /**
     * The log panel where output is displayed to the user
     */
    private LogPanel logPanel;
    /**
     * The button panel for the user to click things
     */
    private ButtonPanel buttonPanel;

    public UserInterface(Main app)
    {
        this.app = app;
        this.setTitle(ApplicationInfo.APPLICATION_NAME + " v" + ApplicationInfo.APPLICATION_VERSION);
        this.setSize(350, 260);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        this.logPanel = new LogPanel();
        this.add(logPanel, BorderLayout.CENTER);

        buttonPanel = new ButtonPanel();
        buttonPanel.registerListener(this);
        this.add(buttonPanel, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    /**
     * The actionPerformed method handles all events triggered within the user interface.
     *
     * @param e The event source
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == buttonPanel.setConfigurationButton) {
            try {
                this.app.loadConfiguration();
                // If there is no error, enable the start scan button
                buttonPanel.startTestingButton.setText("Start Testing");
                buttonPanel.enableTesting(true);
            } catch (SiteDefinitionException e1) {
                addMessage(e1.getMessage(), LogLevel.ERROR);
            }
        } else if (e.getSource() == buttonPanel.startTestingButton) {
            buttonPanel.startTestingButton.setText("Stop Testing");
            this.app.runScans();
        }
    }

    /**
     * Sets output to be displayed to the user.
     *
     * @param message The message string to be displayed
     */
    public void addMessage(String message)
    {
       addMessage(message, LogLevel.MESSAGE);
    }

    /**
     * Sets output to be displayed to the user.
     *
     * @param message The message string to be displayed
     * @param level The level of the message
     */
    public void addMessage(String message, LogLevel level)
    {
        logPanel.setOutput(message, level);
    }
}
