package net.wigedev.qcspider.crawl.components;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MalformedLinkTest
{
    private String testURL;
    private URL testSource;

    @Before
    public void before() throws Exception
    {
        this.testURL = "hotp://example.co";
        this.testSource = new URL("http://test.com");
    }

    @Test
    public void returnsTheProvidedURL() throws Exception
    {
        MalformedLink sut = new MalformedLink(testURL, testSource);
        assertEquals(this.testURL, sut.getURL());
    }

    @Test
    public void returnsSourceFromConstructor() throws Exception
    {
        MalformedLink sut = new MalformedLink(testURL, testSource);
        ArrayList<URL> test_sources = sut.getReferences();
        assertEquals(1, test_sources.size());
        assertEquals(this.testSource, test_sources.get(0));
    }

    @Test
    public void returnsAddedSource() throws Exception
    {
        MalformedLink sut = new MalformedLink(testURL, testSource);
        URL testSource2 = new URL("http://test2.com");
        sut.addReference(testSource2);
        ArrayList<URL> test_sources = sut.getReferences();
        assertEquals(2, test_sources.size());
        assertEquals(testSource2, test_sources.get(1));
    }
}