package pa.lab6.drawingapp.actionpanel;

/**
 * serves as keys and labels for the action panel described in the ActionPanel class description comment
 */
public enum ActionPanelType {
    LOAD("Load"), SAVE("Save"), RESET("Reset"), EXIT("Exit");

    private final String name;

    ActionPanelType(String name) {
        this.name = name;
    }
}
