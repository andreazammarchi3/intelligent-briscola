package briscola

import briscola.gui.view.MenuView
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage
import java.util.*

class Launcher : Application() {
    private val basePath = "/fxml/"

    override fun start(stage: Stage) {
        val menuView = MenuView(stage)
        val loader = FXMLLoader(Objects.requireNonNull(javaClass.getResource(basePath + "Menu.fxml")))
        loader.setController(menuView)
        val root = loader.load<Parent>()
        stage.scene = Scene(root)
        stage.title = "Intelligent Briscola"
        stage.show()
    }
}

fun main() {
    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
        println("Unhandled exception in thread ${thread.name}: ${throwable.message}")
    }
    // Launch the application in a separate thread to avoid blocking the main thread
    Thread {
        Application.launch(Launcher::class.java)
    }.start()
}
