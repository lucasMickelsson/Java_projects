package ax.ha.it.oo2.graphics;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DrawingApplication extends Application {

    private static Stage stageTemp;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DrawingApplication.class.getResource("layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Graphics");
        stage.show();
        stageTemp = stage;
    }

    public static Stage getStageTemp() {
        return stageTemp;
    }

    public static void main(String[] args) {
        launch();
    }
}