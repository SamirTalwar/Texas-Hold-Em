package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.FunctionalList;
import com.noodlesandwich.workshops.functional.Predicate;

public enum Rank {
    twoPair("Two Pair", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return rankForPair(cards, Card.NULL);
        }

        private boolean rankForPair(final FunctionalList<Card> hand, final Card last) {
            return hand.isEmpty()
                       ? false
                       : last.hasTheSameNumberAs(hand.head())
                           ? rankForTwoPair(hand.tail(), Card.NULL)
                           : rankForPair(hand.tail(), hand.head());
        }

        private boolean rankForTwoPair(final FunctionalList<Card> hand, final Card last) {
            return hand.isEmpty()
                       ? false
                       : last.hasTheSameNumberAs(hand.head())
                           ? true
                           : rankForTwoPair(hand.tail(), hand.head());
        }
    }),

    pair("Pair", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return rankForPair(cards, Card.NULL);
        }

        private boolean rankForPair(final FunctionalList<Card> hand, final Card last) {
            return hand.isEmpty()
                       ? false
                       : last.hasTheSameNumberAs(hand.head())
                           ? true
                           : rankForPair(hand.tail(), hand.head());
        }
    }),

    highCard("High Card", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return true;
        }
    });

    private final String name;
    private final Predicate<FunctionalList<Card>> criteria;

    Rank(final String name, final Predicate<FunctionalList<Card>> criteria) {
        this.name = name;
        this.criteria = criteria;
    }

    public String rankName() {
        return name;
    }

    public boolean matches(final FunctionalList<Card> cards) {
        return criteria.matches(cards);
    }

    public static FunctionalList<Rank> ranks() {
        return FunctionalList.of(Rank.values());
    }
}
