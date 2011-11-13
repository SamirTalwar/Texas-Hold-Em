package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.FunctionalList;
import com.noodlesandwich.workshops.functional.Predicate;

public final class Card {
    public static final Card NULL = new Card(Number.NULL, Suit.NULL);

    private final Number number;
    private final Suit suit;

    public static Card lookup(final String cardString) {
        final Number number = Number.from(cardString.charAt(0));
        final Suit suit = Suit.from(cardString.charAt(1));
        return number == Number.NULL || suit == Suit.NULL ? Card.NULL : new Card(number, suit);
    }

    private Card(final String cardString) {
        this(Number.from(cardString.charAt(0)), Suit.from(cardString.charAt(1)));
    }

    private Card(final Number number, final Suit suit) {
        this.number = number;
        this.suit = suit;
    }

    public boolean hasTheSameNumberAs(final Card other) {
        return number == other.number;
    }

    @Override
    public String toString() {
        return "" + number.representation + suit.representation;
    }

    private static enum Number {
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

        private static final FunctionalList<Number> NUMBERS = FunctionalList.of(values());

        private final char representation;

        Number(final char representation) {
            this.representation = representation;
        }

        public static Number from(final char character) {
            return NUMBERS.find(new Predicate<Number>() {
                @Override public boolean matches(final Number input) {
                    return character == input.representation;
                }
            }, NULL);
        }
    }

    private static enum Suit {
        NULL('\0'),

        Spades('s'),
        Diamonds('d'),
        Clubs('c'),
        Hearts('h');

        private static final FunctionalList<Suit> SUITS = FunctionalList.of(values());

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
}
