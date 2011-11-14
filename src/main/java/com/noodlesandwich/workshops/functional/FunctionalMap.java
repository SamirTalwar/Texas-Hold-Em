package com.noodlesandwich.workshops.functional;

import static com.noodlesandwich.workshops.functional.FunctionalList.cons;

public final class FunctionalMap<K, V> {
    private final FunctionalList<Entry> entries;

    public FunctionalMap() {
        this(FunctionalList.<Entry>nil());
    }

    private FunctionalMap(final FunctionalList<Entry> entries) {
        this.entries = entries;
    }

    public FunctionalMap<K, V> with(final K key, final V value) {
        return new FunctionalMap<K, V>(add(key, value, entries));
    }

    public boolean hasItem(final Predicate<FunctionalList<V>> predicate) {
        return hasItem(predicate, entries);
    }

    public boolean hasItem(final Predicate<FunctionalList<V>> predicate, final FunctionalList<Entry> entriesToCheck) {
        return entriesToCheck.isEmpty()
                   ? false
                   : predicate.matches(entriesToCheck.head().values)
                         ? true
                         : hasItem(predicate, entriesToCheck.tail());
    }

    private FunctionalList<Entry> add(final K key, final V value, final FunctionalList<Entry> entriesToCheck) {
        return entriesToCheck.isEmpty()
                   ? cons(new Entry(key, value), FunctionalList.<Entry>nil())
                   : key.equals(entriesToCheck.head().key)
                         ? cons(new Entry(key, cons(value, entriesToCheck.head().values)), entriesToCheck.tail())
                         : cons(entriesToCheck.head(), add(key, value, entriesToCheck.tail()));
    }

    private final class Entry {
        private final K key;
        private final FunctionalList<V> values;

        public Entry(final K key, final V value) {
            this(key, cons(value, FunctionalList.<V>nil()));
        }

        public Entry(final K key, final FunctionalList<V> values) {
            this.key = key;
            this.values = values;
        }
    }
}
