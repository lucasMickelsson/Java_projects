package ax.ha.it.oo2.game.plantsvszombies;

import plants.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SidebarCard extends GameElements {
    private final int cost;
    private final int plantID;
    private static int cardSelected;
    private static boolean isSelected = false;

    private static final String[] cards =
            {"/peashooterCard.png", "/sunflowerCard.png", "/repeaterCard.png", "/wallnutCard.png"};

    public SidebarCard(int x, int y, String path, int width, int height, int cost, int plantID) {
        super(x, y, path, width, height);
        this.cost = cost;
        this.plantID = plantID;
    }

    public static boolean IsSelected() {
        return isSelected;
    }

    public static void setIsSelected(boolean isSelected) {
        SidebarCard.isSelected = isSelected;
    }

    public static int getCost(int ID) {
        return switch (ID) {
            case 1 -> 100;
            case 2, 4 -> 50;
            case 3 -> 200;
            default -> 0;
        };
    }

    public static int getCardSelected() {
        return cardSelected;
    }

    public static void placePlant(GridPane lawn, AnchorPane anchor, int ID, int row, int col) {
        switch (ID) {
            case 1 -> {
                PeaShooter peaShooter = new PeaShooter(Plant.getXCoordinate(col), Plant.getYCoordinate(row), row, col);
                peaShooter.attack(anchor);
                peaShooter.makeImage(lawn);
                GamePlayController.allPlants.add(peaShooter);
            }
            case 2 -> {
                //Sunflower
                Sunflower sunflower = new Sunflower(Plant.getXCoordinate(col), Plant.getYCoordinate(row), row, col);
                sunflower.produceSun(anchor);
                sunflower.makeImage(lawn);
                GamePlayController.allPlants.add(sunflower);
            }

            case 3 -> {
                //repeater
                Repeater repeater = new Repeater(Plant.getXCoordinate(col), Plant.getYCoordinate(row), row, col);
                repeater.attack(anchor);
                repeater.makeImage(lawn);
                GamePlayController.allPlants.add(repeater);
            }
            case 4 -> {
                // Wallnut
                Wallnut wallnut = new Wallnut(Plant.getXCoordinate(col), Plant.getYCoordinate(row), row, col);
                wallnut.makeImage(lawn);
                GamePlayController.allPlants.add(wallnut);
            }
        }
    }

    public static void createCards(Pane pane, int num, int end, int startY) {
        int y = startY;
        for (int i = num; i < end; i++) {
            SidebarCard card = new SidebarCard(50, y, cards[i], 120, 80, getCost(i + 1), i + 1);
            card.makeImage(pane);
            card.imageView.setOnMouseClicked(mouseEvent -> {
                if (GamePlayController.sunCount >= card.cost && !GamePlayController.menuIsUp) {
                    card.imageView.setFitWidth(130);
                    card.imageView.setFitHeight(90);
                    cardSelected = card.plantID;
                    isSelected = true;
                }
            });
            GamePlayController.cards.add(card);
            y += 100;
        }
    }

    public static void setCardsOnScreen(Pane pane, int levelNumber) {
        if (levelNumber == 1) {
            createCards(pane, 0, 2, 100);
        } else {
            createCards(pane, 2, 4, 300);
        }
    }
}
