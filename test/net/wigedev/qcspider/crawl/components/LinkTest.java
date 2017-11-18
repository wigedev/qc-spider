package net.wigedev.qcspider.crawl.components;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LinkTest
{
    private URL testURL;
    private URL testSource;
    private Link sut;

    @Before
    public void before() throws Exception
    {
        this.testURL = new URL("http://www.test.com");
        this.testSource = new URL("http://www.test.com/test");
        this.sut = new Link(testURL, testSource);
    }

    @Test
    public void canAddSource() throws Exception
    {
        URL testPage2 = new URL("http://www.test.com/test2");
        this.sut.addReference(testPage2);
        ArrayList<URL> test_sources = this.sut.getReferences();
        assertEquals(2, test_sources.size());
        assertEquals(testPage2, test_sources.get(1));
    }

    @Test
    public void whenAddingDuplicateSourceThenDuplicateIgnored() throws Exception
    {
        this.sut.addReference(testSource);
        assertEquals(1, this.sut.getReferences().size());
    }

    @Test
    public void isChecked() throws Exception
    {
        assertFalse(this.sut.isChecked());
    }

    @Test
    public void canChangeStatus() throws Exception
    {
        assertEquals(0, this.sut.getStatusCode());
        this.sut.setStatusCode(200);
        assertEquals(200, this.sut.getStatusCode());
    }

    @Test
    public void canGetURL() throws Exception
    {
        assertEquals(this.testURL.toString(), this.sut.getURL());
    }

    @Test
    public void canSetAndGetStatusMessage() throws Exception
    {
        String testMessage = "Test message";
        this.sut.setStatusMessage(testMessage);
        assertEquals(testMessage, sut.getStatusMessage());
    }

    @Test
    public void whenRedirectionURLSameAsOriginalURLThenNoRedirection() throws Exception
    {
        URL redirectionURL = this.testURL;
        sut.setRedirectURL(redirectionURL);
        assertFalse(sut.wasRedirected());
        assertNull(sut.getRedirectURL());
    }

    @Test
    public void canSetAndGetRedirection() throws Exception
    {
        String redirectionString = this.testURL.toString() + "?redir=1";
        URL redirectionURL = new URL(redirectionString);
        sut.setRedirectURL(redirectionURL);
        assertTrue(sut.wasRedirected());
        assertEquals(redirectionURL, sut.getRedirectURL());
    }

    @Test
    public void canSetAndGetScreenshot() throws Exception
    {
        File screenshotFile = new File("testpath");
        sut.setScreenshot(screenshotFile);
        assertEquals(screenshotFile, sut.getScreenshot());
    }

    @Test
    public void whenScreenshotSetThenAccessTimeSet() throws Exception
    {
        File screenshotFile = new File("testpath");
        assertNull(sut.getAccessTime());
        sut.setScreenshot(screenshotFile);
        assertNotNull(sut.getAccessTime());
    }
}