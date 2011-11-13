package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.FunctionalList;

public final class Hand {
    private final FunctionalList<String> hand;

    public Hand(final String hand) {
        this.hand = FunctionalList.of(hand.split(" "));
    }

    public String rank() {
        return rankForPair(hand, "  ");
    }

    private static String rankForPair(final FunctionalList<String> hand, final String last) {
        return hand.isEmpty()
                   ? "High Card"
                   : last.charAt(0) == hand.head().charAt(0)
                       ? rankForTwoPair(hand.tail(), "  ")
                       : rankForPair(hand.tail(), hand.head());
    }

    private static String rankForTwoPair(final FunctionalList<String> hand, final String last) {
        return hand.isEmpty()
                   ? "Pair"
                   : last.charAt(0) == hand.head().charAt(0)
                       ? "Two Pair"
                       : rankForTwoPair(hand.tail(), hand.head());
    }
}
