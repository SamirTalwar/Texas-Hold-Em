package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.FunctionalList;
import com.noodlesandwich.workshops.functional.Predicate;

public class Ranks {
    public static String rank(final FunctionalList<Card> hand) {
        return rank(hand, RANKS);
    }

    public static String rank(final FunctionalList<Card> hand, final FunctionalList<Rank> ranks) {
        final Rank currentRank = ranks.head();
        return currentRank.matches(hand)
                   ? currentRank.name()
                   : rank(hand, ranks.tail());
    }

    private static final class Rank {
        private final String name;
        private final Predicate<FunctionalList<Card>> criteria;

        public Rank(final String name, final Predicate<FunctionalList<Card>> criteria) {
            this.name = name;
            this.criteria = criteria;
        }

        public String name() {
            return name;
        }

        public boolean matches(final FunctionalList<Card> cards) {
            return criteria.matches(cards);
        }
    }

    private static Rank highCard = new Rank("High Card", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return true;
        }
    });

    private static Rank pair = new Rank("Pair", new Predicate<FunctionalList<Card>>() {
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
    });

    private static Rank twoPair = new Rank("Two Pair", new Predicate<FunctionalList<Card>>() {
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
    });

    private static final FunctionalList<Rank> RANKS = FunctionalList.of(twoPair, pair, highCard);
}
