package briscola.utils

import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.IOException
import java.util.*
import kotlin.system.exitProcess


/**
 * Class for swapping scenes.
 */
class SceneSwapper {
    /**
     * Create a scene with a fxml file and assign a controller to it a controller.
     * @param controller The controller to be assigned to the scene.
     * @param fxmlPath The path to the fxml file.
     * @param stage The stage containing the new scene.
     * @throws RuntimeException if the fxml file is not found.
     */
    fun swapScene(controller: Initializable, fxmlPath: FxmlPath, stage: Stage) {
        val loader = FXMLLoader(Objects.requireNonNull(javaClass.getResource(PATH + fxmlPath.path)))
        loader.setController(controller)
        try {
            val root = loader.load<Parent>()
            val home = Scene(root)
            stage.scene = home
            stage.show()
            stage.sizeToScene()
            stage.centerOnScreen()
            stage.setOnCloseRequest { exitProcess(0) }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    companion object {
        private const val PATH = "/fxml/"

        /**
         * If a new stage has been created, set it up (title, size, icon and position). Use only for menu scenes.
         * @param stage the stage to be set up
         */
        fun setUpStage(stage: Stage) {
            stage.isResizable = false
            stage.title = "Intelligent Briscola"
            stage.show()
            stage.icons.add(CardImage.BACK.image)
        }
    }
}
