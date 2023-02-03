package plants;

import ax.ha.it.oo2.game.plantsvszombies.GameElements;
import ax.ha.it.oo2.game.plantsvszombies.GamePlayController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Sun extends GameElements {
    public Sun(int x, int y) {
        super(x, y, "/sun.png", 40, 40);
    }

    public void move() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
            if (this.getY() <= 120) {
                this.setY(getY() + 1);
            } else {
                imageView.setVisible(false);
                imageView.setDisable(true);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        GamePlayController.allAnimations.add(timeline);
    }
}
