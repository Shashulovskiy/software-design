import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNull

class LRUCacheTest {
    @Test
    fun testBadCapacity() {
        assertThrows<IllegalArgumentException> {
            LRUCache<Int, Int>(0)
        }

        assertThrows<IllegalArgumentException> {
            LRUCache<Int, Int>(-100)
        }
    }
    @Test
    fun testBasic() {
        val cache = LRUCache<Int, Int>(3)
        cache.put(1, 1)
        cache.put(2, 2)
        cache.put(3, 3)

        assertEquals(1, cache.get(1))
        assertEquals(2, cache.get(2))
        assertEquals(3, cache.get(3))

        assertNull(cache.get(4))
    }

    @Test
    fun testLRUOrdering() {
        val cache = LRUCache<Int, Int>(3)
        cache.put(1, 1)
        cache.put(2, 2)
        cache.put(3, 3)

        cache.get(1)
        cache.put(4, 4)

        assertEquals(1, cache.get(1))
        assertNull(cache.get(2))
        assertEquals(3, cache.get(3))
        assertEquals(4, cache.get(4))
    }
    @Test
    fun testInverseOrder() {
        val cache = LRUCache<Int, Int>(3)
        cache.put(1, 1)
        cache.put(2, 2)
        cache.put(3, 3)
        cache.put(4, 4)

        assertEquals(4, cache.get(4))
        assertEquals(3, cache.get(3))
        assertEquals(2, cache.get(2))
        assertNull(cache.get(1))

        cache.put(5, 5)

        assertNull(cache.get(1))
        assertEquals(2, cache.get(2))
        assertEquals(3, cache.get(3))
        assertNull(cache.get(4))
        assertEquals(5, cache.get(5))
    }

    @Test
    fun testOverwrite() {
        val size = 10
        val cache = LRUCache<Int, String>(size)

        // Init cache with values
        for (i in 0 until size) {
            cache.put(i, "value$i")
        }

        // Assert each value is stored
        for (i in 0 until size) {
            assertEquals("value$i", cache.get(i))
        }

        // Overwrite with new values
        for (i in size until 2 * size) {
            cache.put(i, "value$i")
        }

        // Assert each value is stored
        for (i in size until 2 * size) {
            assertEquals("value$i", cache.get(i))
        }

        // Assert none of the old values are stored
        for (i in 0 until size) {
            assertNull(cache.get(i))
        }
    }

    @Test
    fun testOverwriteLeastRecentlyUsed() {
        val size = 10
        val cache = LRUCache<Int, String>(size)

        // Init cache with values
        for (i in 0 until size) {
            cache.put(i, "value$i")
        }

        // Access the oldest entries
        for (i in 0 until size / 2) {
            cache.get(i)
        }

        // Overwrite the latest added values
        for (i in size / 2 until size) {
            cache.put(i * 10, "valueNew$i")
        }

        // Ensure that recently used entries are present
        for (i in 0 until size / 2) {
            assertEquals("value$i", cache.get(i))
        }

        // Ensure than least recently used values are gone
        for (i in size / 2 until size) {
            assertNull(cache.get(i))
        }
    }
}