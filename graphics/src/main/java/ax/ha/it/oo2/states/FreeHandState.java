package ax.ha.it.oo2.states;

import ax.ha.it.oo2.graphics.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FreeHandState implements State {
    private GraphicCommandInvoker graphicCommandInvoker;

    private Canvas canvas;

    private static GraphicsContext graphicsContext;

    private static ColorPicker colorPicker;

    private static Color color;
    private Command add;

    public void setGraphicCommandInvoker(GraphicCommandInvoker graphicCommandInvoker) {
        this.graphicCommandInvoker = graphicCommandInvoker;
    }

    public static void ColorPicker(ColorPicker colorPicker, Color color) {
        FreeHandState.colorPicker = colorPicker;
        FreeHandState.color = color;
        //graphicsContext.setStroke(color);
    }

    @Override
    public void doAction(Context context, DrawingPane pane, DrawingState drawingState) {
        if (drawingState == DrawingState.FREEHANDSTATE) {
            if (canvas == null) {
                canvas = new Canvas(595, 395);

                graphicsContext = canvas.getGraphicsContext2D();
                graphicsContext.setLineWidth(1);
                graphicsContext.setStroke(color);

                canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
                    graphicsContext.beginPath();
                    graphicsContext.moveTo(event.getX(), event.getY());
                    graphicsContext.stroke();
                });

                canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
                    graphicsContext.lineTo(event.getX(), event.getY());
                    graphicsContext.stroke();
                });

                add = new AddCommand(pane, canvas);
                graphicCommandInvoker.executeCommand(add);
            }
            canvas = null;

        }
        context.setState(this);
    }
}
