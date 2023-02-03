package ax.ha.it.oo1.cards;

import java.util.Objects;

public record Card(ax.ha.it.oo1.cards.Card.Rank rank, ax.ha.it.oo1.cards.Card.Suit suit) {

    enum Suit {
        HEARTS('\u2665'), SPADES('\u2660'),
        CLUBS('\u2663'), DIAMONDS('\u2666');

        private final char symbol;

        Suit(char symbol) {
            this.symbol = symbol;
        }

        public char getSymbol() {
            return symbol;
        }

    }

    ;

    enum Rank {

        ACE(1, "A"), TWO(2), THREE(3), FOUR(4), FIVE(5),
        SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11, "J"),
        QUEEN(12, "Q"), KING(13, "K");

        private final int value;
        private final String string;

        Rank(int value) {
            this.value = value;
            this.string = Integer.toString(value);
        }

        Rank(int value, String string) {
            this.value = value;
            this.string = string;
        }

        public int getValue() {
            return value;
        }

        public String getString() {
            return string;
        }
    }

    public Card(Card original) {
        this(original.rank, original.suit);
    }

    @Override
    public String toString() {
        return "" + suit.symbol + rank.string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
