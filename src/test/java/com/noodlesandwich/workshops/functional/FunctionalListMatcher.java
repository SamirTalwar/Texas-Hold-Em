package com.noodlesandwich.workshops.functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public final class FunctionalListMatcher<T> {
    public static <T> Matcher<? super FunctionalList<T>> empty() {
        return new EmptyMatcher<T>();
    }

    public static <T> Matcher<? super FunctionalList<T>> empty(final Class<T> type) {
        return new EmptyMatcher<T>();
    }

    public static <T> Matcher<? super FunctionalList<T>> aListOf(final T... items) {
        return new ListMatcher<T>(items);
    }

    private static final class EmptyMatcher<T> extends TypeSafeDiagnosingMatcher<FunctionalList<T>> {
        @Override
        public void describeTo(final Description description) {
            description.appendText("empty");
        }

        @Override
        protected boolean matchesSafely(final FunctionalList<T> list, final Description mismatchDescription) {
            mismatchDescription.appendText("was not empty");
            return list.isEmpty();
        }
    }

    private static final class ListMatcher<T> extends TypeSafeDiagnosingMatcher<FunctionalList<T>> {
        private final T[] expected;

        public ListMatcher(final T[] items) {
            this.expected = items;
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("a list of ").appendValueList("[", ", ", "]", expected);
        }

        @Override
        protected boolean matchesSafely(final FunctionalList<T> list, final Description mismatchDescription) {
            final T[] actual = toArray(list);
            mismatchDescription.appendText("was a list of ").appendValueList("[", ", ", "]", actual);
            return Arrays.equals(expected, actual);
        }

        private static <T> T[] toArray(final FunctionalList<T> list) {
            return toArray(list, new ArrayList<T>());
        }

        @SuppressWarnings("unchecked")
        private static <T> T[] toArray(final FunctionalList<T> list, final List<T> intermediate) {
            return list.isEmpty()
                       ? (T[]) intermediate.toArray()
                       : toArray(list.tail(), add(intermediate, list.head()));
        }

        private static <T> List<T> add(final List<T> list, final T element) {
            list.add(element);
            return list;
        }
    }
}
