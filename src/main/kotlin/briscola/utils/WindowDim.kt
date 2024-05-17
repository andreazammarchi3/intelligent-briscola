package briscola.utils

enum class WindowDim {
    MENU {
        override fun getDim(): Pair<Int, Int> = Pair(500, 200)
    },
    MATCH {
        override fun getDim(): Pair<Int, Int> = Pair(1200, 800)
    };

    abstract fun getDim(): Pair<Int, Int>
}