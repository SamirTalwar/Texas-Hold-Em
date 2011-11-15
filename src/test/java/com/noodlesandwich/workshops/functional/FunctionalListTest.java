package com.noodlesandwich.workshops.functional;

import org.hamcrest.Matcher;
import org.junit.Test;

import com.noodlesandwich.workshops.functional.testutils.FunctionalListMatcher;

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
import static com.noodlesandwich.workshops.functional.testutils.Predicates.lessThan;

public final class FunctionalListTest {
    @Test public void
    is_equal_to_another_list_with_the_same_elements() {
        assertThat(FunctionalList.of(7, 3, 2, 4).isEqualTo(FunctionalList.of(7, 3, 2, 4)), is(true));
    }

    @Test public void
    is_not_equal_to_another_list_with_different_elements() {
        assertThat(FunctionalList.of(7, 3, 4, 4).isEqualTo(FunctionalList.of(7, 3, 2, 4)), is(false));
    }

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
    takes_the_first_N_items() {
        assertThat(FunctionalList.of(2, 7, 4, 8, 1, 2).take(3), is(aListContaining(2, 7, 4)));
    }

    @Test public void
    taking_more_than_the_number_of_items_in_the_list_returns_the_entire_list() {
        assertThat(FunctionalList.of(2, 7, 4, 8, 1, 2).take(7), is(aListContaining(2, 7, 4, 8, 1, 2)));
    }

    @Test public void
    drops_items_from_a_list_until_the_condition_is_met() {
        assertThat(FunctionalList.of(6, 4, 3, 7, 4, 2, 1).dropWhile(even()), is(aListContaining(3, 7, 4, 2, 1)));
    }

    @Test public void
    drops_all_elements_if_the_condition_is_not_met() {
        assertThat(FunctionalList.of(6, 4, 8, 2).dropWhile(even()), is(empty(Integer.class)));
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

    @Test public void
    determines_that_all_elements_of_the_list_match_the_predicate() {
        assertThat(FunctionalList.of(7, 4, 2, 6).all(lessThan(10)), is(true));
    }

    @Test public void
    determines_that_not_all_elements_of_the_list_match_the_predicate() {
        assertThat(FunctionalList.of(7, 4, 12, 6).all(lessThan(10)), is(false));
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
    generates_combinations_of_a_given_size() {
        assertThat(FunctionalList.of(7, 3, 4, 6, 1).combinationsOfSize(3),
                   is(FunctionalListMatcher.<FunctionalList<Integer>>aListContaining(
                       ints(7, 3, 4),
                       ints(7, 3, 6),
                       ints(7, 3, 1),
                       ints(7, 4, 6),
                       ints(7, 4, 1),
                       ints(7, 6, 1),
                       ints(3, 4, 6),
                       ints(3, 4, 1),
                       ints(3, 6, 1),
                       ints(4, 6, 1))));
    }

    private Matcher<? super FunctionalList<Integer>> ints(final Integer... values) {
        return aListContaining(values);
    }

    @Test public void
    returns_an_empty_list_if_the_combination_size_is_greater_than_the_size_of_the_list() {
        assertThat(FunctionalList.of(6, 5, 4).combinationsOfSize(4), is(FunctionalListMatcher.<FunctionalList<Integer>>empty()));
    }

    @Test public void
    concatenates_one_list_to_another() {
        assertThat(FunctionalList.of(5, 3, 1).concat(FunctionalList.of(6, 4, 2)), is(aListContaining(5, 3, 1, 6, 4, 2)));
    }
}
