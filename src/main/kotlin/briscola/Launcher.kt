package briscola

import briscola.env.BriscolaEnvironment
import briscola.utils.FxmlPath
import briscola.utils.SceneSwapper
import briscola.view.MenuView
import jason.infra.centralised.RunCentralisedMAS
import javafx.application.Application
import javafx.stage.Stage
import java.io.File


/**
 * Main class to start the application.
 * It starts the Jason environment and then the JavaFX UI.
 */
class Launcher: Application() {
    private lateinit var env: BriscolaEnvironment
    private lateinit var stage: Stage

    override fun start(primaryStage: Stage) {
        this.stage = primaryStage
        startJasonEnvironment()
    }

    /**
     * Start the Jason environment in a separate thread.
     * When the environment is ready, it starts the JavaFX UI.
     */
    private fun startJasonEnvironment() {
        Thread {
            try {
                val file = File("src/main/resources/mas2j/briscola.mas2j")
                val mas = RunCentralisedMAS()
                mas.init(arrayOf(file.absolutePath))
                mas.create()
                mas.start()
                env = mas.environmentInfraTier.userEnvironment as BriscolaEnvironment
                env.setMAS(mas)
                // Start JavaFX UI setup after environment is ready
                javafx.application.Platform.runLater {
                    setupJavaFX()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    /**
     * Set up the JavaFX UI.
     */
    private fun setupJavaFX() {
        val menuView = MenuView(stage, env)
        SceneSwapper().swapScene(menuView, FxmlPath.MENU, stage)
        SceneSwapper.setUpStage(stage)
    }
}

/**
 * Main function to start the application.
 */
fun main() {
    Application.launch(Launcher::class.java)
}
