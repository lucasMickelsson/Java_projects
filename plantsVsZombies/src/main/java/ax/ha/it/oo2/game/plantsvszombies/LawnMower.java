package ax.ha.it.oo2.game.plantsvszombies;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import zombies.Zombie;

import java.nio.file.Paths;

public class LawnMower extends GameElements {
    private Timeline zombieCheck;

    private boolean activated = false;

    public LawnMower(int x, int y) {
        super(x, y, "/lawnmowerIdle.gif", 70, 60);
    }

    public void checkZombie() {
        zombieCheck = new Timeline(new KeyFrame(Duration.millis(50), actionEvent -> {
            for (int i = 0; i < GamePlayController.spawnedZombies.size(); i++) {
                Zombie zombie = GamePlayController.spawnedZombies.get(i);
                //System.out.println("Zombie: " + zombie.getY() + "Bullet: " + this.getY());
                if (zombie.getY() + 55 == this.getY() && zombie.getX() - this.getX() <= 20 && zombie.getHp() > 0) {
                    if (!activated) {
                        activate();
                        zombie.setHp(0);
                        activated = true;
                        zombie.getAnimation().stop();
                    } else {
                        zombie.setHp(0);
                        zombie.getAnimation().stop();
                    }
                }
            }
        }
        ));
        zombieCheck.setCycleCount(Animation.INDEFINITE);
        zombieCheck.play();
        GamePlayController.allAnimations.add(zombieCheck);
    }

    public void activate() {
        imageView.setImage(new Image("lawnmowerActivated.gif", 70, 60, false, false));
        Media sound = new Media(Paths.get("../plantsVsZombies\\src\\main\\resources\\PlantVsZombies_assets_sounds_lawnmower.wav").toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(1000);
        mediaPlayer.play();
        Timeline driving = new Timeline(new KeyFrame(Duration.millis(5), e -> {
            if (getX() <= 1200) {
                setX(getX() + 1);
            } else {
                zombieCheck.stop();
            }
        }));
        driving.setCycleCount(1000);
        driving.play();
        GamePlayController.allAnimations.add(driving);
    }
}
