package com.noodlesandwich.workshops.functional;

public interface Function<I, O> {
    O apply(I input);
}
