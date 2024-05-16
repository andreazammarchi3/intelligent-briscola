package briscola.gui.model;

public enum FxmlPath {
    MENU("Menu.fxml"),
    MATCH("Match.fxml");

    private final String path;

    FxmlPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
