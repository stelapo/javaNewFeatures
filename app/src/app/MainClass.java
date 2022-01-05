package app;

import test.Java9;

class MainApp {

    private static System.Logger LOGGER = System.getLogger("MainApp");

    public static void main(String[] args) {
        LOGGER.log(System.Logger.Level.ERROR, "error test");
        LOGGER.log(System.Logger.Level.INFO, "info test");

        try {
            Integer.parseInt("cad");
        } catch (Exception e) {
            LOGGER.log(System.Logger.Level.ERROR, "exception", e);
        }

        Java9 java9 = new Java9();

    }
}