package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.Function;
import com.noodlesandwich.workshops.functional.FunctionalList;

import static com.noodlesandwich.workshops.texasholdem.Rank.ranks;

public final class Hand {
    private final FunctionalList<Card> hand;

    public Hand(final String hand) {
        this.hand = FunctionalList.of(hand.split(" ")).map(toCards());
    }

    public String rank() {
        return rank(ranks());
    }

    public String rank(final FunctionalList<Rank> ranks) {
        final Rank currentRank = ranks.head();
        return currentRank.matches(hand)
                   ? currentRank.rankName()
                   : rank(ranks.tail());
    }

    private static Function<String, Card> toCards() {
        return new Function<String, Card>() {
            @Override public Card apply(final String cardString) {
                return Card.lookup(cardString);
            }
        };
    }
}
