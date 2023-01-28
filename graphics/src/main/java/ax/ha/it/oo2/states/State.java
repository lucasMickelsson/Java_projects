package ax.ha.it.oo2.states;

import ax.ha.it.oo2.graphics.DrawingPane;
import ax.ha.it.oo2.graphics.DrawingState;
import ax.ha.it.oo2.graphics.GraphicCommandInvoker;
import javafx.scene.layout.Pane;

public interface State {
    void doAction(Context context, DrawingPane pane, DrawingState drawingState);
}
