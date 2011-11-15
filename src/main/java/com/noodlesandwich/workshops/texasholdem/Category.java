package com.noodlesandwich.workshops.texasholdem;

import com.noodlesandwich.workshops.functional.Function;
import com.noodlesandwich.workshops.functional.FunctionalList;
import com.noodlesandwich.workshops.functional.FunctionalMap;
import com.noodlesandwich.workshops.functional.Predicate;
import com.noodlesandwich.workshops.texasholdem.Card.Rank;
import com.noodlesandwich.workshops.texasholdem.Card.Suit;

public enum Category {
    StraightFlush("Straight Flush", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return cards.subListsOfSize(5).contains(new Predicate<FunctionalList<Card>>() {
                @Override public boolean matches(final FunctionalList<Card> groupedCards) {
                    return areConsecutive(groupedCards.map(rank()), Card.RANKS) && areSame(groupedCards.map(suit()));
                }
            });
        }
    }),

    FourOfAKind("Four of a Kind", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return cards.groupBy(rank()).containsValue(size(4));
        }
    }),

    FullHouse("Full House", new Predicate<FunctionalList<Card>>() {
        @SuppressWarnings("unchecked")
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return cards.groupBy(rank()).containsValues(size(3), size(2));
        }
    }),

    Flush("Flush", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return cards.groupBy(suit()).containsValue(size(5));
        }
    }),

    Straight("Straight", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            final FunctionalMap<Rank, Card> cardsByRank = cards.groupBy(rank());
            return Card.RANKS.subListsOfSize(5).contains(new Predicate<FunctionalList<Rank>>() {
                @Override public boolean matches(final FunctionalList<Rank> ranks) {
                    return cardsByRank.containsKeys(ranks);
                }
            });
        }
    }),

    ThreeOfAKind("Three of a Kind", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return cards.groupBy(rank()).containsValue(size(3));
        }
    }),

    TwoPair("Two Pair", new Predicate<FunctionalList<Card>>() {
        @SuppressWarnings("unchecked")
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return cards.groupBy(rank()).containsValues(size(2), size(2));
        }
    }),

    Pair("Pair", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return cards.groupBy(rank()).containsValue(size(2));
        }
    }),

    HighCard("High Card", new Predicate<FunctionalList<Card>>() {
        @Override public boolean matches(final FunctionalList<Card> cards) {
            return true;
        }
    });

    private final String name;
    private final Predicate<FunctionalList<Card>> criteria;

    Category(final String name, final Predicate<FunctionalList<Card>> criteria) {
        this.name = name;
        this.criteria = criteria;
    }

    public String categoryName() {
        return name;
    }

    public boolean matches(final FunctionalList<Card> cards) {
        return criteria.matches(cards);
    }

    public static FunctionalList<Category> categories() {
        return FunctionalList.of(Category.values());
    }

    private static <T> boolean areConsecutive(final FunctionalList<T> list, final FunctionalList<T> order) {
        return list.isEqualTo(order.dropWhile(not(equalTo(list.head()))).take(list.size()));
    }

    private static <T> boolean areSame(final FunctionalList<T> list) {
        return list.all(equalTo(list.head()));
    }

    private static Function<Card, Rank> rank() {
        return new Function<Card, Rank>() {
            @Override public Rank apply(final Card card) {
                return card.rank();
            }
        };
    }

    private static Function<Card, Suit> suit() {
        return new Function<Card, Suit>() {
            @Override public Suit apply(final Card card) {
                return card.suit();
            }
        };
    }

    private static Predicate<FunctionalList<Card>> size(final int n) {
        return new Predicate<FunctionalList<Card>>() {
            @Override public boolean matches(final FunctionalList<Card> list) {
                return list.size() >= n;
            }
        };
    }

    private static <T> Predicate<T> equalTo(final T value) {
        return new Predicate<T>() {
            @Override public boolean matches(final T input) {
                return value.equals(input);
            }
        };
    }

    private static <T> Predicate<T> not(final Predicate<T> predicate) {
        return new Predicate<T>() {
            @Override public boolean matches(final T input) {
                return !predicate.matches(input);
            }
        };
    }
}
