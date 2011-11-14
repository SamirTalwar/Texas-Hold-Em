package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.FunctionalList;
import com.noodlesandwich.workshops.functional.Predicate;

public final class Card {
    public static final Card NULL = new Card(Rank.NULL, Suit.NULL);

    private final Rank rank;
    private final Suit suit;

    public static Card lookup(final String cardString) {
        final Rank number = Rank.from(cardString.charAt(0));
        final Suit suit = Suit.from(cardString.charAt(1));
        return number == Rank.NULL || suit == Suit.NULL ? Card.NULL : new Card(number, suit);
    }

    private Card(final String cardString) {
        this(Rank.from(cardString.charAt(0)), Suit.from(cardString.charAt(1)));
    }

    private Card(final Rank number, final Suit suit) {
        this.rank = number;
        this.suit = suit;
    }

    public Rank rank() {
        return rank;
    }

    public Suit suit() {
        return suit;
    }

    @Override
    public String toString() {
        return "" + rank.representation + suit.representation;
    }

    public static enum Rank {
        NULL('\0'),

        _2('2'),
        _3('3'),
        _4('4'),
        _5('5'),
        _6('6'),
        _7('7'),
        _8('8'),
        _9('9'),
        _T('T'),
        _J('J'),
        _Q('Q'),
        _K('K'),
        _A('A');

        private final char representation;

        Rank(final char representation) {
            this.representation = representation;
        }

        public static Rank from(final char character) {
            return RANKS.find(new Predicate<Rank>() {
                @Override public boolean matches(final Rank input) {
                    return character == input.representation;
                }
            }, NULL);
        }
    }

    public static enum Suit {
        NULL('\0'),

        Spades('s'),
        Diamonds('d'),
        Clubs('c'),
        Hearts('h');

        private final char representation;

        Suit(final char representation) {
            this.representation = representation;
        }

        public static Suit from(final char character) {
            return SUITS.find(new Predicate<Suit>() {
                @Override public boolean matches(final Suit input) {
                    return character == input.representation;
                }
            }, NULL);
        }
    }

    public static final FunctionalList<Rank> RANKS = FunctionalList.of(Rank.values());

    public static final FunctionalList<Suit> SUITS = FunctionalList.of(Suit.values());
}
