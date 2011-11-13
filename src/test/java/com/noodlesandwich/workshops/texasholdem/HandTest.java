package com.noodlesandwich.workshops.texasholdem;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class HandTest {
    @Test public void
    detects_a_hand_is_a_high_card() {
        assertThat(new Hand("2c 5d 6c 8s Td Ks Ah").rank(), is("High Card"));
    }

    @Test public void
    detects_a_pair() {
        assertThat(new Hand("4d 8c 9s Td Th Qs Kc").rank(), is("Pair"));
    }

    @Test public void
    detects_two_pair() {
        assertThat(new Hand("3s 5c 5d 8h 9h 9d Jc").rank(), is("Two Pair"));
    }
}