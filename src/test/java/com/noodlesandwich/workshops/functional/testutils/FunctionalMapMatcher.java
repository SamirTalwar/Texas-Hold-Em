package com.noodlesandwich.workshops.functional.testutils;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import com.noodlesandwich.workshops.functional.FunctionalList;
import com.noodlesandwich.workshops.functional.FunctionalMap;
import com.noodlesandwich.workshops.functional.Predicate;

import static com.noodlesandwich.workshops.functional.testutils.FunctionalListMatcher.anUnorderedListContaining;

public final class FunctionalMapMatcher<K, V> extends TypeSafeDiagnosingMatcher<FunctionalMap<K, V>> {
    private final List<EntryMatcher<K, V>> entries;

    private FunctionalMapMatcher(final EntryMatcher<K, V>[] entries) {
        this.entries = Arrays.asList(entries);
    }

    public static <K, V> FunctionalMapMatcher<K, V> aMapOf(final EntryMatcher<K, V>... entries) {
        return new FunctionalMapMatcher<K, V>(entries);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("a map of ").appendList("", ", ", "", entries);
    }

    @Override
    protected boolean matchesSafely(final FunctionalMap<K, V> map, final Description mismatchDescription) {
        for (final EntryMatcher<K, V> entry : entries) {
            if (!entry.matches(map)) {
                entry.describeMismatch(map, mismatchDescription);
                return false;
            }
        }
        return true;
    }

    public static <K> EntryMatcherBuilder<K> entry(final K key) {
        return new EntryMatcherBuilder<K>(key);
    }

    public static final class EntryMatcherBuilder<K> {
        private final K key;

        public EntryMatcherBuilder(final K key) {
            this.key = key;
        }

        public <V> EntryMatcher<K, V> with(final V... values) {
            return new EntryMatcher<K, V>(key, values);
        }
    }

    public static final class EntryMatcher<K, V> extends TypeSafeDiagnosingMatcher<FunctionalMap<K, V>> {
        private final K key;
        private final V[] values;

        public EntryMatcher(final K key, final V[] values) {
            this.key = key;
            this.values = values;
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("entry ").appendValue(key)
                       .appendText(" with ").appendValueList("[", ", ", "]", values);
        }

        @Override
        protected boolean matchesSafely(final FunctionalMap<K, V> map, final Description mismatchDescription) {
            if (!map.containsValue(matching(key, values))) {
                mismatchDescription.appendText("did not contain an entry matching ").appendValue(key)
                                   .appendText(" with ").appendValueList("[", ", ", "]", values);
                return false;
            }

            return true;
        }

        private static <K, V> Predicate<FunctionalList<V>> matching(final K key, final V[] expectedValues) {
            return new Predicate<FunctionalList<V>>() {
                @Override public boolean matches(final FunctionalList<V> actualValues) {
                    return anUnorderedListContaining(expectedValues).matches(actualValues);
                }
            };
        }
    }
}
