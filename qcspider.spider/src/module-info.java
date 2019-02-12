module qcspider.spider {
    requires org.jetbrains.annotations;
    requires selenium.api;
    requires selenium.firefox.driver;
    requires org.jsoup;
    requires qcspider.cli;

    exports qcspider.spider;
}