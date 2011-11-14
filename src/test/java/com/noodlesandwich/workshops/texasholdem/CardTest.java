package com.noodlesandwich.workshops.texasholdem;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;

public final class CardTest {
    @Test public void
    cards_can_be_converted_to_strings() {
        assertThat(Card.lookup("Ks"), hasToString("Ks"));
    }

    @Test public void
    returns_a_null_card_if_it_cant_find_the_number_specified() {
        assertThat(Card.lookup("Xs"), is(Card.NULL));
    }

    @Test public void
    returns_a_null_card_if_it_cant_find_the_suit_specified() {
        assertThat(Card.lookup("Ax"), is(Card.NULL));
    }
}
