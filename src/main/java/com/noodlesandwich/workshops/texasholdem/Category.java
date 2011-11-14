package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.Function;
import com.noodlesandwich.workshops.functional.FunctionalList;
import com.noodlesandwich.workshops.functional.Predicate;
import com.noodlesandwich.workshops.texasholdem.Card.Rank;

public enum Category {
    ThreeOfAKind("Three of a Kind", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return cards.groupBy(number()).hasItem(size(3));
        }
    }),

    TwoPair("Two Pair", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return categoryForPair(cards, Card.NULL);
        }

        private boolean categoryForPair(final FunctionalList<Card> hand, final Card last) {
            return hand.isEmpty()
                       ? false
                       : last.hasTheSameNumberAs(hand.head())
                           ? categoryForTwoPair(hand.tail(), Card.NULL)
                           : categoryForPair(hand.tail(), hand.head());
        }

        private boolean categoryForTwoPair(final FunctionalList<Card> hand, final Card last) {
            return hand.isEmpty()
                       ? false
                       : last.hasTheSameNumberAs(hand.head())
                           ? true
                           : categoryForTwoPair(hand.tail(), hand.head());
        }
    }),

    Pair("Pair", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return categoryForPair(cards, Card.NULL);
        }

        private boolean categoryForPair(final FunctionalList<Card> hand, final Card last) {
            return hand.isEmpty()
                       ? false
                       : last.hasTheSameNumberAs(hand.head())
                           ? true
                           : categoryForPair(hand.tail(), hand.head());
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

    private static Function<Card, Rank> number() {
        return new Function<Card, Rank>() {
            @Override public Rank apply(final Card card) {
                return card.rank();
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
