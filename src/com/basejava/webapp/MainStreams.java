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
                .toArray()));

        System.out.println(oddOrEven(ThreadLocalRandom
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
        return integers.stream()
                .filter(integer -> {
                    if (integers.stream().reduce(0, Integer::sum) % 2 != 0) {
                        return integer % 2 == 0;
                    } else {
                        return integer % 2 != 0;
                    }
                })
                .collect(Collectors.toList());
    }
}