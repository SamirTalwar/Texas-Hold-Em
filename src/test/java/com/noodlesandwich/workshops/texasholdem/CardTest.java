package com.noodlesandwich.workshops.texasholdem;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

public final class CardTest {
    @Test public void
    cards_can_test_whether_they_have_the_same_number_as_another() {
        assertThat(new Card("5d"), hasTheSameNumberAs(new Card("5s")));
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
