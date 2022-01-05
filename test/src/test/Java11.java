package test;

import test.util.Print;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;

import static test.util.Print.p;

public class Java11 {

    //su String aggiunto metodo lines() per spezzare più linee
    private static void testLines() {
        String multilineStr = "This is \na \nmultiline string.";

        p("LINES:");
        multilineStr.lines().forEach(Print::p);
    }

    private static void testHttpClient() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();

        HttpRequest request =
                HttpRequest.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .uri(URI.create("https://advancedweb.hu/"))
                        .GET()
                        .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Print.p("response.statusCode()=" + response.statusCode());
        Print.p("response.body()=" + response.body());
        response.headers().map().forEach((s, strings) -> {
            Print.p(s + "=" + strings.stream().collect(Collectors.joining(",")));
        });
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        testLines();
        testHttpClient();
    }
}
