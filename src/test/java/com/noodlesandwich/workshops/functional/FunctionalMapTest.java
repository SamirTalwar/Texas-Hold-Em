package com.noodlesandwich.workshops.functional;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.workshops.functional.testutils.FunctionalMapMatcher.aMapOf;
import static com.noodlesandwich.workshops.functional.testutils.FunctionalMapMatcher.entry;

public class FunctionalMapTest {
    @SuppressWarnings("unchecked")
    @Test public void
    a_map_contains_its_items() {
        final FunctionalMap<Character, Integer> map = new FunctionalMap<Character, Integer>()
            .with('o', 1)
            .with('t', 2)
            .with('t', 3)
            .with('f', 4)
            .with('f', 5)
            .with('s', 6)
            .with('s', 7)
            .with('e', 8)
            .with('n', 9)
            .with('t', 10);

        assertThat(map, is(aMapOf(entry('o').with(1),
                                  entry('t').with(2, 3, 10),
                                  entry('f').with(4, 5),
                                  entry('s').with(6, 7),
                                  entry('e').with(8),
                                  entry('n').with(9))));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    a_map_will_match_on_multiple_items() {
        final FunctionalMap<Character, Integer> map = new FunctionalMap<Character, Integer>()
            .with('o', 1)
            .with('t', 2)
            .with('t', 3)
            .with('f', 4)
            .with('f', 5)
            .with('s', 6)
            .with('s', 7)
            .with('e', 8)
            .with('n', 9)
            .with('t', 10);

        assertThat(map.containsValues(containing(3), containing(5), containing(7)), is(true));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    a_map_will_not_match_the_same_items_twice() {
        final FunctionalMap<Character, Integer> map = new FunctionalMap<Character, Integer>()
            .with('o', 1)
            .with('t', 2)
            .with('t', 3)
            .with('f', 4)
            .with('f', 5)
            .with('s', 6)
            .with('s', 7)
            .with('e', 8)
            .with('n', 9)
            .with('t', 10);

        assertThat(map.containsValues(containing(3), containing(3)), is(false));
    }

    private static <T> Predicate<FunctionalList<T>> containing(final T item) {
        return new Predicate<FunctionalList<T>>() {
            @Override public boolean matches(final FunctionalList<T> list) {
                return containsItem(item, list);
            }

            private boolean containsItem(final T item, final FunctionalList<T> list) {
                return list.isEmpty()
                           ? false
                           : item.equals(list.head())
                                 ? true
                                 : containsItem(item, list.tail());
            }
        };
    }
}
