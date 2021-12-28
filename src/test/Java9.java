package test;

import test.util.Print;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.BaseStream;
import java.util.stream.Collectors;

public class Java9 {

    private static LocalDateTime currentLocalDateTime() {
        return LocalDateTime.now();
    }


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

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


        Thread.sleep(2000);

        //Delay
        CompletableFuture<Object> future = new CompletableFuture<>();
        Print.p("Delay - BEFORE SCHEDULE = " + currentLocalDateTime());
        future
                .completeAsync(() -> {
                    Print.p("Delay - START = " + currentLocalDateTime());
                    return "Hello";
                }, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS))
                .thenAccept(o -> Print.p("Delay - thenAccept = " + currentLocalDateTime() + "; O = " + o))
        ;
        String resp = future.get().toString();
        Print.p("Delay - END = " + currentLocalDateTime() + "; RESPONSE = " + resp);


        Thread.sleep(2000);


        //Complete With Value on Timeout
        future = new CompletableFuture<>();
        Print.p("Complete With Value on Timeout - START = " + currentLocalDateTime());
        future
                .completeAsync(() -> {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "Complete With Value on Timeout - Ho finito dentro 1 secondo!";
                })
                .completeOnTimeout("Complete With Value on Timeout - Non ho finito dopo 1 secondo", 1, TimeUnit.SECONDS);
        resp = future.get().toString();
        Print.p("Complete With Value on Timeout - END = " + currentLocalDateTime());
        Print.p("Complete With Value on Timeout - RESPONSE = " + resp + " - " + currentLocalDateTime());


        Thread.sleep(2000);


        //Timeout
        future = new CompletableFuture<>();
        Print.p("Timeout - START = " + currentLocalDateTime());
        future = future
                .completeAsync(() -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "Timeout - Ho finito in meno di 1 secondo!";
                })
                .orTimeout(1, TimeUnit.SECONDS);
        resp = future.get().toString();
        Print.p("Timeout - END = " + currentLocalDateTime());
        Print.p("Timeout - RESPONSE = " + resp + " - " + currentLocalDateTime());


        Thread.sleep(2000);
    }
}
