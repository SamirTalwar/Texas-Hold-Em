package com.noodlesandwich.workshops.functional;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public final class FunctionalListMatcher<T> {
    public static <T> Matcher<? super FunctionalList<T>> empty() {
        return new EmptyMatcher<T>();
    }

    private static final class EmptyMatcher<T> extends TypeSafeDiagnosingMatcher<FunctionalList<T>> {
        @Override
        public void describeTo(final Description description) {
            description.appendText("empty");
        }

        @Override
        protected boolean matchesSafely(final FunctionalList<T> list, final Description mismatchDescription) {
            mismatchDescription.appendText("not empty");
            return list.isEmpty();
        }
    }
}
