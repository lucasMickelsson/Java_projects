package ax.ha.it.oo2.gui;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javafx.concurrent.Task;

public class Deck {
    private final boolean success;
    private final String deck_id;
    private final boolean shuffled;
    private final Card[] cards;
    private final int remaining;

    @JsonCreator
    public Deck(@JsonProperty("success") boolean success, @JsonProperty("deck_id") String deck_id,
                @JsonProperty("shuffled") boolean shuffled, @JsonProperty("remaining") int remaining, @JsonProperty("cards") Card[] cards) {
        this.success = success;
        this.deck_id = deck_id;
        this.shuffled = shuffled;
        this.remaining = remaining;
        this.cards = cards;
    }

    public Card[] getCards() {
        return cards;
    }

    public String getDeckId() {
        return deck_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isShuffled() {
        return shuffled;
    }

    public int getRemaining() {
        return remaining;
    }


}
