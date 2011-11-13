package com.noodlesandwich.workshops.texasholdem;

public final class TexasHoldEm {
    public String rank(final String hand) {
        return new Hand(hand).rank();
    }
}
