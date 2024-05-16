package briscola.view

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.net.URL
import java.util.*

class MenuView(private val stage: Stage) : Initializable {
    @FXML
    private lateinit var txtPlayerName: TextField
    @FXML
    private lateinit var btnStartMatch: Button

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
    }
}