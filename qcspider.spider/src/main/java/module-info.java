module qcspider.spider {
    requires org.jetbrains.annotations;
    requires selenium.api;
    requires selenium.firefox.driver;
    requires org.jsoup;
    requires qcspider.gui;

    exports qcspider.spider;
    exports qcspider.spider.appinfo;
    exports qcspider.spider.components;
    exports qcspider.spider.crawl;
    exports qcspider.spider.userinterface;
    exports qcspider.spider.sitedefinition;
}