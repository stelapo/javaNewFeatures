package test;

import test.util.Print;

import static test.util.Print.p;

public class Java11 {

    //su String aggiunto metodo lines() per spezzare più linee
    private static void testLines() {
        String multilineStr = "This is \na \nmultiline string.";

        p("LINES:");
        multilineStr.lines().forEach(Print::p);
    }

    public static void main(String[] args) {
        testLines();
    }
}
