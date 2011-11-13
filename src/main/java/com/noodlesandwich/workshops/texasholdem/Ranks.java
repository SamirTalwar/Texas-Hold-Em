package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.FunctionalList;
import com.noodlesandwich.workshops.functional.Predicate;

public class Ranks {
    public static String rank(final FunctionalList<Card> hand) {
        return TWO_PAIR.matches(hand)
                   ? "Two Pair"
                   : PAIR.matches(hand)
                       ? "Pair"
                       : "High Card";
    }

    public static Predicate<FunctionalList<Card>> PAIR = new Predicate<FunctionalList<Card>>() {
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
    };

    public static Predicate<FunctionalList<Card>> TWO_PAIR = new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return rankForPair(cards, Card.NULL);
        }

        private boolean rankForPair(final FunctionalList<Card> hand, final Card last) {
            return hand.isEmpty()
                       ? false
                       : last.hasTheSameNumberAs(hand.head())
                           ? rankForTwoPair(hand.tail(), Card.NULL)
                           : false;
        }

        private boolean rankForTwoPair(final FunctionalList<Card> hand, final Card last) {
            return hand.isEmpty()
                       ? false
                       : last.hasTheSameNumberAs(hand.head())
                           ? true
                           : rankForTwoPair(hand.tail(), hand.head());
        }
    };
}
