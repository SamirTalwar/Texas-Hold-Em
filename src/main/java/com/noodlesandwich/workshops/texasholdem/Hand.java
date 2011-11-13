package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.Function;
import com.noodlesandwich.workshops.functional.FunctionalList;

public final class Hand {
    private final FunctionalList<Card> hand;

    public Hand(final String hand) {
        this.hand = FunctionalList.of(hand.split(" ")).map(toCards());
    }

    public String rank() {
        return Ranks.rank(hand);
    }

    private static Function<String, Card> toCards() {
        return new Function<String, Card>() {
            @Override public Card apply(final String cardString) {
                return Card.lookup(cardString);
            }
        };
    }
}
