package briscola.utils

/**
 * Enum class that contains the dimensions of the windows
 * @property MENU dimensions of the menu window
 * @property MATCH dimensions of the match window
 * @constructor creates a pair of integers representing the dimensions of the window
 */
enum class WindowDim {
    MENU {
        override fun getDim(): Pair<Int, Int> = Pair(500, 200)
    },
    MATCH {
        override fun getDim(): Pair<Int, Int> = Pair(1200, 800)
    };

    abstract fun getDim(): Pair<Int, Int>
}