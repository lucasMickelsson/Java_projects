package ax.ha.it.oo1.cards;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    public void testEqualsAndHashCode() {
        // Same cards in two decks
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        deck1.shuffle();
        deck2.shuffle();

        // In theory, we could get two identically shuffled decks even after
        // shuffling both decks. As there are 52! =
        // 80658175170943878571660636856403766975289505440883277824000000000000
        // different ways to shuffle a deck we may be able to live with
        // that particular risk.
        assertEquals(deck1, deck2);

        // Contract for equals: If the objects are equal they must also share
        // the same hashcode
        assertEquals(deck1.hashCode(), deck2.hashCode());
    }

    /**
     * Test of equals method, of class Deck.
     */
    @Test
    public void testEqualsWithTwoAceOfSpades1() {
        // Replace Two of Spades with Ace of Spades
        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        // Make deck2 an illegal deck, with one duplicate card
        try {
            Field f = deck2.getClass().getDeclaredField("cards");
            f.setAccessible(true);
            Card[] cards = (Card[]) f.get(deck2);
            cards[1] = new Card(Card.Rank.ACE, Card.Suit.SPADES);  // Extra Ace of Spades
        } catch (IllegalArgumentException | IllegalAccessException
                 | NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
        }

        assertNotEquals(deck1, deck2);
        assertNotEquals(deck2, deck1);
        deck1.shuffle();
        deck2.shuffle();
        assertNotEquals(deck1, deck2);
        assertNotEquals(deck2, deck1);
    }

    /**
     * Test of equals method, of class Deck.
     */
    @Test
    public void testEqualsWithTwoAceOfSpades2() {

        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        // Make deck2 an illegal deck, with one extra card
        try {
            Field f = deck2.getClass().getDeclaredField("cards");
            f.setAccessible(true);

            Card[] cards = new Card[53];

            int counter = 0;
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values()) {
                    cards[counter] = new Card(rank, suit);
                    counter++;
                }
            }

            cards[52] = new Card(Card.Rank.ACE, Card.Suit.SPADES);  // Add en extra Ace of Spades

            f.set(deck2, cards);

        } catch (IllegalArgumentException | IllegalAccessException
                 | NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
        }

        assertNotEquals(deck1, deck2);
        assertNotEquals(deck2, deck1);
        deck1.shuffle();
        deck2.shuffle();
        assertNotEquals(deck1, deck2);
        assertNotEquals(deck2, deck1);
    }

    /**
     * Test of equals method, of class Deck.
     */
    @Test
    public void testEqualsWithTwoAceOfSpades3() {

        Deck deck1 = new Deck();
        Deck deck2 = new Deck();

        // Make deck1 and deck2 contain the same cards but with different amounts
        try {
            Field f = deck2.getClass().getDeclaredField("cards");
            f.setAccessible(true);
            Card[] cards1 = new Card[3];
            Card[] cards2 = new Card[3];
            // Deck 1: 2*Ace of Spades, 1*Ace of Hearts
            cards1[0] = new Card(Card.Rank.ACE, Card.Suit.SPADES);
            cards1[1] = new Card(Card.Rank.ACE, Card.Suit.SPADES);
            cards1[2] = new Card(Card.Rank.ACE, Card.Suit.HEARTS);

            // Deck 2: 1*Ace of Spades, 2*Ace of Hearts
            cards2[0] = new Card(Card.Rank.ACE, Card.Suit.SPADES);
            cards2[1] = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
            cards2[2] = new Card(Card.Rank.ACE, Card.Suit.HEARTS);

            f.set(deck1, cards1);
            f.set(deck2, cards2);

        } catch (IllegalArgumentException | IllegalAccessException
                 | NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
        }

        assertNotEquals(deck1, deck2);
        assertNotEquals(deck2, deck1);
        deck1.shuffle();
        deck2.shuffle();
        assertNotEquals(deck1, deck2);
        assertNotEquals(deck2, deck1);
    }


    /**
     * Test of copy constructor
     */
    @Test
    public void testCopyConstructor() {
        Deck original = new Deck();
        Deck copy = new Deck(original);

        // Not the same object
        assertNotSame(original, copy);

        // But considered equal
        assertEquals(original, copy);

        // And deep-copied
        try {
            Field f = original.getClass().getDeclaredField("cards");
            f.setAccessible(true);
            Card[] cards1 = (Card[]) f.get(original);
            Card[] cards2 = (Card[]) f.get(copy);
            assertEquals(cards1.length, cards2.length);

            for (int i = 0; i < cards1.length; i++) {
                // Not the same cards...
                assertNotSame(cards1[i], cards2[i]);
                // But the same card values
                assertEquals(cards1[i], cards2[i]);
            }
        } catch (IllegalArgumentException | IllegalAccessException
                 | NoSuchFieldException | SecurityException ex) {
            ex.printStackTrace();
        }
    }
}