package ax.ha.it.oo2.graphics;

import javafx.scene.Node;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

public class AddCommand implements Command {

    private DrawingPane drawingPane;
    private Node figure;

    public AddCommand(DrawingPane drawingPane, Node figure) {
        this.drawingPane = drawingPane;
        this.figure = figure;
    }

    @Override
    public void execute() {
        drawingPane.getChildren().add(figure);
    }

    @Override
    public void undo() {
        drawingPane.getChildren().remove(figure);
    }
}
