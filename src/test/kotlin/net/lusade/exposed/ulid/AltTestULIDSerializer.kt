package net.lusade.exposed.ulid

object AltTestULIDSerializer : ULIDSerializer {
    override fun <T> serialize(value: T): String {
        return value.toString().dropLast(1) + "S"
    }

    override fun <T> deserialize(value: String): T {
        @Suppress("UNCHECKED_CAST")
        return value as T
    }
}
