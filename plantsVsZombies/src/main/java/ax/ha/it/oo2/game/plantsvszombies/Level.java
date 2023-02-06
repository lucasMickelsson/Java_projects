package ax.ha.it.oo2.game.plantsvszombies;

import zombies.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Level {
    private final int levelNumber;
    private int totalZombies;
    private int totalNormalZombies;
    private int totalBucketZombies;
    private int totalConeZombies;
    private int totalFootballZombies;
    private ArrayList<Zombie> zombieList;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        zombieList = new ArrayList<>();

        if (levelNumber == 1) {
            this.totalZombies = 10;
            this.totalNormalZombies = 10;
            this.totalBucketZombies = 0;
            this.totalConeZombies = 0;
            this.totalFootballZombies = 0;
            fillNormal();
        } else if (levelNumber == 2) {
            this.totalZombies = 15;
            this.totalNormalZombies = 10;
            this.totalBucketZombies = 0;
            this.totalConeZombies = 5;
            this.totalFootballZombies = 0;
            fillNormal();
            fillCone();
        } else if (levelNumber == 3) {
            this.totalZombies = 20;
            this.totalNormalZombies = 8;
            this.totalBucketZombies = 2;
            this.totalConeZombies = 8;
            this.totalFootballZombies = 2;
            fillWithAllTypes();
        } else if (levelNumber == 4) {
            this.totalZombies = 25;
            this.totalNormalZombies = 8;
            this.totalBucketZombies = 4;
            this.totalConeZombies = 9;
            this.totalFootballZombies = 4;
            fillWithAllTypes();
        } else if (levelNumber == 5) {
            this.totalZombies = 30;
            this.totalNormalZombies = 6;
            this.totalBucketZombies = 8;
            this.totalConeZombies = 10;
            this.totalFootballZombies = 6;
            fillWithAllTypes();
        }
        Collections.shuffle(zombieList);
    }

    private void fillWithAllTypes() {
        fillNormal();
        fillCone();
        fillBucket();
        fillFootball();
    }

    private void fillCone() {
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

    private void fillFootball() {
        Random random = new Random();
        FootballZombie zombie = null;
        for (int i = 0; i < totalFootballZombies; i++) {
            int lane = random.nextInt(1, 6);
            if (lane == 1) {
                zombie = new FootballZombie(1024, 45);
            } else if (lane == 2) {
                zombie = new FootballZombie(1024, 145);
            } else if (lane == 3) {
                zombie = new FootballZombie(1024, 245);
            } else if (lane == 4) {
                zombie = new FootballZombie(1024, 345);
            } else if (lane == 5) {
                zombie = new FootballZombie(1024, 445);
            }
            zombieList.add(zombie);
        }
    }

    private void fillBucket() {
        Random random = new Random();
        BucketZombie zombie = null;
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
