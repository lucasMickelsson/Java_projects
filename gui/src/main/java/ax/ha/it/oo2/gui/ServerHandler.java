package ax.ha.it.oo2.gui;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerHandler extends Task<Deck> {
    BufferedReader inputStream;
    HttpURLConnection connection;
    private Deck deck;
    private String search;

    public void setSearch(String search) {
        this.search = search;
    }

    private int setUpServer(String search) {
        int status = 0;
        try {
            URL url = new URL(search);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            status = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    @Override
    synchronized protected Deck call() throws Exception {
        StringBuilder output = new StringBuilder();
        try {
            if (setUpServer(search) == 200) {
                inputStream = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String nextLine;
                while ((nextLine = inputStream.readLine()) != null) {
                    output.append(nextLine);
                }
            } else {
                inputStream = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        deck = JSONHandler.getDeck(output.toString());

        return deck;
    }

    public Deck getDeck() {
        return deck;
    }
}