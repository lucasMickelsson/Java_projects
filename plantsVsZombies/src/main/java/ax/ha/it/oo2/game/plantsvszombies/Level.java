package ax.ha.it.oo2.game.plantsvszombies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Level {
    private final int levelNumber;
    private int totalZombies;
    private int totalNormalZombies;
    private int totalBucketZombies;
    private int totalConeZombies;
    private ArrayList<Zombie> zombieList;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        zombieList = new ArrayList<>();

        if (levelNumber == 1) {
            this.totalZombies = 10;
            this.totalNormalZombies = 10;
            this.totalBucketZombies = 0;
            this.totalConeZombies = 0;
            fillNormal();
        } else if (levelNumber == 2) {
            this.totalZombies = 15;
            this.totalNormalZombies = 10;
            this.totalBucketZombies = 0;
            this.totalConeZombies = 5;
            fillWithConeAndNormal();
        } else if (levelNumber == 3) {
            this.totalZombies = 20;
            this.totalNormalZombies = 10;
            this.totalBucketZombies = 5;
            this.totalConeZombies = 5;
            fillWithAllSort();
        } else if (levelNumber == 4) {
            this.totalZombies = 25;
            this.totalNormalZombies = 3;
            this.totalBucketZombies = 13;
            this.totalConeZombies = 9;
            fillWithAllSort();
        } else if (levelNumber == 5) {
            this.totalZombies = 30;
            this.totalNormalZombies = 2;
            this.totalBucketZombies = 20;
            this.totalConeZombies = 8;
            fillWithAllSort();
        }
        Collections.shuffle(zombieList);
    }

    private void fillWithConeAndNormal() {
        Random random = new Random();
        ConeZombie zombie = null;
        for (int i = 0; i < totalConeZombies; i++) {
            int lane = random.nextInt(1, 6);
            if (lane == 1) {
                zombie = new ConeZombie(1024, 45);
            } else if (lane == 2) {
                zombie = new ConeZombie(1024, 145);
            } else if (lane == 3) {
                zombie = new ConeZombie(1024, 245);
            } else if (lane == 4) {
                zombie = new ConeZombie(1024, 345);
            } else if (lane == 5) {
                zombie = new ConeZombie(1024, 445);
            }
            zombieList.add(zombie);
        }
        fillNormal();
    }

    private void fillNormal() {
        Random random = new Random();
        NormalZombie zombie = null;
        for (int i = 0; i < totalNormalZombies; i++) {

            int lane = random.nextInt(1, 6);
            if (lane == 1) {
                zombie = new NormalZombie(1024, 45);
            } else if (lane == 2) {
                zombie = new NormalZombie(1024, 145);
            } else if (lane == 3) {
                zombie = new NormalZombie(1024, 245);
            } else if (lane == 4) {
                zombie = new NormalZombie(1024, 345);
            } else if (lane == 5) {
                zombie = new NormalZombie(1024, 445);
            }
            zombieList.add(zombie);
        }
    }

    private void fillWithAllSort() {
        Random random = new Random();
        BucketZombie zombie = null;
        fillWithConeAndNormal();
        for (int i = 0; i < totalBucketZombies; i++) {
            int lane = random.nextInt(1, 6);
            if (lane == 1) {
                zombie = new BucketZombie(1024, 45);
            } else if (lane == 2) {
                zombie = new BucketZombie(1024, 145);
            } else if (lane == 3) {
                zombie = new BucketZombie(1024, 245);
            } else if (lane == 4) {
                zombie = new BucketZombie(1024, 345);
            } else if (lane == 5) {
                zombie = new BucketZombie(1024, 445);
            }
            zombieList.add(zombie);
        }
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public ArrayList<Zombie> getZombieList() {
        return zombieList;
    }
}
