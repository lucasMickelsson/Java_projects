package plants;

import ax.ha.it.oo2.game.plantsvszombies.GamePlayController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.Glow;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Sunflower extends Plant {
    private Timeline timeline;

    public Sunflower(int x, int y, int row, int col) {
        super(x, y, 430, "/sunflower.gif", row, col, 60, 60);
    }

    public void produceSun(Pane pane) {
        Random random = new Random();
        Timeline stopGlow = new Timeline(new KeyFrame(Duration.seconds(1), actionEvent -> {
            Glow glow = new Glow();
            glow.setLevel(0);
            this.imageView.setEffect(glow);
        }));

        timeline = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> {
            checkHp();
            if (this.getHp() > 0) {
                int x = random.nextInt(getXCoordinate(col) + 20, getXCoordinate(col) + 40);
                int y = random.nextInt(getYCoordinate(row), getYCoordinate(row) + 20);
                Sun sun = new Sun(x, y);
                //sun.move();
                sun.makeImage(pane);
                sun.getImageView().setOnMouseClicked(mouseEvent -> {
                    sun.getImageView().setVisible(false);
                    sun.getImageView().setDisable(true);
                    GamePlayController.sunCount += 25;
                });
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        sun.getImageView().setDisable(true);
                        sun.getImageView().setVisible(false);
                        timer.cancel();
                    }
                }, 3000);
            }
            stopGlow.play();
        }));
        Timeline glowEffect = new Timeline(new KeyFrame(Duration.seconds(10), actionEvent -> {
            Glow glow = new Glow();
            glow.setLevel(0.5);
            this.imageView.setEffect(glow);
            timeline.play();
        }));

        glowEffect.setCycleCount(Animation.INDEFINITE);
        glowEffect.play();
        GamePlayController.allAnimations.add(timeline);
        GamePlayController.allAnimations.add(glowEffect);
        GamePlayController.allAnimations.add(stopGlow);
    }

    public void checkHp() {
        if (getHp() <= 0) {
            timeline.stop();
        }
    }
}
