package briscola.utils

import javafx.scene.image.Image

/**
 * Enum containing all the possible cards.
 */
enum class CardImage(private val id: Int, private val path: String?) {
    NULL(0, null),
    CLUBS_1(1, "/imgs/cards/clubs_one.png"),
    CLUBS_2(2, "/imgs/cards/clubs_two.png"),
    CLUBS_3(3, "/imgs/cards/clubs_three.png"),
    CLUBS_4(4, "/imgs/cards/clubs_four.png"),
    CLUBS_5(5, "/imgs/cards/clubs_five.png"),
    CLUBS_6(6, "/imgs/cards/clubs_six.png"),
    CLUBS_7(7, "/imgs/cards/clubs_seven.png"),
    CLUBS_8(8, "/imgs/cards/clubs_eight.png"),
    CLUBS_9(9, "/imgs/cards/clubs_nine.png"),
    CLUBS_10(10, "/imgs/cards/clubs_ten.png"),

    COINS_1(11, "/imgs/cards/coins_one.png"),
    COINS_2(12, "/imgs/cards/coins_two.png"),
    COINS_3(13, "/imgs/cards/coins_three.png"),
    COINS_4(14, "/imgs/cards/coins_four.png"),
    COINS_5(15, "/imgs/cards/coins_five.png"),
    COINS_6(16, "/imgs/cards/coins_six.png"),
    COINS_7(17, "/imgs/cards/coins_seven.png"),
    COINS_8(18, "/imgs/cards/coins_eight.png"),
    COINS_9(19, "/imgs/cards/coins_nine.png"),
    COINS_10(20, "/imgs/cards/coins_ten.png"),

    CUPS_1(21, "/imgs/cards/cups_one.png"),
    CUPS_2(22, "/imgs/cards/cups_two.png"),
    CUPS_3(23, "/imgs/cards/cups_three.png"),
    CUPS_4(24, "/imgs/cards/cups_four.png"),
    CUPS_5(25, "/imgs/cards/cups_five.png"),
    CUPS_6(26, "/imgs/cards/cups_six.png"),
    CUPS_7(27, "/imgs/cards/cups_seven.png"),
    CUPS_8(28, "/imgs/cards/cups_eight.png"),
    CUPS_9(29, "/imgs/cards/cups_nine.png"),
    CUPS_10(30, "/imgs/cards/cups_ten.png"),

    SWORDS_1(31, "/imgs/cards/swords_one.png"),
    SWORDS_2(32, "/imgs/cards/swords_two.png"),
    SWORDS_3(33, "/imgs/cards/swords_three.png"),
    SWORDS_4(34, "/imgs/cards/swords_four.png"),
    SWORDS_5(35, "/imgs/cards/swords_five.png"),
    SWORDS_6(36, "/imgs/cards/swords_six.png"),
    SWORDS_7(37, "/imgs/cards/swords_seven.png"),
    SWORDS_8(38, "/imgs/cards/swords_eight.png"),
    SWORDS_9(39, "/imgs/cards/swords_nine.png"),
    SWORDS_10(40, "/imgs/cards/swords_ten.png"),

    BACK(41, "/imgs/back.png"),
    NOT_FOUND(42, "/imgs/not_found.png");

    val image: Image?
        /**
         * Get the card Image
         * @return the Image
         */
        get() = if (path != null) Image(path) else null

    companion object {
        /**
         * Get the card Image by the card id
         * @param id the id of the card
         * @return the Image of the card
         */
        fun getImageById(id: Int): Image {
            for (cardImage in entries) {
                if (cardImage.id == id) {
                    return Image(cardImage.path)
                }
            }
            return Image("/imgs/not_found.png")
        }
    }
}