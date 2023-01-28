package ax.ha.it.oo2.gui;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONHandler {
    private static final ObjectMapper objectmapper = new ObjectMapper();

    public static Deck getDeck(String response) {
        Deck deck;
        try {
            deck = objectmapper.readValue(response, Deck.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return deck;
    }
}
