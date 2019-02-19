package qcspider.swing.userinterface;

import qcspider.spider.userinterface.LogLevel;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.HashMap;

public class LogPanel extends JPanel
{
    private AbstractDocument doc;

    private static HashMap<LogLevel, SimpleAttributeSet> attributes;

    LogPanel()
    {
        // Set up the attribute set to style text based on level
        attributes = new HashMap<>(5);
        attributes.put(LogLevel.DEBUG, new SimpleAttributeSet());
        StyleConstants.setForeground(attributes.get(LogLevel.DEBUG), Color.gray);
        StyleConstants.setFontSize(attributes.get(LogLevel.DEBUG), 8);
        attributes.put(LogLevel.MESSAGE, new SimpleAttributeSet());
        StyleConstants.setForeground(attributes.get(LogLevel.MESSAGE), Color.black);
        attributes.put(LogLevel.NOTICE, new SimpleAttributeSet());
        StyleConstants.setForeground(attributes.get(LogLevel.NOTICE), Color.red);
        attributes.put(LogLevel.ALERT, new SimpleAttributeSet());
        StyleConstants.setForeground(attributes.get(LogLevel.ALERT), Color.red);
        attributes.put(LogLevel.ERROR, new SimpleAttributeSet());
        StyleConstants.setForeground(attributes.get(LogLevel.ERROR), Color.red);
        StyleConstants.setBold(attributes.get(LogLevel.ERROR), true);

        // Create and configure the text pane
        JTextPane textArea = new JTextPane();
        textArea.setCaretPosition(0);
        textArea.setMargin(new Insets(5, 5, 5, 5));
        textArea.setFocusable(false);
        StyledDocument styledDoc = textArea.getStyledDocument();
        if (styledDoc instanceof AbstractDocument) {
            doc = (AbstractDocument) styledDoc;
        } else {
            System.err.println("The text pane document is not an Abstract Document.");
            System.exit(-1);
        }
        JScrollPane scroll_pane = new JScrollPane(textArea);

        this.setLayout(new BorderLayout());
        this.add(scroll_pane, BorderLayout.CENTER);
    }

    /**
     * Set a message that should be displayed in the log panel.
     *
     * @param message The message to be displayed
     */
    void setOutput(String message)
    {
        setOutput(message, LogLevel.MESSAGE);
    }

    /**
     * Set a message that should be displayed in the log panel, styled based on the specified level.
     * @param message The message to be displayed
     * @param level The level of the message
     */
    void setOutput(String message, LogLevel level)
    {
        try {
            String newline = "\n";
            doc.insertString(doc.getLength(), message + newline, attributes.get(level));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
