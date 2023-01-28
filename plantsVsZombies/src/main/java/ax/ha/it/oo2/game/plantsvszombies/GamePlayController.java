package ax.ha.it.oo2.game.plantsvszombies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class GamePlayController {
    public AnchorPane GamePlayRoot;
    public ImageView lawnImage;
    public Label sunCountLabel;
    public GridPane lawn_grid;
    public Level level = null;
    public static List<Plant> allPlants;
    public static List<Zombie> allZombies;
    public static List<Zombie> spawnedZombies;
    public static List<Timeline> allAnimations;
    public static List<SidebarCard> cards;
    public static int killedZombies;
    public Label levelNumber;
    public ImageView gameMenu;
    public ImageView GameMenuLoader;
    public Button startButton;
    public AnchorPane pauseMenu;
    public Label zombieCounter;
    private Timeline zombiespawner;
    public static int sunCount = 25;
    public static boolean gameStatus = true;
    public static boolean menuIsUp = false;

    @FXML
    public void getGridPosition(MouseEvent mouseEvent) {
        if (startButton.isDisabled()) {
            Node node = (Node) mouseEvent.getSource();
            Integer row = GridPane.getRowIndex(node);
            Integer col = GridPane.getColumnIndex(node);

            if (!checkOccupiedPosition(row, col) && sunCount >= SidebarCard.getCost(SidebarCard.getCardSelected())
                    && SidebarCard.IsSelected() && gameStatus && !menuIsUp) {
                SidebarCard.placePlant(lawn_grid, GamePlayRoot, SidebarCard.getCardSelected(), row, col);
                sunCount = sunCount - SidebarCard.getCost(SidebarCard.getCardSelected());
                cards.get(SidebarCard.getCardSelected() - 1).imageView.setFitHeight(80);
                cards.get(SidebarCard.getCardSelected() - 1).imageView.setFitWidth(120);
                SidebarCard.setIsSelected(false);
            }
        }
    }

    public boolean checkOccupiedPosition(int row, int col) {
        for (Plant plant : allPlants) {
            if (plant.row == row && plant.col == col) {
                return true;
            }
        }
        return false;
    }

    @FXML
    public void start() {
        startButton.setDisable(true);
        startButton.setVisible(false);
        level = new Level(1);
        allPlants = new ArrayList<>();

        allZombies = level.getZombieList();
        spawnedZombies = new ArrayList<>();
        allAnimations = new ArrayList<>();
        cards = new ArrayList<>();
        sunCountLabel.setText(String.valueOf(sunCount));
        SidebarCard.setCardsOnScreen(GamePlayRoot, level.getLevelNumber());
        gameProgress();
        createLawnMower();
        sunSpawner();
        startZombieSpawner(23);
    }

    @FXML
    public void loadGameMenu() throws IOException {
        if (startButton.isDisabled()) {
            stopAnimations();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/GameMenu.fxml"));
            Parent parent = fxmlLoader.load();
            GamePlayRoot.getChildren().addAll(parent);
            menuIsUp = true;
        }
    }

    public void createLawnMower() {
        int y = 100;
        for (int i = 0; i < 5; i++) {
            LawnMower lawnMower = new LawnMower(260, y);
            lawnMower.makeImage(GamePlayRoot);
            lawnMower.checkZombie();
            y += 100;
        }
    }

    public void sunSpawner() {
        Random random = new Random();
        Timeline suns = new Timeline(new KeyFrame(Duration.seconds(10), actionEvent -> {
            int x = random.nextInt(300, 900);
            Sun sun = new Sun(x, 0);
            sun.makeImage(GamePlayRoot);
            sun.move();
            sun.imageView.setOnMouseClicked(mouseEvent -> {
                sun.imageView.setVisible(false);
                sun.imageView.setDisable(true);
                sunCount += 25;
            });
        }));
        suns.setCycleCount(Animation.INDEFINITE);
        suns.play();
        allAnimations.add(suns);
    }

    public void startZombieSpawner(int time) {
        //Zombie animations
        zombiespawner = new Timeline(new KeyFrame(Duration.seconds(time), actionEvent -> {
            try {
                Zombie zombie = allZombies.get(0);
                allZombies.remove(0);
                zombie.makeImage(GamePlayRoot);
                zombie.moveZombie();
                spawnedZombies.add(zombie);
            } catch (IndexOutOfBoundsException e) {
                zombiespawner.stop();
            }
        }));
        zombiespawner.setCycleCount(Timeline.INDEFINITE);
        zombiespawner.play();
        allAnimations.add(zombiespawner);
    }

    public void stopAnimations() {
        for (Timeline timeline : allAnimations) {
            timeline.stop();
        }
    }

    public void resumeAnimations() {
        for (Timeline timeline : allAnimations) {
            timeline.play();
        }
        pauseMenu.setDisable(true);
        pauseMenu.setVisible(false);
        menuIsUp = false;
    }

    public void updateLabels() {
        sunCountLabel.setText(String.valueOf(sunCount));
        zombieCounter.setText(String.valueOf(killedZombies));
        levelNumber.setText("Level: " + level.getLevelNumber());
    }

    public void gameProgress() {
        Timeline gameProgressCheck = new Timeline(new KeyFrame(Duration.millis(5), actionEvent -> {
            updateLabels();
            if (!gameStatus) {
                gameLost();
                gameStatus = true;
                sunCount = 25;
            } else if (level.getLevelNumber() == 1 && killedZombies == 10) {
                killedZombies = 0;
                level = new Level(2);
                allZombies = level.getZombieList();
                startZombieSpawner(3);
            } else if (level.getLevelNumber() == 2 && killedZombies == 15) {
                killedZombies = 0;
                level = new Level(3);
                allZombies = level.getZombieList();
                SidebarCard.setCardsOnScreen(GamePlayRoot, level.getLevelNumber());
                startZombieSpawner(2);
            } else if (level.getLevelNumber() == 3 && killedZombies == 20) {
                killedZombies = 0;
                level = new Level(4);
                allZombies = level.getZombieList();
                startZombieSpawner(2);
            } else if (level.getLevelNumber() == 4 && killedZombies == 25) {
                killedZombies = 0;
                level = new Level(5);
                allZombies = level.getZombieList();
                startZombieSpawner(2);
            } else if (level.getLevelNumber() == 5 && killedZombies == 30) {
                gameWin();
                gameStatus = true;
                sunCount = 25;
            }
        }));
        gameProgressCheck.setCycleCount(Animation.INDEFINITE);
        gameProgressCheck.play();
        allAnimations.add(gameProgressCheck);
    }

    public void restartGame() {
        Main main = new Main();
        try {
            main.startGame();
            sunCount = 25;
            killedZombies=0;
            menuIsUp = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void mainMenu() {
        Main.mediaPlayer.stop();
        Main main = new Main();
        try {
            main.start(Main.stageTemp);
            sunCount = 25;
            menuIsUp = false;
            killedZombies=0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void gameWin() {
        stopAnimations();
        ImageView imageView = new ImageView(new Image("Win.png", 564, 470, false, false));
        imageView.setX(232);
        imageView.setY(1);
        createQuitButton(imageView);
    }

    public void gameLost() {
        stopAnimations();
        ImageView imageView = new ImageView(new Image("ZombiesAteYourBrains.png"));
        imageView.setX(232);
        imageView.setY(1);
        createQuitButton(imageView);
    }

    public void createQuitButton(ImageView imageView) {
        ImageView quitGame = new ImageView(new Image("returnTomainMenu.png", 150, 80, false, false));
        quitGame.setLayoutX(400);
        quitGame.setLayoutY(450);
        quitGame.setOnMouseClicked(actionEvent -> mainMenu());
        GamePlayRoot.getChildren().addAll(imageView, quitGame);
    }

}