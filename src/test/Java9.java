package test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;

public class Java9 {


    private static void p(BaseStream baseStream, String title, boolean isChar) {
        p("\n" + title + ":");
        baseStream.iterator().forEachRemaining(i -> System.out.println(!isChar ? i : (char) ((Integer) i).intValue()));
    }

    private static void p(BaseStream baseStream, String title) {
        p(baseStream, title, false);
    }

    private static void p(String s) {
        System.out.println("-" + s + "-");
    }

    private static void p(ProcessHandle.Info i) {
        String formattedString = ("\nCMD: " + i.command().get()).indent(1);
        p(1 + formattedString);

        formattedString = formattedString
                .concat(("CMD LINE: " + i.commandLine().orElse("")).indent(3));
        p(3 + formattedString);

        formattedString = formattedString.concat(("Arguments=" + Arrays.stream(i.arguments().orElse(new String[0])).collect(Collectors.joining("-"))).indent(5));
        p(5 + formattedString);

        formattedString = formattedString.concat(("USER: " + i.user().orElse("")).indent(7));
        p(7 + formattedString);
    }


    public static void main(String[] args) throws IOException {
        /*ProcessBuilder ls = new ProcessBuilder()
                .command("ls")
                .directory(Paths.get("/tmp")
                        .toFile());
        ProcessBuilder wc = new ProcessBuilder()
                .command("wc", "-l")
                .redirectOutput(ProcessBuilder.Redirect.INHERIT);
        List<Process> dirPipeù = ProcessBuilder.startPipeline(Arrays.asList(ls, wc));*/


        ProcessBuilder echo = new ProcessBuilder()
                .command("CMD", "/C", "echo", "==== DIR cmd output ====");
        ProcessBuilder dir = new ProcessBuilder()
                .command("CMD", "/C", "dir")
                .directory(Paths.get("C:\\Users\\l.pancani")
                        .toFile()).redirectOutput(ProcessBuilder.Redirect.INHERIT);
        List<Process> echoPipeDir = ProcessBuilder.startPipeline(Arrays.asList(echo, dir));


        ProcessBuilder processBuilder = new ProcessBuilder();
        Map<String, String> environment = processBuilder.environment();
        environment.keySet().stream()
                .filter(s -> s.toUpperCase().contains("TNS") || s.toUpperCase().contains("ORA") || s.toUpperCase()
                        .contains("INST"))
                .sorted()
                .peek(s -> p("==== ENV key=\"" + s + "\" ===="))
                .forEach(key -> System.out.printf("%s -> %s\n\n", key, environment.get(key)));


        echoPipeDir.stream()
                .peek(process -> p("==== INFO process - pid: " + process.pid() + " ===="))
                .forEach(process -> p(process.info()));



        String multilineStr = "This is a multiline string.";

        p("LINES:");
        multilineStr.lines().forEach(s -> p(s.indent(1)));
    }
}
