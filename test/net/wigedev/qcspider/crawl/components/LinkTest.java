package net.wigedev.qcspider.crawl.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LinkTest
{
    private URL testURL;
    private URL testSource;
    private Link sut;

    @BeforeEach
    void before() throws Exception
    {
        this.testURL = new URL("http://www.test.com");
        this.testSource = new URL("http://www.test.com/test");
        this.sut = new Link(testURL, testSource);
    }

    @Test
    void canAddSource() throws Exception
    {
        URL testPage2 = new URL("http://www.test.com/test2");
        this.sut.addReference(testPage2);
        ArrayList<URL> test_sources = this.sut.getReferences();
        assertEquals(2, test_sources.size());
        assertEquals(testPage2, test_sources.get(1));
    }

    @Test
    void whenAddingDuplicateSourceThenDuplicateIgnored()
    {
        this.sut.addReference(testSource);
        assertEquals(1, this.sut.getReferences().size());
    }

    @Test
    void isChecked()
    {
        assertFalse(this.sut.isChecked());
    }

    @Test
    void canChangeStatus()
    {
        assertEquals(0, this.sut.getStatusCode());
        this.sut.setStatusCode(200);
        assertEquals(200, this.sut.getStatusCode());
    }

    @Test
    void canGetURL()
    {
        assertEquals(this.testURL.toString(), this.sut.getURL());
    }

    @Test
    void canSetAndGetStatusMessage()
    {
        String testMessage = "Test message";
        this.sut.setStatusMessage(testMessage);
        assertEquals(testMessage, sut.getStatusMessage());
    }

    @Test
    void whenRedirectionURLSameAsOriginalURLThenNoRedirection()
    {
        URL redirectionURL = this.testURL;
        sut.setRedirectURL(redirectionURL);
        assertFalse(sut.wasRedirected());
        assertNull(sut.getRedirectURL());
    }

    @Test
    void canSetAndGetRedirection() throws Exception
    {
        String redirectionString = this.testURL.toString() + "?redir=1";
        URL redirectionURL = new URL(redirectionString);
        sut.setRedirectURL(redirectionURL);
        assertTrue(sut.wasRedirected());
        assertEquals(redirectionURL, sut.getRedirectURL());
    }

    @Test
    void canSetAndGetScreenshot()
    {
        File screenshotFile = new File("testpath");
        sut.setScreenshot(screenshotFile);
        assertEquals(screenshotFile, sut.getScreenshot());
    }

    @Test
    void whenScreenshotSetThenAccessTimeSet()
    {
        File screenshotFile = new File("testpath");
        assertNull(sut.getAccessTime());
        sut.setScreenshot(screenshotFile);
        assertNotNull(sut.getAccessTime());
    }
}