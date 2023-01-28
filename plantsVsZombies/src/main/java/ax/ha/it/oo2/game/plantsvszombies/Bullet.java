package ax.ha.it.oo2.game.plantsvszombies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Bullet extends GameElements {

    private Timeline timeline;

    public Bullet(int x, int y) {
        super(x, y, "/bullet.png", 20, 20);
    }

    public void shoot() {
        timeline = new Timeline(new KeyFrame(Duration.millis(5), actionEvent -> {
            if (getX() <= 1050) {
                setX(getX() + 1);
                checkZombieCollision();
            } else {
                imageView.setVisible(false);
                imageView.setDisable(true);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void checkZombieCollision() {
        for (int i = 0; i < GamePlayController.spawnedZombies.size(); i++) {
            Zombie zombie = GamePlayController.spawnedZombies.get(i);
            if (zombie.getY() + 55 == this.getY() && zombie.getX() - this.getX() <= -4 && zombie.getHp() > 0) {
                zombie.setHp(zombie.getHp() - 1);
                this.imageView.setDisable(true);
                this.imageView.setVisible(false);
                timeline.stop();
            }
        }
    }
}
