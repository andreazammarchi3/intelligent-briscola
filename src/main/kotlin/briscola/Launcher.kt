package briscola

import briscola.utils.FxmlPath
import briscola.utils.SceneSwapper
import briscola.view.MenuView
import javafx.application.Application
import javafx.stage.Stage


class Launcher : Application() {
    override fun start(stage: Stage) {
        val menuView = MenuView(stage)
        SceneSwapper().swapScene(menuView, FxmlPath.MENU, stage)
        SceneSwapper.setUpStage(stage)
    }
}

fun main() {
    // Launch the application in a separate thread to avoid blocking the main thread
    Thread {
        Application.launch(Launcher::class.java)
    }.start()
}
