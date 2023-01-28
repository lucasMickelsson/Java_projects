module ax.ha.it.oo2.gui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.fasterxml.jackson.databind;
    opens ax.ha.it.oo2.gui to com.fasterxml.jackson.databind, javafx.fxml;


    exports ax.ha.it.oo2.gui;
}