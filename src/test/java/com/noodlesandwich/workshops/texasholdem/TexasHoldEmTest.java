package com.noodlesandwich.workshops.texasholdem;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public final class TexasHoldEmTest {
    @Ignore("pending implementation")
    @Test public void
    detects_a_hand_is_a_high_card() {
        final TexasHoldEm texasHoldEm = new TexasHoldEm();
        assertThat(texasHoldEm.rank("2c 5d 6c 8s Td Ks Ah"), is("High Card"));
    }
}
