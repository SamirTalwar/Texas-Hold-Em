package com.noodlesandwich.workshops.functional;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.workshops.functional.FunctionalList.nil;
import static com.noodlesandwich.workshops.functional.FunctionalListMatcher.aListContaining;
import static com.noodlesandwich.workshops.functional.FunctionalListMatcher.empty;
import static com.noodlesandwich.workshops.functional.FunctionalMapMatcher.aMapOf;
import static com.noodlesandwich.workshops.functional.FunctionalMapMatcher.entry;

public final class FunctionalListTest {
    @Test public void
    maps_an_empty_list_to_an_empty_list() {
        assertThat(nil().map(toStringFunction), is(empty(String.class)));
    }

    @Test public void
    maps_a_list_to_another_of_the_same_type() {
        assertThat(FunctionalList.of(1, 2, 3).map(add(1)), is(aListContaining(2, 3, 4)));
    }

    @Test public void
    finds_the_first_value_that_matches_a_predicate() {
        assertThat(FunctionalList.of(5, 7, 4, 2, 9, 8).find(even(), null), is(4));
    }

    @Test public void
    returns_the_default_value_if_no_item_matches_the_predicate() {
        assertThat(FunctionalList.of(5, 7, 3, 11, 9, 7).find(even(), 999), is(999));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    groups_by_a_key_generated_by_a_function() {
        assertThat(FunctionalList.of(7, 6, 5, 4, 8, 7, 6, 5).groupBy(oddsAndEvens()),
                   is(aMapOf(entry(0).with(6, 4, 8, 6),
                             entry(1).with(7, 5, 7, 5))));
    }

    private static Function<Object, String> toStringFunction =
        new Function<Object, String>() {
            @Override public String apply(final Object input) {
                return input.toString();
            }
        };

    private static Function<Integer, Integer> add(final int n) {
        return new Function<Integer, Integer>() {
            @Override public Integer apply(final Integer input) {
                return input + n;
            }
        };
    }

    private static Predicate<Integer> even() {
        return new Predicate<Integer>() {
            @Override public boolean matches(final Integer input) {
                return input % 2 == 0;
            }
        };
    }

    private static Function<Integer, Integer> oddsAndEvens() {
        return new Function<Integer, Integer>() {
            @Override public Integer apply(final Integer input) {
                return input % 2;
            }
        };
    }
}
