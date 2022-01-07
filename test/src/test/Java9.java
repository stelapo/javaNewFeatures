package test;

import test.util.Print;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Java9 {

    public Java9(){
        Print.p("Java9 created!");
    }

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


        //Arrays.mismatch
        Print.p("\nArrays.mismatch example:");
        int array1[] = { 15, 4, 22, 20, 45, 37, 18, 14, 12};
        Print.p("Array1 from index 3 to 5 (excluse):");
        Arrays.stream(Arrays.copyOfRange(array1, 3, 5)).forEach(Print::p);
        int array2[] = { 7, 10, 11, 20, 45, 37, 18, 14, 12};
        Print.p("Array2 from index 3 to 8 (excluse):");
        Arrays.stream(Arrays.copyOfRange(array2, 3, 8)).forEach(Print::p);
        int index = Arrays.mismatch(array1, 3, 5, array2, 3, 8 );
        Print.p("mismatched index for both sub-arrays is: "+index);


        //var _ = "ciao"; //as of release 9, '_' is a keyword, and may not be used as an identifier
        //Print.p(_);
    }
}
