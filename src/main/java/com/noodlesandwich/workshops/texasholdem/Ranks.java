package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.FunctionalList;

public class Ranks {
    public static String rank(final FunctionalList<Card> hand) {
        return rankForPair(hand, Card.NULL);
    }

    private static String rankForPair(final FunctionalList<Card> hand, final Card last) {
        return hand.isEmpty()
                   ? "High Card"
                   : last.hasTheSameNumberAs(hand.head())
                       ? rankForTwoPair(hand.tail(), Card.NULL)
                       : rankForPair(hand.tail(), hand.head());
    }

    private static String rankForTwoPair(final FunctionalList<Card> hand, final Card last) {
        return hand.isEmpty()
                   ? "Pair"
                   : last.hasTheSameNumberAs(hand.head())
                       ? "Two Pair"
                       : rankForTwoPair(hand.tail(), hand.head());
    }

}
