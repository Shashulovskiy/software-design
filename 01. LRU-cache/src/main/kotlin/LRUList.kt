class LRUList<K> {
    private val lruHead: Node<K> = Node(null)
    private val lruTail: Node<K> = Node(null)
    private var size = 0

    init {
        lruHead.next = lruTail
        lruTail.prev = lruHead
    }

    fun remove(node: Node<K>) {
        assert(size > 0) { "attempt to remove from an empty list" }
        assert(node.prev != null) { "node.prev is null, attempt to delete head?" }
        assert(node.next != null) { "node.next is null, attempt to delete tail?" }

        node.prev?.next = node.next
        node.next?.prev = node.prev
        size--
    }

    fun addLast(node: Node<K>) {
        assert(lruTail.prev != null) { "lruTail.prev is null, missing head?" }

        lruTail.prev?.next = node
        node.prev = lruTail.prev
        lruTail.prev = node
        node.next = lruTail
        size++

        assert(lruTail.prev?.key?.equals(node.key) ?: false) { "node was not added to the end of the list" }
    }

    fun evict(): Node<K>? {
        assert(size > 0) { "attempt to remove from an empty list" }

        val toEvict = lruHead.next
        lruHead.next = toEvict?.next
        lruHead.next?.prev = lruHead
        size--
        return toEvict
    }

    fun getSize(): Int = size
    fun getTail(): Node<K> = lruTail
}

class Node<K>(val key: K?) {
    var prev: Node<K>? = null
    var next: Node<K>? = null
}