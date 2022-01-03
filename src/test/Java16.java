package test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.List.of;

public class Java16 {
    public static void main(String[] args) {
        List<Double> doubleList = of(1.1d, 2.2d, 3d);
        Stream<Long> longStream = doubleList.stream().map(Math::round);

        List<Double> doubleList1 = of(1.1d, 2.2d, 3d);
        Stream<Long> longStream1 = doubleList.stream().mapToLong(Math::round).boxed();


        List<Optional<Double>> optionalList = of(Optional.of(1.1d), Optional.of(2.2d), Optional.empty(), Optional.of(3d));
        optionalList
                .stream()
                .forEach(System.out::println);

        System.out.println("\nESEMPIO1:");

        List<Optional<String>> listOfOptionals = Arrays.asList(
                Optional.empty(), Optional.of("foo"), Optional.empty(), Optional.of("bar"));
        listOfOptionals
                .stream()
                .forEach(System.out::println);

        System.out.println("\nESEMPIO2:");

        listOfOptionals.stream()
                .filter(Optional::isPresent)
                .forEach(System.out::println);

        System.out.println("\nESEMPIO3:");

        listOfOptionals.stream()
                .flatMap(Optional::stream) //stream aggiunto in Java 9
                .forEach(System.out::println);

        System.out.println("\nESEMPIO4:");

        List<String> filteredList = listOfOptionals.stream()
                .flatMap(Optional::stream)
                .toList(); //toList aggiunto in Java 16
        filteredList.stream().forEach(System.out::println);

        System.out.println("\nESEMPIO5:");

        listOfOptionals.stream()
                .mapMulti(Optional::ifPresent)
                .forEach(System.out::println);


        System.out.println("\nESEMPIO6:");

        Consumer<Integer> myConsumer = integer -> System.out.println(integer.toString() + "x");


        var lists = of(of(1), of(2, 3), List.<Integer>of());

        System.out.println("\nStep 6.1:");
        var result = lists.stream()
                .<Integer>mapMulti((list, downstream) -> list.forEach(downstream))
                .collect(Collectors.toList());

        System.out.println("\nStep 6.2:");
        result.stream().forEach(System.out::println);


        IntStream.of(0)
                .mapMulti((a, c) -> {
                    for (int b = 1; a >= 0 && a < 100; b = a + (a = b)) {
                        System.out.println("a=" + a + "; b=" + b);
                        c.accept(a);
                    }
                })
                .forEach(System.out::println);

        var a = 100;
        var i = 101;
        System.out.println("(a=i) = " + (a = i));
    }
}
