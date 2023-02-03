package ax.ha.it.oo1.cards;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    public void testEqualsAndHashCode() {
        Card instance1 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card instance2 = new Card(Card.Rank.ACE, Card.Suit.HEARTS);
        Card instance3 = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        assertNotEquals(instance1, instance2);
        assertEquals(instance1, instance3);

        // Contract for equals: If the objects are equal they must also share
        // the same hashcode
        assertEquals(instance1.hashCode(), instance3.hashCode());
    }

    @Test
    public void testCopyConstructor() {
        Card original = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        Card copy = new Card(original);

        // The copy should be considered equal...
        assertEquals(original, copy);
        // But not be the same object as the original
        assertNotSame(original, copy);
    }

    @Test
    public void testToString() {
        Card aceOfSpades = new Card(Card.Rank.ACE, Card.Suit.SPADES);
        assertEquals("\u2660A", aceOfSpades.toString());

        Card tenOfHearts = new Card(Card.Rank.TEN, Card.Suit.HEARTS);
        assertEquals("\u266510", tenOfHearts.toString());

        Card jackOfClubs = new Card(Card.Rank.JACK, Card.Suit.CLUBS);
        assertEquals("\u2663J", jackOfClubs.toString());

        Card kingOfDiamonds = new Card(Card.Rank.KING, Card.Suit.DIAMONDS);
        assertEquals("\u2666K", kingOfDiamonds.toString());

    }

}
