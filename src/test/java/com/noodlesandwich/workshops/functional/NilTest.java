package com.noodlesandwich.workshops.functional;

import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static com.noodlesandwich.workshops.functional.FunctionalList.nil;
import static com.noodlesandwich.workshops.functional.FunctionalListMatcher.empty;

public final class NilTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test public void
    nil_is_empty() {
        assertThat(nil(), is(empty()));
    }

    @Test public void
    attempting_to_retrieve_the_head_throws_an_exception() {
        exception.expect(NoSuchElementException.class);
        nil().head();
    }

    @Test public void
    attempting_to_retrieve_the_tail_throws_an_exception() {
        exception.expect(NoSuchElementException.class);
        nil().tail();
    }
}
