package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.Function;
import com.noodlesandwich.workshops.functional.FunctionalList;
import com.noodlesandwich.workshops.functional.FunctionalMap;
import com.noodlesandwich.workshops.functional.Predicate;
import com.noodlesandwich.workshops.texasholdem.Card.Rank;
import com.noodlesandwich.workshops.texasholdem.Card.Suit;

public enum Category {
    Flush("Flush", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return cards.groupBy(suit()).containsValue(size(5));
        }
    }),

    Straight("Straight", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            final FunctionalMap<Rank, Card> groupedCards = cards.groupBy(rank());
            return Card.RANKS.subListsOfSize(5).contains(new Predicate<FunctionalList<Rank>>() {
                @Override public boolean matches(final FunctionalList<Rank> ranks) {
                    return groupedCards.containsKeys(ranks);
                }
            });
        }
    }),

    ThreeOfAKind("Three of a Kind", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return cards.groupBy(rank()).containsValue(size(3));
        }
    }),

    TwoPair("Two Pair", new Predicate<FunctionalList<Card>>() {
        @SuppressWarnings("unchecked")
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return cards.groupBy(rank()).containsValues(size(2), size(2));
        }
    }),

    Pair("Pair", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return cards.groupBy(rank()).containsValue(size(2));
        }
    }),

    HighCard("High Card", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return true;
        }
    });

    private final String name;
    private final Predicate<FunctionalList<Card>> criteria;

    Category(final String name, final Predicate<FunctionalList<Card>> criteria) {
        this.name = name;
        this.criteria = criteria;
    }

    public String categoryName() {
        return name;
    }

    public boolean matches(final FunctionalList<Card> cards) {
        return criteria.matches(cards);
    }

    public static FunctionalList<Category> categories() {
        return FunctionalList.of(Category.values());
    }

    private static Function<Card, Rank> rank() {
        return new Function<Card, Rank>() {
            @Override public Rank apply(final Card card) {
                return card.rank();
            }
        };
    }

    private static Function<Card, Suit> suit() {
        return new Function<Card, Suit>() {
            @Override public Suit apply(final Card card) {
                return card.suit();
            }
        };
    }

    private static Predicate<FunctionalList<Card>> size(final int n) {
        return new Predicate<FunctionalList<Card>>() {
            @Override public boolean matches(final FunctionalList<Card> list) {
                return list.size() >= n;
            }
        };
    }
}
