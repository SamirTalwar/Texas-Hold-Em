package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.FunctionalList;

public final class Hand {
    private final FunctionalList<String> hand;

    public Hand(final String hand) {
        this.hand = FunctionalList.of(hand.split(" "));
    }

    public String rank() {
        return rank(hand, "  ") ? "Pair" : "High Card";
    }

    private static boolean rank(final FunctionalList<String> hand, final String last) {
        return hand.isEmpty()
                   ? false
                   : last.charAt(0) == hand.head().charAt(0)
                       ? true
                       : rank(hand.tail(), hand.head());
    }
}
