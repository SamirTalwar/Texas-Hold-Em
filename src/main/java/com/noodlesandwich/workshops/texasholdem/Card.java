package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.FunctionalList;
import com.noodlesandwich.workshops.functional.Predicate;

public final class Card {
    public static final Card NULL = new Card(Numbers.NULL);

    private final Numbers number;

    public Card(final String cardString) {
        this(Numbers.from(cardString.charAt(0)));
    }

    private Card(final Numbers number) {
        this.number = number;
    }

    public boolean hasTheSameNumberAs(final Card other) {
        return number == other.number;
    }

    private static enum Numbers {
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

        private static final FunctionalList<Numbers> NUMBERS = FunctionalList.of(values());

        private final char representation;

        Numbers(final char representation) {
            this.representation = representation;
        }

        public static Numbers from(final char character) {
            return NUMBERS.find(new Predicate<Numbers>() {
                @Override public boolean matches(final Numbers input) {
                    return character == input.representation;
                }
            });
        }
    }
}
