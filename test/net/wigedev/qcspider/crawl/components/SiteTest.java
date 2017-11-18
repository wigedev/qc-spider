package net.wigedev.qcspider.crawl.components;

import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class SiteTest
{
    private URL testURL;
    private Site sut;

    @Before
    public void before() throws MalformedURLException
    {
        this.testURL = new URL("http://www.example.com/index");
        this.sut = new Site(this.testURL);
    }

    @Test
    public void getURL() throws Exception
    {
        assertEquals("The wrong URL was returned", testURL, sut.getStartURL());
    }

    @Test
    public void getRootDomain()
    {
        assertEquals(this.testURL.getHost(), this.sut.getRootDomain());
    }

    @Test
    public void whenSiteRedirectsNewURLStored() throws MalformedURLException
    {
        assertFalse(sut.wasRedirected());
        URL redirectionURL = new URL("http://www.example.com/index2");
        sut.setRedirectURL(redirectionURL);
        assertTrue(sut.wasRedirected());
        assertEquals(redirectionURL, sut.getRedirectURL());
    }

    @Test
    public void whenSiteNotRedirectedNoNewURLStored() throws MalformedURLException
    {
        assertFalse(sut.wasRedirected());
        URL redirectionURL = this.testURL;
        this.sut.setRedirectURL(redirectionURL);
        assertFalse(sut.wasRedirected());
        assertEquals(this.testURL, sut.getRedirectURL());
    }
}