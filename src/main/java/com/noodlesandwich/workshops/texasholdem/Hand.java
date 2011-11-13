package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.Function;
import com.noodlesandwich.workshops.functional.FunctionalList;

public final class Hand {
    private final FunctionalList<Card> hand;

    public Hand(final String hand) {
        this.hand = FunctionalList.of(hand.split(" ")).map(toCards());
    }

    public String rank() {
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

    private static Function<String, Card> toCards() {
        return new Function<String, Card>() {
            @Override public Card apply(final String cardString) {
                return Card.lookup(cardString);
            }
        };
    }
}
