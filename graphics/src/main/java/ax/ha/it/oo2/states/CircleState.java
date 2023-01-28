package ax.ha.it.oo2.states;

import ax.ha.it.oo2.graphics.*;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleState implements State {

    private Circle circle;
    private Command add;

    private GraphicCommandInvoker graphicCommandInvoker;

    public void setGraphicCommandInvoker(GraphicCommandInvoker graphicCommandInvoker) {
        this.graphicCommandInvoker = graphicCommandInvoker;
    }

    @Override
    public void doAction(Context context, DrawingPane pane, DrawingState drawingState) {
        pane.setOnMouseReleased(mouseEvent -> {
            // ToDo: Handle MouseEvents according to state
            if (mouseEvent.getButton() == MouseButton.PRIMARY && drawingState == DrawingState.CIRCLE) {
                if (circle == null) {
                    circle = new Circle();
                    circle.setFill(Color.CHOCOLATE);
                    circle.setStroke(Color.BLACK);

                    circle.setCenterX(mouseEvent.getX());
                    circle.setCenterY(mouseEvent.getY());
                    circle.setRadius(50);
                    circle.setRadius(50);

                    add = new AddCommand(pane, circle);
                    graphicCommandInvoker.executeCommand(add);
                }
                circle = null;
            }
        });

        context.setState(this);
    }
}
