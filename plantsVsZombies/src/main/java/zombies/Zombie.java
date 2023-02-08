package zombies;

import ax.ha.it.oo2.game.plantsvszombies.GameElements;
import ax.ha.it.oo2.game.plantsvszombies.GamePlayController;
import ax.ha.it.oo2.game.plantsvszombies.Main;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import plants.Plant;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.nio.file.Paths;

public abstract class Zombie extends GameElements {
    protected double hp;
    protected double speed;
    protected double attackPower;
    protected Timeline animation;
    protected boolean isEating = false;
    protected boolean reachedPlant = false;
    protected int id;

    public Zombie(int id, double attackPower, double speed, int x, int y, double hp, String path, int width, int height) {
        super(x, y, path, width, height);
        this.attackPower = attackPower;
        this.speed = speed;
        this.hp = hp;
        this.id = id;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
        if (this.hp <= 0) {
            //stop all animations
            System.out.print("Killed zombie  ");
            imageView.setVisible(false);
            imageView.setDisable(true);
            animation.stop();

            GamePlayController.killedZombies += 1;
            GamePlayController.spawnedZombies.remove(this);
            System.out.println(GamePlayController.spawnedZombies.size());
            Media sound = new Media(Paths.get("../plantsVsZombies\\src\\main\\resources\\PlantVsZombies_assets_sounds_yuck.wav").toUri().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.play();
        } else if (this.hp == 10 && getClass() != NormalZombie.class) { // check life if its time to make a normal zombie
            for (int i = 0; i < GamePlayController.spawnedZombies.size(); i++) {
                Zombie zombieIndex = GamePlayController.spawnedZombies.get(i);
                if (zombieIndex == this) {
                    GamePlayController.spawnedZombies.remove(zombieIndex);
                    zombieIndex.imageView.setDisable(true);
                    zombieIndex.imageView.setVisible(false);
                    zombieIndex.animation.stop();

                    Zombie zombie = new NormalZombie(this.getX(), this.getY());
                    GamePlayController.spawnedZombies.add(zombie);
                    int index = GamePlayController.spawnedZombies.indexOf(zombie);
                    AnchorPane anchorPane = (AnchorPane) Main.stageTemp.getScene().getRoot();
                    GamePlayController.spawnedZombies.get(index).makeImage(anchorPane);
                    GamePlayController.spawnedZombies.get(index).moveZombie();
                    break;
                }
            }
        }
    }

    private void checkReachedHouse() {
        // Check if the zombie has reached the house
        if (this.getX() <= 250) {
            GamePlayController.gameStatus = false;
        }
    }

    public void moveZombie() {
        animation = new Timeline(new KeyFrame(Duration.millis(70), e -> {
            if (this.hp > 0) {
                setX((int) (getX() - speed));
                eatPlant();
                checkReachedHouse();
            }
        }));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        GamePlayController.allAnimations.add(animation);
    }

    public Timeline getAnimation() {
        return animation;
    }

    private void eatPlant() {
        int foundPlant = 0;
        for (int i = 0; i < GamePlayController.allPlants.size(); i++) {
            Plant plant = GamePlayController.allPlants.get(i);
            if ((plant.getY() == this.getY() + 55 && this.getX() - plant.getX() == -30 && plant.getHp() > 0) ||
                    (this.getX() == plant.getX() - 50 && plant.getY() == this.getY() + 55 && plant.getHp() > 0)) {
                foundPlant = 1;
                if (!reachedPlant) {
                    reachedPlant = true;
                    isEating = true;
                }
                if (this.id == Zombies.values()[id - 1].getID() && speed > 0) {
                    imageView.setImage(new Image(Zombies.values()[id - 1].getEat(), width, height, false, false));
                }
                this.speed = 0;
                System.out.println("Plant hp: " + plant.getHp() + " Zombie hp: " + this.getHp());
                plant.setHp(plant.getHp() - this.attackPower);
                if (plant.getHp() <= 0) {
                    plant.setHp(0);
                    GamePlayController.allPlants.remove(plant);
                    plant.getImageView().setDisable(true);
                    plant.getImageView().setVisible(false);
                    if (this.id == Zombies.values()[id - 1].getID() && speed == 0) {
                        imageView.setImage(new Image(Zombies.values()[id - 1].getNormal(), width, height, false, false));
                    }
                    this.speed = 1;
                    // timeline.stop();
                    reachedPlant = false;
                }
            }
        }
        if (foundPlant == 0) {
            if (this.id == Zombies.values()[id - 1].getID() && speed == 0) {
                imageView.setImage(new Image(Zombies.values()[id - 1].getNormal(), width, height, false, false));
            }
            this.speed = 1;
            reachedPlant = false;
        }
    }
}
