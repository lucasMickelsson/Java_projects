package plants;

import ax.ha.it.oo2.game.plantsvszombies.GamePlayController;
import zombies.Zombie;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Repeater extends Plant {
    private Timeline timeline;

    public Repeater(int x, int y, int row, int col) {
        super(x, y, 530, "/repeater.gif", row, col, 60, 60);
    }

    public void attack(Pane pane) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> {
            for (int i = 0; i < GamePlayController.spawnedZombies.size(); i++) {
                Zombie zombie = GamePlayController.spawnedZombies.get(i);
                if (this.getY() == zombie.getY() + 55 && this.getX() <= zombie.getX() && zombie.getHp() > 0) {
                    Bullet bullet1 = new Bullet(getXCoordinate(col) + 30, getYCoordinate(row));
                    Bullet bullet2 = new Bullet(getXCoordinate(col), getYCoordinate(row));
                    bullet1.makeImage(pane);
                    bullet2.makeImage(pane);
                    bullet1.shoot();
                    bullet2.shoot();
                    checkHp();
                }
            }
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
