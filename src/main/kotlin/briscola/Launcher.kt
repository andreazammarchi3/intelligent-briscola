package briscola

import briscola.env.BriscolaEnvironment
import briscola.utils.FxmlPath
import briscola.utils.SceneSwapper
import briscola.view.MenuView
import javafx.application.Application
import javafx.stage.Stage
import java.io.File
import jason.infra.centralised.RunCentralisedMAS

class Launcher: Application() {
    private lateinit var env: BriscolaEnvironment
    private lateinit var stage: Stage

    override fun start(primaryStage: Stage) {
        this.stage = primaryStage
        startJasonEnvironment()
    }

    private fun startJasonEnvironment() {
        Thread {
            try {
                val file = File("src/briscola.mas2j")
                val mas = RunCentralisedMAS()
                mas.init(arrayOf(file.absolutePath))
                mas.create()
                mas.start()
                env = mas.environmentInfraTier.userEnvironment as BriscolaEnvironment
                // Start JavaFX UI setup after environment is ready
                javafx.application.Platform.runLater {
                    setupJavaFX()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun setupJavaFX() {
        val menuView = MenuView(stage, env)
        SceneSwapper().swapScene(menuView, FxmlPath.MENU, stage)
        SceneSwapper.setUpStage(stage)
    }
}

fun main() {
    Application.launch(Launcher::class.java)
}
