package ax.ha.it.oo2.graphics;

import java.util.ArrayList;
import java.util.List;

public class GraphicCommandInvoker {
    private List<Command> history = new ArrayList<>();
    private int lastCommand = -1;

    public void executeCommand(Command c) {
        for (int i = history.size() - 1; i > lastCommand; i--) {
            history.remove(i);
        }
        c.execute();
        history.add(c);
        lastCommand++;
    }

    public void undoLastCommand() {
        if (lastCommand >= 0) {
            Command c = history.get(lastCommand);
            c.undo();
            lastCommand--;
        }
    }

    public void redoNextCommand() {
        if (lastCommand < history.size() - 1) {
            lastCommand++;
            Command c = history.get(lastCommand);
            c.execute();
        }
    }
}
