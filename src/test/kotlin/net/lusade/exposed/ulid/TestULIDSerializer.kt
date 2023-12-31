package net.lusade.exposed.ulid

object TestULIDSerializer : ULIDSerializer {
    override fun <T> serialize(value: T): String {
        return value.toString()
    }

    override fun <T> deserialize(value: String): T {
        @Suppress("UNCHECKED_CAST")
        return value as T
    }
}
