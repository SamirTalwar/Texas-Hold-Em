package com.noodlesandwich.workshops.functional;

public interface Predicate<T> {
    boolean matches(T input);
}
