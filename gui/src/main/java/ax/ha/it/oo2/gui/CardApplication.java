package ax.ha.it.oo2.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class CardApplication extends Application {
    private Scene scene;
    private Stage stageTemp;
    private Deck deck;
    private ServerHandler serverHandler = new ServerHandler();

    private Thread thread;

    @Override
    public void start(Stage stage) {
        scene = createStartingScene(stage);
        stage.setTitle("Deck Application");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stageTemp = stage;
    }

    public Scene createStartingScene(Stage stage) {
        HBox hBox = createStartingPane(stage);
        VBox gamePane = createBackgroundHeader("Card Application");
        gamePane.getChildren().addAll(hBox);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gamePane);
        borderPane.setBackground(setCardBackground());
        scene = new Scene(borderPane, 640, 408);
        return scene;
    }

    private VBox createBackgroundHeader(String header) {
        Label head = new Label(header);
        head.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(head);
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.setPadding(new Insets(80.0));
        return vBox;
    }

    private HBox createStartingPane(Stage stage) {
        Button startButton = new Button();
        startButton.setText("Start");
        startButton.setPadding(new Insets(20));
        serverHandler.setSearch("https://deckofcardsapi.com/api/deck/new/shuffle/");
        thread = new Thread(serverHandler);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        startButton.setOnAction(actionEvent -> {
            deck = serverHandler.getValue();
            scene = createCardScene();
            stage.setScene(scene);
        });
        Button endButton = createCloseButton();

        HBox movesPane = new HBox(20);
        movesPane.getChildren().addAll(startButton, endButton);
        movesPane.setAlignment(Pos.CENTER);
        movesPane.setPadding(new Insets(20.0));
        return movesPane;
    }

    private Scene createCardScene() {//When the deck is out of cards a new one will be created automatically
        Label labelHelp = new Label("You can also shuffle the deck multiple times");
        labelHelp.setFont(Font.font(15));
        BorderPane borderPane = new BorderPane();
        VBox gamePane = createBackgroundHeader("Select how many cards you want");
        borderPane.setCenter(gamePane);
        borderPane.setBackground(setCardBackground());
        Button shuffleButton = createShuffleButton(gamePane);
        Button backButton = new Button("Back to Main Menu");
        backButton.setPadding(new Insets(5));
        backButton.setOnAction(actionEvent -> {
            scene = createStartingScene(stageTemp);
            stageTemp.setScene(scene);
        });
        gamePane.getChildren().addAll(backButton, labelHelp, shuffleButton);
        createCardButtons(gamePane, borderPane);

        return new Scene(borderPane, 640, 408);
    }

    private Button createShuffleButton(VBox vbox) {
        Label label = new Label();
        Button shuffleButton = new Button("Shuffle the deck");
        shuffleButton.setPadding(new Insets(5));
        shuffleButton.setOnAction(actionEvent -> {
            label.setText("Shuffled deck");
            checkDeck();
            serverHandler.setSearch("https://deckofcardsapi.com/api/deck/" +
                    deck.getDeckId() + "/shuffle/?remaining=true");
            thread = new Thread(serverHandler);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            deck = serverHandler.getValue();
        });
        vbox.getChildren().addAll(label);
        return shuffleButton;
    }

    private void createCardButtons(VBox gamePane, BorderPane borderPane) {
        // create a text input dialog
        TextInputDialog td = new TextInputDialog();
        td.setHeaderText("How many cards (1-5)?");

        // create a button
        Button d = new Button("Click to select cards");
        d.setPadding(new Insets(6));
        // create an event handler
        EventHandler<ActionEvent> event = e -> {
            int choice = 0;
            do {
                // show the text input dialog
                td.showAndWait();
                try {
                    choice = Integer.parseInt(td.getEditor().getText());
                } catch (NumberFormatException ex) {
                    td.getEditor().setText(null);
                }
            } while (choice < 1 || choice > 5);
            handleCards(choice, borderPane);
        };
        d.setOnAction(event);

        gamePane.getChildren().add(d);

    }

    private Button createCloseButton() {
        Button endButton = new Button();
        endButton.setText("close application");
        endButton.setPadding(new Insets(20));
        endButton.setOnAction(actionEvent -> Platform.exit());
        return endButton;
    }

    private void checkDeck() {
        if (deck == null || deck.getRemaining() == 0) {
            serverHandler = new ServerHandler();
            serverHandler.setSearch("https://deckofcardsapi.com/api/deck/new/shuffle/");
            thread = new Thread(serverHandler);
            thread.start();
            try {
                thread.join();//wait for thread to finish
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            deck = serverHandler.getDeck();
        }
    }

    private boolean cardRemains(int choice) {
        return deck.getRemaining() >= choice;
    }

    private void handleCards(int cardChoice, BorderPane pane) {
        if (cardRemains(cardChoice)) {
            createThreadCards(cardChoice);
            deck = serverHandler.getDeck();

            Image[] images = getCardImages(deck);
            HBox cardView = createCardBoxView(images);

            VBox vbox = createBackgroundHeader("Got " + cardChoice + " cards: " + deck.getRemaining() + " remaining");
            Button endButton = createButtonForSelectingCards();
            pane.getChildren().remove(0);
            BorderPane borderPane = new BorderPane();
            borderPane.setTop(vbox);
            borderPane.setBottom(cardView);
            borderPane.setCenter(endButton);
            borderPane.setBackground(setCardBackground());
            scene = new Scene(borderPane, 640, 408);
            stageTemp.setScene(scene);
        }
    }

    private void createThreadCards(int cardChoice) {
        if (!thread.isAlive()) {
            serverHandler = new ServerHandler();
            serverHandler.setSearch("https://deckofcardsapi.com/api/deck/" +
                    deck.getDeckId() + "/draw/?count=" + cardChoice);
            thread = new Thread(serverHandler);
            thread.setDaemon(true);
            thread.start();

            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Button createButtonForSelectingCards() {
        Button endButton = new Button();
        endButton.setText("Go back to selection");
        endButton.setPadding(new Insets(20));
        endButton.setOnAction(actionEvent -> {
            scene = createCardScene();
            stageTemp.setScene(scene);

        });
        return endButton;
    }

    private HBox createCardBoxView(Image[] images) {
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.TOP_CENTER);
        for (Image image : images) {
            ImageView imageView = new ImageView(image);
            hBox.getChildren().addAll(imageView);
        }
        return hBox;
    }

    private Image[] getCardImages(Deck deck) {
        Image[] images = new Image[deck.getCards().length];
        for (int i = 0; i < deck.getCards().length; i++) {
            Image image = new Image(deck.getCards()[i].image(), 100, 150, false, false);
            images[i] = image;
        }
        return images;
    }

    private Background setCardBackground() {
        Image image = new Image("/background.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
        return new Background(backgroundImage);
    }

    public static void main(String[] args) {
        launch();
    }
}