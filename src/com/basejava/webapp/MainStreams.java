package com.basejava.webapp;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MainStreams {

    public static void main(String[] args) {

        System.out.println(minValue(ThreadLocalRandom
                .current()
                .ints(9, 1, 10)
                .toArray()
        ));

        System.out.println(oddOrEven(ThreadLocalRandom
                .current()
                .ints(9, 1, 10)
                .boxed()
                .collect(Collectors.toList())
        ));

        System.out.println(oddOrEven2(ThreadLocalRandom
                .current()
                .ints(9, 1, 10)
                .boxed()
                .collect(Collectors.toList())
        ));
    }

    public static int minValue(int[] values) {
        return IntStream.of(values)
                .distinct()
                .sorted()
                .reduce(0, (a, b) -> a * 10 + b);
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        boolean isOdd = integers.stream().reduce(0, Integer::sum) % 2 != 0;
        return integers.stream()
                .filter(isOdd ? integer -> integer % 2 == 0 : integer -> integer % 2 != 0)
                .collect(Collectors.toList());
    }


    public static List<Integer> oddOrEven2(List<Integer> integers) {
        boolean isOdd = integers.stream().reduce(0, Integer::sum) % 2 != 0;
        return isOdd ? getOdd(integers) : getEven(integers);

    }

    private static List<Integer> getEven(List<Integer> integers) {
        return integers.stream()
                .filter(integer -> integer % 2 == 0)
                .collect(Collectors.toList());
    }

    private static List<Integer> getOdd(List<Integer> integers) {
        return integers.stream()
                .filter(integer -> integer % 2 != 0)
                .collect(Collectors.toList());
    }

}