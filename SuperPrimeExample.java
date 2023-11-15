package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

class MyStream<T> {
    private final List<T> data;

    private MyStream(List<T> data) {
        this.data = data;
    }
    public static <T> MyStream<T> of(List<T> data) {
        return new MyStream<>(data);
    }
    public MyStream<T> filter(Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T item : data) {
            if (predicate.test(item)) {
                result.add(item);
            }
        }
        return new MyStream<>(result);
    }
    public <R> MyStream<R> map(Function<T, R> mapper) {
        List<R> result = new ArrayList<>();
        for (T item : data) {
            result.add(mapper.apply(item));
        }
        return new MyStream<>(result);
    }
    public List<T> collect() {
        return data;
    }

    public int count() {
        return data.size();
    }
}
public class SuperPrimeExample {
    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i <= 1000; i++) {
            numbers.add(i);
        }

        List<Integer> superPrimes = MyStream.of(numbers)
                .filter(SuperPrimeExample::isSuperPrime)
                .collect();

        int lengthSuperPrimes = MyStream.of(numbers)
                .filter(SuperPrimeExample::isSuperPrime)
                .count();

        List<Double> squares = MyStream.of(numbers)
                .map(n -> Math.pow(2, n))
                .collect();

        System.out.println("Суперпрості числа: " + superPrimes);
        System.out.println("Довжина: " + lengthSuperPrimes);
        System.out.println("Квадрати 2: " + squares);

    }
    private static boolean isSuperPrime(int number) {
        if (number <= 1 || !isPrime(number)) {
            return false;
        }

        int invertedNumber = invertNumber(number);

        return isPrime(number) && isPrime(invertedNumber);
    }
    private static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i * i <= number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
    private static int invertNumber(int number) {
        int inverted = 0;
        while (number > 0) {
            inverted = inverted * 10 + number % 10;
            number /= 10;
        }
        return inverted;
    }
}
