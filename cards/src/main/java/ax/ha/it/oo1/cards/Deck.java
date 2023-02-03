package ax.ha.it.oo1.cards;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Deck {

    private final Card[] cards;

    public Deck() {
        cards = new Card[52];
        int counter = 0;
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards[counter] = new Card(rank, suit);
                counter++;
            }
        }
    }

    public Deck(Deck original) {
        cards = new Card[original.cards.length];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new Card(original.cards[i]);
        }
    }

    public void displayContents() {

        for (Card card : cards) {
            System.out.println(card.rank().getValue() + " of " + card.suit().getSymbol());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Deck deck = (Deck) o;
        if (deck.cards.length == cards.length) {
            Card[] cardTemp = new Card[deck.cards.length];
            System.arraycopy(deck.cards, 0, cardTemp, 0, deck.cards.length);// we make a copy of the array to be checked

            boolean cardExists;
            for (int i = 0; i < cards.length; i++) {
                cardExists = false;
                Card card = cards[i];
                for (int j = i; j < cardTemp.length; j++) {
                    if (cardTemp[j].rank() == card.rank() && cardTemp[j].suit() == card.suit()) {
                        cardExists = true;
                        swap(cardTemp, j, i);// every card we find is put behind variable i index
                        break;
                    }
                }
                if (!cardExists) { //we return false if we didn't find the card in the array
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private void swap(Card[] old, int oldItem, int swapItem) {
        Card card = old[oldItem];
        old[oldItem] = old[swapItem];
        old[swapItem] = card;
    }

    @Override
    public int hashCode() {
        int result = 17;
        for (Card card : cards) {
            result = result + Objects.hash(card);
        }
        return result;
    }

    public void shuffle() {
        // Option #1: Create a List and utilize the built-in shuffle method
        // Collections.shuffle(Arrays.asList(cards));

        // Option #2: Implement your own shuffle, taking care to avoid the
        // incorrect "swap each card starting from the beginning" approach
        // https://blog.codinghorror.com/the-danger-of-naivete/
        // => Knuth-Fisher-Yates shuffle algorithm

        Random random = new Random();
        for (int i = cards.length - 1; i > 0; i--) {
            int n = random.nextInt(i + 1);
            Card temp = cards[i];
            cards[i] = cards[n];
            cards[n] = temp;
        }
    }

    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + Arrays.toString(cards) +
                '}';
    }
}
