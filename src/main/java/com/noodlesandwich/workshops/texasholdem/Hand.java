package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.Function;
import com.noodlesandwich.workshops.functional.FunctionalList;

import static com.noodlesandwich.workshops.texasholdem.Category.categories;

public final class Hand {
    private final FunctionalList<Card> hand;

    public Hand(final String hand) {
        this.hand = FunctionalList.of(hand.split(" ")).map(toCards());
    }

    public String category() {
        return categorise(categories());
    }

    public String categorise(final FunctionalList<Category> categories) {
        final Category category = categories.head();
        return category.matches(hand)
                   ? category.categoryName()
                   : categorise(categories.tail());
    }

    private static Function<String, Card> toCards() {
        return new Function<String, Card>() {
            @Override public Card apply(final String cardString) {
                return Card.lookup(cardString);
            }
        };
    }
}
