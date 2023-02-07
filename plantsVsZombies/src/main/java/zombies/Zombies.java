package zombies;

public enum Zombies {
    NORMALZOMBIE("/Zombie.gif", "/ZombieAttack.gif", 1),
    CONEZOMBIE("/coneheadzombie.gif", "/ConeheadZombieAttack.gif", 2),
    BUCKETZOMBIE("/bucketheadzombie.gif", "/BucketheadZombieAttack.gif", 3),
    FOOTBALLZOMBIE("/FootballZombie.gif", "/FootballZombieAttack.gif", 4);
    private final String normal;
    private final String eat;
    private final int ID;

    Zombies(String normal, String eat, int ID) {
        this.normal = normal;
        this.eat = eat;
        this.ID = ID;
    }

    public String getNormal() {
        return normal;
    }

    public String getEat() {
        return eat;
    }

    public int getID() {
        return ID;
    }
}
