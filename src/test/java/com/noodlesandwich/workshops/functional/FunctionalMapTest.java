package com.noodlesandwich.workshops.functional;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.workshops.functional.FunctionalMapMatcher.aMapOf;
import static com.noodlesandwich.workshops.functional.FunctionalMapMatcher.entry;

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
}
