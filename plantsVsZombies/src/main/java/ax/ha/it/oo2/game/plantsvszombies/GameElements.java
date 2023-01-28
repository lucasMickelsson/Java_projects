package ax.ha.it.oo2.game.plantsvszombies;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public abstract class GameElements {
    protected int x;
    protected int y;
    protected String path;
    protected ImageView imageView;
    protected int width;
    protected int height;

    public GameElements(int x, int y, String path, int width, int height) {
        this.x = x;
        this.y = y;
        this.path = path;
        this.width = width;
        this.height = height;
    }

    public void makeImage(Pane pane) {
        imageView = new ImageView();
        Image image = new Image(path, width, height, false, false);
        imageView.setImage(image);
        imageView.setX(x);
        imageView.setY(y);
        pane.getChildren().addAll(imageView);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
        imageView.setX(x);
    }

    public void setY(int y) {
        this.y = y;
        imageView.setY(y);
    }
}
