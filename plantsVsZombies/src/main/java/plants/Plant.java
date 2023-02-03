package plants;

import ax.ha.it.oo2.game.plantsvszombies.GameElements;
import ax.ha.it.oo2.game.plantsvszombies.GamePlayController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public abstract class Plant extends GameElements {

    protected int hp;
    protected int row;
    protected int col;

    public Plant(int x, int y, int hp, String path, int row, int col, int width, int height) {
        super(x, y, path, width, height);
        this.row = row;
        this.col = col;
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void makeImage(GridPane gridPane) {
        imageView = new ImageView();
        Image image = new Image(path, width, height, false, false);
        imageView.setImage(image);
        gridPane.add(imageView, col, row, 1, 1);
    }

    public static int getYCoordinate(int row) {
        return switch (row) {
            case 0 -> 100;
            case 1 -> 200;
            case 2 -> 300;
            case 3 -> 400;
            case 4 -> 500;
            default -> 0;
        };
    }

    public static int getXCoordinate(int col) {
        return switch (col) {
            case 0 -> 350;
            case 1 -> 440;
            case 2 -> 520;
            case 3 -> 590;
            case 4 -> 670;
            case 5 -> 750;
            case 6 -> 830;
            case 7 -> 900;
            case 8 -> 980;
            default -> 0;
        };
    }

    public void setHp(int hp) {
        this.hp = hp;
        if (this.hp <= 0) {
            imageView.setVisible(false);
            imageView.setDisable(true);
            GamePlayController.allPlants.remove(this);
        }
    }
}
