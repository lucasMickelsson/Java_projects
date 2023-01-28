package ax.ha.it.oo2.graphics;

import ax.ha.it.oo2.states.CircleState;
import ax.ha.it.oo2.states.Context;
import ax.ha.it.oo2.states.FreeHandState;
import ax.ha.it.oo2.states.PolygonState;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

/**
 * View part of the MVC architecture.
 * Use this if you want to create Shapes and add them to a Pane
 *
 * @author joakim
 */
public class DrawingPane extends Pane {

    private DrawingState drawingState;
    GraphicCommandInvoker graphicCommandInvoker = new GraphicCommandInvoker();

    private final PolygonState polygonState;
    private final CircleState circleState;

    private final FreeHandState freeHandState;
    private final Context context = new Context();

    /**
     * Constructor
     */

    public void handleStages() {

        switch (drawingState) {
            case POLYLINE, RECTANGLE -> {
                //when we changed figure option on the screen we have to terminate the previous mouse listener
                this.setOnMouseReleased(null);
                polygonState.setGraphicCommandInvoker(graphicCommandInvoker);
                polygonState.doAction(context, this, drawingState);
            }
            case CIRCLE -> {
                this.setOnMousePressed(null);
                circleState.setGraphicCommandInvoker(graphicCommandInvoker);
                circleState.doAction(context, this, drawingState);
            }
            case FREEHANDSTATE -> {
                freeHandState.setGraphicCommandInvoker(graphicCommandInvoker);
                freeHandState.doAction(context, this, drawingState);
            }
        }

    }

    public DrawingPane() {

        polygonState = new PolygonState();
        circleState = new CircleState();
        freeHandState = new FreeHandState();

        setBorder(new Border(new BorderStroke(Paint.valueOf("black"),
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));

        // Need to set the 'clip' property of the Pane to avoid drawing Shapes outside the pane.
        // Simplified version of https://news.kynosarges.org/2016/11/03/javafx-pane-clipping/
        final Rectangle outputClip = new Rectangle();
        setClip(outputClip);
        layoutBoundsProperty().addListener((ov, oldValue, newValue) -> {
            outputClip.setWidth(newValue.getWidth());
            outputClip.setHeight(newValue.getHeight());
        });
    }

    void setDrawingState(DrawingState state) {
        this.drawingState = state;
    }

    public void clear() {
        // Clearing a List in Java is surprisingly cumbersome, but
        // utilizing subList helps:
        // See also https://www.w3resource.com/java-tutorial/arraylist/arraylist_subList.php
        this.getChildren().subList(0, getChildren().size()).clear();
    }
}
