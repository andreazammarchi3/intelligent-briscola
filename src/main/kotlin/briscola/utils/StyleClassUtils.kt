package briscola.utils

import javafx.scene.Node

class StyleClassUtils {
    companion object {
        /**
         * Adds a style class to a node.
         * @param node the node to which the style class is added
         * @param styleClass the style class to add
         */
        fun addStyleClass(node: Node, styleClass: String) {
            node.styleClass.add(styleClass)
        }

        /**
         * Removes a style class from a node.
         * @param node the node from which the style class is removed
         * @param styleClass the style class to remove
         */
        fun removeStyleClass(node: Node, styleClass: String) {
            node.styleClass.remove(styleClass)
        }

        /**
         * Toggles a style class on a node.
         * @param node the node on which the style class is toggled
         * @param styleClass the style class to toggle
         */
        fun toggleStyleClass(node: Node, styleClass: String) {
            if (node.styleClass.contains(styleClass)) {
                node.styleClass.remove(styleClass)
            } else {
                node.styleClass.add(styleClass)
            }
        }
    }
}