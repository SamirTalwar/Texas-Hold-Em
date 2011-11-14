package com.noodlesandwich.workshops.functional.testutils;

import com.noodlesandwich.workshops.functional.Predicate;

public final class Predicates {
    private Predicates() { }

    public static <T> Predicate<T> equalTo(final T value) {
        return new Predicate<T>() {
            @Override public boolean matches(final T input) {
                return value.equals(input);
            }
        };
    }

    public static Predicate<Integer> even() {
        return new Predicate<Integer>() {
            @Override public boolean matches(final Integer input) {
                return input % 2 == 0;
            }
        };
    }

    public static <T> Predicate<T> alwaysFalse(final Class<T> type) {
        return new Predicate<T>() {
            @Override public boolean matches(final T input) {
                return false;
            }
        };
    }
}
