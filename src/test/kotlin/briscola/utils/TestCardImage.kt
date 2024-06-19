package briscola.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Test class for [CardImage].
 */
class TestCardImage {
    /**
     * Test the [CardImage] enum.
     */
    @Test
    fun testCardImage() {
        val nullImage = CardImage.NULL
        assertNull(nullImage.image)
    }

    /**
     * Test the [CardImage.getImageById] method.
     */
    @Test
    fun testGetImageById() {
        // Necessary to run in a separate thread when testing with javafx
        Thread {
            val image = CardImage.getImageById(1)
            assertEquals(CardImage.CLUBS_1.image, image)
            assertNull(CardImage.getImageById(-1))
        }.start()
    }
}