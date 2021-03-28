package pa.lab6.drawingapp.actionpanel;

public enum ActionPanelType {
    LOAD("Load"), SAVE("Save"), RESET("Reset"), EXIT("Exit");

    private final String name;

    ActionPanelType(String name) {
        this.name = name;
    }
}
