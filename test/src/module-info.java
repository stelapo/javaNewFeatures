module javaNewFeatures {
    requires java.net.http;
    provides java.lang.System.LoggerFinder
            with test.log.CustomLoggerFinder;
    exports test.log;
    exports test;
}