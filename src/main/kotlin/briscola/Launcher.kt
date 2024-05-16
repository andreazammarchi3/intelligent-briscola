package briscola

import briscola.gui.utils.SceneSwapper
import briscola.gui.view.MenuView
import javafx.application.Application
import javafx.stage.Stage


class Launcher : Application() {
    override fun start(stage: Stage) {
        val menuView = MenuView(stage)
        SceneSwapper().swapScene(menuView, "Menu.fxml", stage)
        SceneSwapper.setUpStage(stage)
    }
}

fun main() {
    // Launch the application in a separate thread to avoid blocking the main thread
    Thread {
        Application.launch(Launcher::class.java)
    }.start()
}
