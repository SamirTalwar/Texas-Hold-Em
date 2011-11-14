package com.noodlesandwich.workshops.functional.testutils;

import com.noodlesandwich.workshops.functional.Function;

public final class Functions {
    private Functions() { }

    public static Function<Object, String> toStringFunction() {
        return new Function<Object, String>() {
            @Override public String apply(final Object input) {
                return input.toString();
            }
        };
    }

    public static Function<Integer, Integer> add(final int n) {
        return new Function<Integer, Integer>() {
            @Override public Integer apply(final Integer input) {
                return input + n;
            }
        };
    }

    public static Function<Integer, Integer> oddsAndEvens() {
        return new Function<Integer, Integer>() {
            @Override public Integer apply(final Integer input) {
                return input % 2;
            }
        };
    }

}
