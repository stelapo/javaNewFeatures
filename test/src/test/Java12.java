package test;

import test.util.Print;

import java.util.concurrent.atomic.AtomicReference;

import static test.util.Print.p;

public class Java12 {

    //su String aggiunto metodo indent() per spezzare più linee
    private static void testIndent() {
        String multilineStr = "This is \na \nmultiline string.";

        p("LINES:");
        AtomicReference<Integer> i = new AtomicReference<>(0);
        multilineStr.lines().forEach(s -> Print.p(s.indent(i.getAndSet(i.get() + 4))));
    }

    public static void main(String[] args) {
        testIndent();
    }
}
