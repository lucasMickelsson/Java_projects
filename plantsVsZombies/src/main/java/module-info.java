module ax.ha.it.oo2.game.plantsvszombies {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens ax.ha.it.oo2.game.plantsvszombies to javafx.fxml;
    exports ax.ha.it.oo2.game.plantsvszombies;
}