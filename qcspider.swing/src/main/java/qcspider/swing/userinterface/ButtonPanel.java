package qcspider.swing.userinterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class ButtonPanel extends JPanel
{
    final JButton setConfigurationButton;
    final JButton startTestingButton;

    ButtonPanel()
    {
        this.setLayout(new FlowLayout());
        setConfigurationButton = new JButton("Load Configuration");
        setConfigurationButton.setEnabled(true);
        this.add(setConfigurationButton);

        startTestingButton = new JButton("Start Tests");
        startTestingButton.setEnabled(false);
        this.add(startTestingButton);
    }

    /**
     * Registers the action listener with the buttons.
     *
     * @param listener The ActionListener
     */
    void registerListener(ActionListener listener)
    {
        setConfigurationButton.addActionListener(listener);
        startTestingButton.addActionListener(listener);
    }

    /**
     * Pass true to enable the start button or false to disable it.
     *
     * @param enabled True if the button should be enabled, false otherwise
     */
    void enableTesting(boolean enabled)
    {
        startTestingButton.setEnabled(enabled);
    }

    /**
     * @param enabled True if the button should be enabled, false otherwise
     */
    public void enableConfiguration(boolean enabled)
    {
        setConfigurationButton.setEnabled(enabled);
    }
}
