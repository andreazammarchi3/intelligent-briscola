package briscola.utils

import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.io.IOException
import java.util.*


/**
 * Class for swapping scenes.
 */
class SceneSwapper {
    /**
     * Create a scene with a fxml file and assign a controller to it a controller.
     * @param controller The controller to be assigned to the scene.
     * @param fxmlPath The path to the fxml file.
     * @param stage The stage containing the new scene.
     */
    fun swapScene(controller: Initializable, fxmlPath: FxmlPath, stage: Stage) {
        val loader = FXMLLoader(Objects.requireNonNull(javaClass.getResource(PATH + fxmlPath.path)))
        loader.setController(controller)
        try {
            val root = loader.load<Parent>()
            val home = Scene(root)
            stage.scene = home
            stage.show()
            packStage(stage)
            // setUpStage(stage);
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

        /**
         * Update the size of the stage.
         * @param stage the stage to be resized
         */
        fun setDim(stage: Stage, width: Int, height: Int) {
            stage.width = width.toDouble()
            stage.height = height.toDouble()
        }

        /**
         * Pack the stage to the size of the scene.
         * @param stage the stage to be packed
         */
        fun packStage(stage: Stage) {
            stage.sizeToScene()
        }
    }
}
