package com.noodlesandwich.workshops.functional;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;

import static com.noodlesandwich.workshops.functional.FunctionalList.cons;
import static com.noodlesandwich.workshops.functional.FunctionalList.nil;
import static com.noodlesandwich.workshops.functional.testutils.FunctionalListMatcher.empty;

public final class ConsTest {
    @Test public void
    cons_is_not_empty() {
        assertThat(cons(new Object(), nil()), is(not(empty())));
    }

    @Test public void
    returns_the_head() {
        final Object head = new Object();
        final FunctionalList<Object> tail = cons(new Object(), cons(new Object(), nil()));
        assertThat(cons(head, tail).head(), is(sameInstance(head)));
    }

    @Test public void
    returns_the_tail() {
        final Object head = new Object();
        final FunctionalList<Object> tail = cons(new Object(), cons(new Object(), nil()));
        assertThat(cons(head, tail).tail(), is(sameInstance(tail)));
    }
}
