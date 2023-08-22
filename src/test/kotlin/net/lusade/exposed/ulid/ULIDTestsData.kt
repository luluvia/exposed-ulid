package net.lusade.exposed.ulid

import org.jetbrains.exposed.dao.id.EntityID

object ULIDTestsData {
    object UlidTable : ULIDTable<String>(
        name = "ulid_table",
        serializer = TestULIDSerializer,
        ulidGenerator = { "01H8A9687C8Q2F566YEF8YNGEP" }
    ) {
        val ulid = ulid<String>("ulid", TestULIDSerializer)
    }

    object UlidTableCustomSerializer : ULIDTable<String>(
        serializer = UlidTestSerializer,
        ulidGenerator = { "01H8A9687C8Q2F566YEF8YNGEP" }
    )

    class UlidEntity(id: EntityID<String>) : ULIDEntity<String>(id) {
        companion object : ULIDEntityClass<String, UlidEntity>(UlidTable)
        var ulid by UlidTable.ulid
    }

    object UlidTestSerializer : ULIDSerializer {
        override fun <T> serialize(value: T): String {
            return value.toString().dropLast(1) + "S"
        }

        override fun <T> deserialize(value: String): T {
            @Suppress("UNCHECKED_CAST")
            return value as T
        }
    }
}
