package test;

import test.util.Print;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.List.of;

public class Java16 {
    public static void main(String[] args) {
        List<Double> doubleList = of(1.1d, 2.2d, 3d);
        Stream<Long> longStream = doubleList.stream().map(Math::round);
        LongStream longStream1 = doubleList.stream().mapToLong(Math::round);

        List<Optional<Double>> optionalList = of(Optional.of(1.1d), Optional.of(2.2d), Optional.empty(), Optional.of(3d));
        optionalList
                .stream()
                .forEach(System.out::println);

        Print.p("\nESEMPIO1:");

        List<Optional<String>> listOfOptionals = Arrays.asList(
                Optional.empty(), Optional.of("foo"), Optional.empty(), Optional.of("bar"));
        listOfOptionals
                .stream()
                .forEach(optional -> Print.p(optional.stream().collect(Collectors.joining()))); //stream() mi restituisce un empty string per il primo e secondo elemento

        Print.p("\nESEMPIO2:");

        listOfOptionals.stream()
                .filter(Optional::isPresent) //Filtro solo gli optional NON empty
                .forEach(Print::p);

        Print.p("\nESEMPIO3:");

        listOfOptionals.stream()
                .flatMap(Optional::stream) //stream aggiunto in Java 9
                .forEach(Print::p);

        Print.p("\nESEMPIO4:");

        List<String> filteredList = listOfOptionals.stream()
                .flatMap(Optional::stream)
                .toList(); //toList aggiunto in Java 16
        filteredList.stream().forEach(Print::p);

        Print.p("\nESEMPIO5:");

        listOfOptionals.stream()
                .mapMulti(Optional::ifPresent)
                .forEach(o -> Print.p(o.toString()));


        Print.p("\nESEMPIO6 - mapMulti():");

        //Consumer<Integer> myConsumer = integer -> Print.p(integer.toString() + "x");

        var lists = of(of(1), of(2, 3), List.<Integer>of());

        Print.p("\nStep 6.1:");
        var result = lists.stream()
                .<Integer>mapMulti((list, downstream) -> list.forEach(downstream)) //forEach prende un Consumer e quindi va bene passare downstream che è appunto un Consumer
                .collect(Collectors.toList());

        Print.p("\nStep 6.2:");
        result.stream().forEach(Print::p);

        Print.p("\nStep 6.3 - Fibonacci:");

        IntStream.of(0)
                .mapMulti((a, c) -> {
                    for (int b = 1; a >= 0 && a < 100; b = a + (a = b)) {
                        Print.p("a=" + a + "; b=" + b);
                        c.accept(a);
                    }
                })
                .forEach(Print::p);

        var a = 100;
        var i = 101;
        Print.p("(a=i) = " + (a = i));


        Print.p("\nESEMPIO7 - iterate() - Fibonacci:");
        Stream.iterate(new int[]{0, 1}, n -> new int[]{n[1], n[0] + n[1]})
                .limit(10)
                .map(n -> n[0]) // Stream<Integer>
                .forEach(Print::p);
        Print.p("SUM Fibonacci=" + Stream.iterate(new int[]{0, 1}, n -> new int[]{n[1], n[0] + n[1]})
                .limit(10)
                .map(n -> n[0]) // Stream<Integer>
                .mapToInt(n -> n)
                .sum());
    }
}
