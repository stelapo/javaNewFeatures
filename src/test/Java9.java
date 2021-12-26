package test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Java9 {
    public static void main(String[] args) throws IOException {
        /*ProcessBuilder ls = new ProcessBuilder()
                .command("ls")
                .directory(Paths.get("/tmp")
                        .toFile());
        ProcessBuilder wc = new ProcessBuilder()
                .command("wc", "-l")
                .redirectOutput(ProcessBuilder.Redirect.INHERIT);
        List<Process> lsPipeWc = ProcessBuilder.startPipeline(Arrays.asList(ls, wc));*/



        ProcessBuilder dir = new ProcessBuilder()
                .command("CMD", "/C", "dir")
                .directory(Paths.get("C:\\Users\\l.pancani")
                        .toFile()).redirectOutput(ProcessBuilder.Redirect.INHERIT);
        ProcessBuilder echo = new ProcessBuilder()
                .command("CMD", "/C", "echo", "pippo")
                .redirectOutput(ProcessBuilder.Redirect.INHERIT);
        List<Process> lsPipeWc = ProcessBuilder.startPipeline(Arrays.asList(/*dir,*/ echo));

        ProcessBuilder processBuilder = new ProcessBuilder();
        Map<String, String> environment = processBuilder.environment();
        environment.keySet().stream().filter(s -> s.toUpperCase().contains("TNS") || s.toUpperCase().contains("ORA") || s.toUpperCase().contains("INST")).sorted().forEach(key -> System.out.printf("%s -> %s\n", key, environment.get(key)));
    }
}
