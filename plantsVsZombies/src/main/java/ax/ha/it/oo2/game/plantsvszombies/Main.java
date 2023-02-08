package ax.ha.it.oo2.game.plantsvszombies;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class Main extends Application {
    public static Stage stageTemp;
    static MediaPlayer mediaPlayer;

    @Override
    public void start(Stage stage) throws IOException {
        addMusic(); //https://github.com/lwd-temp/jspvz/tree/master/images/Zombies
        VBox vBox = createStartScene();
        vBox.setBackground(setMenuBackground());
        Scene scene = new Scene(vBox, 1024, 600);
        stage.setTitle("Plant vs Zombies");
        stage.setResizable(false);
        stage.setScene(scene);
        stageTemp = stage;
        stage.show();
    }

    public void addMusic() {
        Media sound = new Media(Paths.get("../plantsVsZombies\\src\\main\\resources\\PlantsVsZombiesSound.mp3").toUri().toString());
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public VBox createStartScene() {
        Label label = new Label();
        ImageView imageViewStart = new ImageView(new Image("/startgame.png", 234, 61, false, false));
        imageViewStart.setOnMouseClicked(actionEvent -> {
            try {
                startGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        ImageView imageViewExit = new ImageView(new Image("/exit.png"));
        imageViewExit.setOnMouseClicked(actionEvent -> Platform.exit());

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));
        vbox.getChildren().addAll(imageViewStart, imageViewExit, label);
        return vbox;
    }

    private Background setMenuBackground() {
        Image image = new Image("/menu.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
        return new Background(backgroundImage);
    }

    public void startGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Gameplay.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stageTemp.setScene(scene);

    }

    public static void main(String[] args) {
        launch();
    }
}