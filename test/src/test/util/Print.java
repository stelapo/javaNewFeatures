package test.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;

public class Print {
    public static void p(BaseStream baseStream, String title, boolean isChar) {
        p("\n" + title + ":");
        baseStream.iterator().forEachRemaining(i -> System.out.println(!isChar ? i : (char) ((Integer) i).intValue()));
    }

    public static void p(BaseStream baseStream, String title) {
        p(baseStream, title, false);
    }

    public static void p(String s) {
        System.out.println(s);
    }

    public static void p(Optional<String> s) {
        p(s.get());
    }

    public static void p(Object o) { p(o.toString()); }

    public static void p(ProcessHandle.Info i) {
        String formattedString = ("\nCMD: " + i.command().get()).indent(1);
        //p(1 + formattedString);

        formattedString = formattedString
                .concat(("CMD LINE: " + i.commandLine().orElse("")).indent(3));
        //p(3 + formattedString);

        formattedString = formattedString.concat(("Arguments=" + Arrays.stream(i.arguments().orElse(new String[0])).collect(Collectors.joining("-"))).indent(5));
        //p(5 + formattedString);

        formattedString = formattedString.concat(("USER: " + i.user().orElse("")).indent(7));
        p(formattedString);
    }
}
