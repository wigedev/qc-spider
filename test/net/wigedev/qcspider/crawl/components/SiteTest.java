package net.wigedev.qcspider.crawl.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class SiteTest
{
    private URL testURL;
    private Site sut;

    @BeforeEach
    void before() throws MalformedURLException
    {
        this.testURL = new URL("http://www.example.com/index");
        this.sut = new Site(this.testURL);
    }

    @Test
    @Disabled
    void getURL()
    {
        //assertEquals("The wrong URL was returned", testURL, sut.getStartURL());
    }

    @Test
    void getRootDomain()
    {
        assertEquals(this.testURL.getHost(), this.sut.getRootDomain());
    }

    @Test
    void whenSiteRedirectsNewURLStored() throws MalformedURLException
    {
        assertFalse(sut.wasRedirected());
        URL redirectionURL = new URL("http://www.example.com/index2");
        sut.setRedirectURL(redirectionURL);
        assertTrue(sut.wasRedirected());
        assertEquals(redirectionURL, sut.getRedirectURL());
    }

    @Test
    void whenSiteNotRedirectedNoNewURLStored()
    {
        assertFalse(sut.wasRedirected());
        URL redirectionURL = this.testURL;
        this.sut.setRedirectURL(redirectionURL);
        assertFalse(sut.wasRedirected());
        assertEquals(this.testURL, sut.getRedirectURL());
    }
}