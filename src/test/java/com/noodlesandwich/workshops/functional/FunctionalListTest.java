package com.noodlesandwich.workshops.functional;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.workshops.functional.FunctionalList.nil;
import static com.noodlesandwich.workshops.functional.testutils.FunctionalListMatcher.aListContaining;
import static com.noodlesandwich.workshops.functional.testutils.FunctionalListMatcher.empty;
import static com.noodlesandwich.workshops.functional.testutils.FunctionalMapMatcher.aMapOf;
import static com.noodlesandwich.workshops.functional.testutils.FunctionalMapMatcher.entry;
import static com.noodlesandwich.workshops.functional.testutils.Functions.add;
import static com.noodlesandwich.workshops.functional.testutils.Functions.oddsAndEvens;
import static com.noodlesandwich.workshops.functional.testutils.Functions.toStringFunction;
import static com.noodlesandwich.workshops.functional.testutils.Predicates.alwaysFalse;
import static com.noodlesandwich.workshops.functional.testutils.Predicates.equalTo;
import static com.noodlesandwich.workshops.functional.testutils.Predicates.even;

public final class FunctionalListTest {
    @Test public void
    maps_an_empty_list_to_an_empty_list() {
        assertThat(nil().map(toStringFunction()), is(empty(String.class)));
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

    @Test public void
    detects_that_a_list_contains_an_element() {
        assertThat(FunctionalList.of(7, 5, 6, 2).contains(even()), is(true));
    }

    @Test public void
    detects_that_a_list_does_not_contain_an_element() {
        assertThat(FunctionalList.of(7, 5, 3, 1).contains(even()), is(false));
    }

    @Test public void
    removes_an_element_from_a_list() {
        assertThat(FunctionalList.of(5, 4, 3).remove(equalTo(4)), is(aListContaining(5, 3)));
    }

    @Test public void
    does_not_remove_more_than_one_element() {
        assertThat(FunctionalList.of(5, 4, 3, 2).remove(even()), is(aListContaining(5, 3, 2)));
    }

    @Test public void
    returns_the_same_list_if_no_element_was_found() {
        assertThat(FunctionalList.of(5, 4, 3, 2).remove(alwaysFalse(Integer.class)), is(aListContaining(5, 4, 3, 2)));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    groups_by_a_key_generated_by_a_function() {
        assertThat(FunctionalList.of(7, 6, 5, 4, 8, 7, 6, 5).groupBy(oddsAndEvens()),
                   is(aMapOf(entry(0).with(6, 4, 8, 6),
                             entry(1).with(7, 5, 7, 5))));
    }

    @SuppressWarnings("unchecked")
    @Test public void
    partitions_a_list_into_sub_lists_of_a_given_size() {
        assertThat(FunctionalList.of(6, 1, 7, 2, 8, 4, 3, 2, 6, 7, 8, 10).subListsOfSize(6),
                   is(aListContaining(aListContaining(6, 1, 7, 2, 8, 4),
                                      aListContaining(1, 7, 2, 8, 4, 3),
                                      aListContaining(7, 2, 8, 4, 3, 2),
                                      aListContaining(2, 8, 4, 3, 2, 6),
                                      aListContaining(8, 4, 3, 2, 6, 7),
                                      aListContaining(4, 3, 2, 6, 7, 8),
                                      aListContaining(3, 2, 6, 7, 8, 10))));
    }
}
