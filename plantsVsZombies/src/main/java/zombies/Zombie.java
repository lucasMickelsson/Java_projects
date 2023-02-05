package zombies;

import ax.ha.it.oo2.game.plantsvszombies.GameElements;
import ax.ha.it.oo2.game.plantsvszombies.GamePlayController;
import plants.Plant;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class Zombie extends GameElements {
    protected double hp;
    protected double speed;
    protected double attackPower;
    protected Timeline animation;
    protected boolean isEating = false;
    protected boolean reachedPlant = false;

    public Zombie(double attackPower, double speed, int x, int y, double hp, String path, int width, int height) {
        super(x, y, path, width, height);
        this.attackPower = attackPower;
        this.speed = speed;
        this.hp = hp;
    }//https://github.com/ArkaSarkar19/Plants-vs-Zombies-Game/blob/master/pvzGUI/src/sample/Zombie.java

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
        if (this.hp <= 0) {
            //stop all animations
            imageView.setVisible(false);
            imageView.setDisable(true);
            animation.stop();

            GamePlayController.killedZombies += 1;
            GamePlayController.spawnedZombies.remove(this);
        }
        // check life if its time to make a normal zombie

    }

    public void checkReachedHouse() {
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

    public void eatPlant() {
        int foundPlant = 0;
        for (int i = 0; i < GamePlayController.allPlants.size(); i++) {
            Plant plant = GamePlayController.allPlants.get(i);
            if ((plant.getY() == this.getY() + 55 && this.getX() - plant.getX() == -25 && plant.getHp() > 0) ||
                    (this.getX() == plant.getX() && plant.getY() == this.getY() + 55 && plant.getHp() > 0)) {
                foundPlant = 1;
                if (!reachedPlant) {
                    reachedPlant = true;
                    isEating = true;
                }
                this.speed = 0;
                // System.out.println("Plant hp: " + plant.getHp() + " Zombie hp: " + this.getHp());
                plant.setHp(plant.getHp() - this.attackPower);
                if (plant.getHp() <= 0) {
                    plant.setHp(0);
                    GamePlayController.allPlants.remove(plant);
                    plant.getImageView().setDisable(true);
                    plant.getImageView().setVisible(false);
                    this.speed = 1;
                    reachedPlant = false;
                }
            }
        }
        if (foundPlant == 0) {
            this.speed = 1;
            reachedPlant = false;
        }
    }
}
