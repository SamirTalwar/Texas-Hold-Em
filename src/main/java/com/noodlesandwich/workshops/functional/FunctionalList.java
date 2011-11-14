package com.noodlesandwich.workshops.functional;

import java.util.NoSuchElementException;

public abstract class FunctionalList<T> {
    public static <T> FunctionalList<T> nil() {
        return new Nil<T>();
    }

    public static <T> FunctionalList<T> cons(final T head, final FunctionalList<T> tail) {
        return new Cons<T>(head, tail);
    }

    public static <T> FunctionalList<T> of(final T... items) {
        return fromArray(items, 0, items.length);
    }

    private static <T> FunctionalList<T> fromArray(final T[] items, final int start, final int end) {
        return start == end
                   ? FunctionalList.<T>nil()
                   : cons(items[start], fromArray(items, start + 1, end));
    }

    public abstract boolean isEmpty();

    public abstract T head();

    public abstract FunctionalList<T> tail();

    public <U> FunctionalList<U> map(final Function<T, U> mapping) {
        return isEmpty()
                   ? FunctionalList.<U>nil()
                   : cons(mapping.apply(head()), tail().map(mapping));
    }

    public T find(final Predicate<T> predicate, final T defaultValue) {
        return isEmpty()
                   ? defaultValue
                   : predicate.matches(head())
                         ? head()
                         : tail().find(predicate, defaultValue);
    }

    public boolean contains(final Predicate<T> predicate) {
        return isEmpty()
                   ? false
                   : predicate.matches(head())
                         ? true
                         : tail().contains(predicate);
    }

    public FunctionalList<T> remove(final Predicate<T> predicate) {
        return isEmpty()
                   ? FunctionalList.<T>nil()
                   : predicate.matches(head())
                         ? tail()
                         : cons(head(), tail().remove(predicate));
    }

    public <K> FunctionalMap<K, T> groupBy(final Function<T, K> grouping) {
        return groupBy(grouping, new FunctionalMap<K, T>());
    }

    private <K> FunctionalMap<K, T> groupBy(final Function<T, K> grouping, final FunctionalMap<K, T> map) {
        return isEmpty()
                   ? map
                   : tail().groupBy(grouping, map.with(grouping.apply(head()), head()));
    }

    public int size() {
        return size(0);
    }

    private int size(final int currentSize) {
        return isEmpty()
                  ? currentSize
                  : tail().size(currentSize + 1);
    }

    public static final class Nil<T> extends FunctionalList<T> {
        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public T head() {
            throw new NoSuchElementException();
        }

        @Override
        public FunctionalList<T> tail() {
            throw new NoSuchElementException();
        }
    }

    public static final class Cons<T> extends FunctionalList<T> {
        private final T head;
        private final FunctionalList<T> tail;

        public Cons(final T head, final FunctionalList<T> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public FunctionalList<T> tail() {
            return tail;
        }
    }
}
