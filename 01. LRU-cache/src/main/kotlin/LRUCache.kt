class LRUCache<K, V>(private val capacity: Int): Cache<K, V> {

    private val cache: MutableMap<K, CacheEntry<K, V>> = hashMapOf()
    private val lruList: LRUList<K> = LRUList()

    init {
        if (capacity < 1) {
            throw IllegalArgumentException("LRUCache capacity should be greater than zero")
        }
    }

    override fun get(key: K): V? {
        validateSize()

        val initialSize = cache.size

        val res = cache[key]

        if (res != null) {
            val node = res.node
            remove(node)

            addLast(node, res.value)
        }

        validateAccessedElementIsLast(res?.value, key)
        validateSize()
        assert(initialSize == cache.size) { "cache size changed after insert" }

        return res?.value
    }

    override fun put(key: K, value: V) {
        validateSize()

        val initialSize = cache.size

        cache[key]?.let {
            remove(it.node)
        }

        if (lruList.getSize() == capacity) {
            evict()
        }

        val node = Node(key)
        addLast(node, value)

        validateSize()
        validateAccessedElementIsLast(cache[key]?.value, key)
        assert(cache[key]?.value?.equals(value) ?: false) { "added value is not in cache" }
        assert(initialSize <= cache.size) { "cache size decreased after put" }
    }

    private fun validateAccessedElementIsLast(res: V?, key: K) {
        assert(res == null || lruList.getTail().prev?.key?.equals(key) ?: false) {
            "least used key is not the one we just performed operation on"
        }
    }

    private fun validateSize() {
        assert(cache.size in 0..capacity) { "size out of 0..capacity bound" }
        assert(cache.size == lruList.getSize()) { "map size is inconsistent with linked list size" }
    }

    private fun remove(node: Node<K>) {
        assert(cache.isNotEmpty()) { "trying to remove from an empty cache" }

        lruList.remove(node)
        cache.remove(node.key)
    }

    private fun addLast(node: Node<K>, value: V) {
        assert(node.key != null) { "Attempting to add null key" }

        lruList.addLast(node)
        cache[node.key!!] = CacheEntry(value, node)
    }

    private fun evict() {
        val toEvict = lruList.evict()
        cache.remove(toEvict?.key)
    }

    private class CacheEntry<K, V>(val value: V, val node: Node<K>)
}