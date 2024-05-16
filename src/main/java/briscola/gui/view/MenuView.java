package briscola.gui.view;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuView implements Initializable {
    private final Stage stage;
    private TextField txtPlayerName;
    private ComboBox<String> comboBotLevel;
    private Button btnStartMatch;

    public MenuView(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
