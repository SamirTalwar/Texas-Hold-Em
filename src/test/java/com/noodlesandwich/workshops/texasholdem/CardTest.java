package com.noodlesandwich.workshops.texasholdem;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;

public final class CardTest {
    @Test public void
    cards_can_test_whether_they_have_the_same_number_as_another() {
        assertThat(Card.lookup("5d"), hasTheSameNumberAs(Card.lookup("5s")));
    }

    @Test public void
    cards_can_be_converted_to_strings() {
        assertThat(Card.lookup("Ks"), hasToString("Ks"));
    }

    @Test public void
    returns_a_null_card_if_it_cant_find_the_number_specified() {
        assertThat(Card.lookup("Xs"), is(Card.NULL));
    }

    @Test public void
    returns_a_null_card_if_it_cant_find_the_suit_specified() {
        assertThat(Card.lookup("Ax"), is(Card.NULL));
    }

    private static Matcher<? super Card> hasTheSameNumberAs(final Card expected) {
        return new TypeSafeDiagnosingMatcher<Card>() {
            @Override
            public void describeTo(final Description description) {
                description.appendText("has the same number as ").appendValue(expected);
            }

            @Override
            protected boolean matchesSafely(final Card actual, final Description mismatchDescription) {
                mismatchDescription.appendText("was ").appendValue(actual);
                return actual.hasTheSameNumberAs(expected);
            }
        };
    }
}
