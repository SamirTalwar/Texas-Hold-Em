package com.noodlesandwich.workshops.functional;

import java.util.ArrayList;
import java.util.Collection;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static com.noodlesandwich.workshops.functional.Predicates.equalTo;

public final class FunctionalListMatcher<T> {
    private FunctionalListMatcher() { }

    public static <T> Matcher<? super FunctionalList<T>> empty() {
        return new EmptyMatcher<T>();
    }

    public static <T> Matcher<? super FunctionalList<T>> empty(final Class<T> type) {
        return new EmptyMatcher<T>();
    }

    public static <T> Matcher<? super FunctionalList<T>> aListContaining(final T... items) {
        return new ListMatcher<T>(items);
    }

    public static <T> Matcher<? super FunctionalList<T>> anUnorderedListContaining(final T... items) {
        return new UnorderedListMatcher<T>(items);
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
            description.appendText("a list containing ").appendValueList("[", ", ", "]", expected);
        }

        @Override
        protected boolean matchesSafely(final FunctionalList<T> actual, final Description mismatchDescription) {
            mismatchDescription.appendText("was a list containing ").appendValueList("[", ", ", "]", toIterable(actual));
            return listEquals(FunctionalList.of(expected), actual);
        }

        private boolean listEquals(final FunctionalList<T> one, final FunctionalList<T> two) {
            return one.isEmpty() && two.isEmpty()
                       ? true
                       : one.isEmpty() || two.isEmpty()
                             ? false
                             : !one.head().equals(two.head())
                                   ? false
                                   : listEquals(one.tail(), two.tail());
        }
    }

    private static final class UnorderedListMatcher<T> extends TypeSafeDiagnosingMatcher<FunctionalList<T>> {
        private final T[] expected;

        public UnorderedListMatcher(final T[] items) {
            this.expected = items;
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("a list containing ").appendValueList("[", ", ", "]", expected)
                       .appendText(" in any order");
        }

        @Override
        protected boolean matchesSafely(final FunctionalList<T> actual, final Description mismatchDescription) {
            mismatchDescription.appendText("was a list containing ").appendValueList("[", ", ", "]", toIterable(actual));
            return unorderedListEquals(FunctionalList.of(expected), actual);
        }

        private boolean unorderedListEquals(final FunctionalList<T> one, final FunctionalList<T> two) {
            return one.isEmpty() && two.isEmpty()
                       ? true
                       : one.isEmpty() || two.isEmpty()
                             ? false
                             : !two.contains(equalTo(one.head()))
                                   ? false
                                   : unorderedListEquals(one.tail(), two.remove(equalTo(one.head())));
        }
    }

    private static <T> Iterable<T> toIterable(final FunctionalList<T> list) {
        return toCollection(list, new ArrayList<T>());
    }

    private static <T> Collection<T> toCollection(final FunctionalList<T> list, final Collection<T> intermediate) {
        return list.isEmpty()
                   ? intermediate
                   : toCollection(list.tail(), add(intermediate, list.head()));
    }

    private static <T> Collection<T> add(final Collection<T> list, final T element) {
        list.add(element);
        return list;
    }
}
