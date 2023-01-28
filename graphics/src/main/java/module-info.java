module ax.ha.it.oo2.graphics {
    requires javafx.controls;
    requires javafx.fxml;

    opens ax.ha.it.oo2.graphics to javafx.fxml;
    exports ax.ha.it.oo2.graphics;
}