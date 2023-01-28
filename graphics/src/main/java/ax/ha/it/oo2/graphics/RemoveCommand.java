package ax.ha.it.oo2.graphics;

import javafx.scene.Node;
import javafx.scene.shape.Path;
import javafx.scene.shape.Shape;

public class RemoveCommand implements Command {
    private DrawingPane drawingPane;
    private Node path;

    public RemoveCommand(DrawingPane drawingPane, Node path) {
        this.drawingPane = drawingPane;
        this.path = path;
    }

    @Override
    public void execute() {
        drawingPane.getChildren().remove(path);
    }

    @Override
    public void undo() {
        drawingPane.getChildren().add(path);
    }

}
