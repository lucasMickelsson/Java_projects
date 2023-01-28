package ax.ha.it.oo2.states;

import ax.ha.it.oo2.graphics.*;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class PolygonState implements State {

    private Path pathUnderConstruction;
    private GraphicCommandInvoker graphicCommandInvoker;
    private Command add;
    private Command remove;

    public void setGraphicCommandInvoker(GraphicCommandInvoker graphicCommandInvoker) {
        this.graphicCommandInvoker = graphicCommandInvoker;
    }

    @Override
    public void doAction(Context context, DrawingPane pane, DrawingState drawingState) {
        pane.setOnMousePressed(mouseEvent -> {
            // ToDo: Handle MouseEvents according to state
            if (mouseEvent.getButton() == MouseButton.PRIMARY && (drawingState == DrawingState.POLYLINE
                    || drawingState == DrawingState.RECTANGLE)) {
                if (pathUnderConstruction == null) {
                    // Start drawing the polyline
                    pathUnderConstruction = new Path();
                    add = new AddCommand(pane, pathUnderConstruction);
                    graphicCommandInvoker.executeCommand(add);
                    MoveTo moveTo = new MoveTo(mouseEvent.getX(), mouseEvent.getY());
                    pathUnderConstruction.getElements().add(moveTo);
                }
                LineTo lineTo = new LineTo(mouseEvent.getX(), mouseEvent.getY());
                pathUnderConstruction.getElements().add(lineTo);
                //graphicCommandInvoker.executeCommand(c);
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                if (pathUnderConstruction != null) {
                    // Close the polyline
                    pathUnderConstruction.getElements().remove(pathUnderConstruction.getElements().size() - 1);
                    pathUnderConstruction.getElements().add(new ClosePath());
                    pathUnderConstruction = null;
                } else {
                    // Remove the topmost intersecting shape.
                    // Need to use traditional for-loop as foreach loops will cause an
                    // exception when trying to modify the collection being iterated upon
                    for (int i = pane.getChildren().size() - 1; i >= 0; i--) {
                        Node node = pane.getChildren().get(i);
                        if (node.getBoundsInParent().intersects(mouseEvent.getX(), mouseEvent.getY(), 1, 1)) {
                            remove = new RemoveCommand(pane, node);
                            graphicCommandInvoker.executeCommand(remove);
                            break;
                        }
                    }
                }
            }
        });
        pane.setOnMouseMoved(mouseEvent -> {
            // ToDo: Handle MouseEvents according to state
            if (drawingState == DrawingState.POLYLINE || drawingState == DrawingState.RECTANGLE) {
                if (pathUnderConstruction != null) {
                    LineTo lineTo = (LineTo) pathUnderConstruction.getElements().get(
                            pathUnderConstruction.getElements().size() - 1);
                    lineTo.setX(mouseEvent.getX());
                    lineTo.setY(mouseEvent.getY());
                }
            }
        });

        context.setState(this);
    }
}
