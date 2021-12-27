package test;

import test.util.Print;

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




    public static void main(String[] args) throws IOException {

        /* ====== Process API ====== */

        /*ProcessBuilder ls = new ProcessBuilder()
                .command("ls")
                .directory(Paths.get("/tmp")
                        .toFile());
        ProcessBuilder wc = new ProcessBuilder()
                .command("wc", "-l")
                .redirectOutput(ProcessBuilder.Redirect.INHERIT);
        List<Process> dirPipe = ProcessBuilder.startPipeline(Arrays.asList(ls, wc));*/

        ProcessBuilder echo = new ProcessBuilder()
                .command("CMD", "/C", "echo", "==== DIR cmd output ====");
        ProcessBuilder dir = new ProcessBuilder()
                .command("CMD", "/C", "dir")
                .directory(Paths.get("C:\\Users\\l.pancani")
                        .toFile()).redirectOutput(ProcessBuilder.Redirect.INHERIT);
        List<Process> echoPipeDir = ProcessBuilder.startPipeline(Arrays.asList(echo, dir));

        //Filtro le varibili d'ambiente rispetto al nome della variabile (chiave) e stampo chiave --> valore
        ProcessBuilder processBuilder = new ProcessBuilder();
        Map<String, String> environment = processBuilder.environment();
        environment.keySet().stream()
                .filter(key -> key.toUpperCase().contains("TNS")
                        || key.toUpperCase().contains("ORA")
                        || key.toUpperCase().contains("INST"))
                .sorted()
                .peek(key -> Print.p("==== ENV key=\"" + key + "\" ===="))
                .forEach(key -> System.out.printf("%s -> %s\n\n", key, environment.get(key)));
        //Stampo le info dei due processi
        echoPipeDir.stream()
                .peek(process -> Print.p("==== INFO process - pid: " + process.pid() + " ===="))
                .forEach(process -> Print.p(process.info()));

    }
}
