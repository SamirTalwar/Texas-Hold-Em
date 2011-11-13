package com.noodlesandwich.workshops.functional;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.workshops.functional.FunctionalList.nil;
import static com.noodlesandwich.workshops.functional.FunctionalListMatcher.empty;
import static com.noodlesandwich.workshops.functional.FunctionalListMatcher.aListOf;

public final class FunctionalListTest {
    @Test public void
    maps_an_empty_list_to_an_empty_list() {
        assertThat(nil().map(toStringFunction), is(empty(String.class)));
    }

    @Test public void
    maps_a_list_to_another_of_the_same_type() {
        assertThat(FunctionalList.of(1, 2, 3).map(add(1)), is(aListOf(2, 3, 4)));
    }

    private static Function<Object, String> toStringFunction =
        new Function<Object, String>() {
            @Override public String apply(final Object input) {
                return input.toString();
            }
        };

    private Function<Integer, Integer> add(final int n) {
        return new Function<Integer, Integer>() {
            @Override public Integer apply(final Integer input) {
                return input + n;
            }
        };
    }
}
