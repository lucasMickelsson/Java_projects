package ax.ha.it.oo2.game.plantsvszombies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Random;

public class Sunflower extends Plant {
    private Timeline timeline;

    public Sunflower(int x, int y, int row, int col) {
        super(x, y, 100, "/sunflower.gif", row, col, 60, 60);
    }

    public void produceSun(Pane pane) {
        Random random = new Random();
        timeline = new Timeline(new KeyFrame(Duration.seconds(15), actionEvent -> {
            checkHp();
            int x = random.nextInt(getXCoordinate(col), getXCoordinate(col) + 20);
            int y = random.nextInt(getYCoordinate(row), getYCoordinate(row) + 20);
            Sun sun = new Sun(x, y);
            //sun.move();
            sun.makeImage(pane);
            sun.imageView.setOnMouseClicked(mouseEvent -> {
                sun.imageView.setVisible(false);
                sun.imageView.setDisable(true);
                GamePlayController.sunCount += 25;
            });

        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        GamePlayController.allAnimations.add(timeline);
    }

    public void checkHp() {
        if (getHp() <= 0) {
            timeline.stop();
        }
    }
}
