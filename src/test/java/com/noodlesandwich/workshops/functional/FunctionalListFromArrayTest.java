package com.noodlesandwich.workshops.functional;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.workshops.functional.FunctionalListMatcher.empty;

public final class FunctionalListFromArrayTest {
    @Test public void
    an_empty_functional_list_is_empty() {
        assertThat(FunctionalList.of(), is(empty()));
    }

    @Test public void
    constructs_a_list_from_an_array() {
        final Object one = new Object();
        final Object two = new Object();
        final Object three = new Object();

        final FunctionalList<Object> list = FunctionalList.of(one, two, three);
        assertThat(list.head(), is(one));
        assertThat(list.tail().head(), is(two));
        assertThat(list.tail().tail().head(), is(three));
        assertThat(list.tail().tail().tail(), is(empty()));
    }
}
