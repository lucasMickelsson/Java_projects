package ax.ha.it.oo2.graphics;

import ax.ha.it.oo2.states.FreeHandState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller part of MVC architecture.
 *
 * @author joakim
 */
public class DrawingController {

    @FXML
    private DrawingPane drawingPane;

    @FXML
    private ColorPicker colorPicker;

    public void initialize() {
        drawingPane.setDrawingState(DrawingState.POLYLINE);
    }

    @FXML
    private void drawPolyline() {
        drawingPane.setDrawingState(DrawingState.POLYLINE);
        drawingPane.handleStages();
    }

    @FXML
    private void drawFree() {
        Color myColor = colorPicker.getValue();
        FreeHandState.ColorPicker(colorPicker, myColor);
        drawingPane.setDrawingState(DrawingState.FREEHANDSTATE);
        drawingPane.handleStages();
    }

    @FXML
    private void drawRectangle() {
        drawingPane.setDrawingState(DrawingState.RECTANGLE);
        drawingPane.handleStages();
    }

    @FXML
    private void drawCircle() {
        drawingPane.setDrawingState(DrawingState.CIRCLE);
        drawingPane.handleStages();
    }

    @FXML
    private void setFreeHandDrawingScene() throws IOException {
        Stage stage = DrawingApplication.getStageTemp();
        FXMLLoader fxmlLoader = new FXMLLoader(DrawingApplication.class.getResource("freeHand.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void changeSceneBack() throws IOException {
        Stage stage = DrawingApplication.getStageTemp();
        FXMLLoader fxmlLoader = new FXMLLoader(DrawingApplication.class.getResource("layout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void clear() {
        drawingPane.clear();
    }

    @FXML
    private void undo() {
        // ToDo: Undo a previous command
        drawingPane.graphicCommandInvoker.undoLastCommand();
    }

    @FXML
    private void redo() {
        // ToDo: Redo a previous command
        drawingPane.graphicCommandInvoker.redoNextCommand();
    }

}
