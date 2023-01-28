package ax.ha.it.oo2.graphics;

/*

Note: Enums are handy for simple state machines with only a few lines of codes per event-handling override.
(see e.g. https://www.baeldung.com/java-enum-simple-state-machine  )
They are however not the best choice for more complex states as the code quickly gets messy,
at least if all state-handling code is placed inside the enum.

If you choose to implement the State pattern you will probably want to change DrawingState into an interface or
abstract base class, with implementing classes corresponding to different states.
 */

public enum DrawingState {POLYLINE, RECTANGLE, CIRCLE, FREEHANDSTATE};
