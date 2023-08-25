package net.lusade.exposed.ulid

import org.jetbrains.exposed.dao.id.EntityID
import javax.swing.text.html.parser.Entity

object ULIDTestsData {
    object UlidTable : ULIDTable<String>(
        name = "ulid_table",
        serializer = TestULIDSerializer,
        ulidGenerator = { "01H8A9687C8Q2F566YEF8YNGEP" }
    ) {
        val ulid = ulid<String>("ulid", TestULIDSerializer)
    }

    object UlidTableCustomSerializer : ULIDTable<String>(
        serializer = AltTestULIDSerializer,
        ulidGenerator = { "01H8A9687C8Q2F566YEF8YNGEP" }
    )

    object CulidTable : CULIDTable<String>(
        name = "culid_table",
        serializer = TestCULIDSerializer,
        ulidGenerator = { "01H8A9687C8Q2F566YEF8YNGEP" }
    ) {
        val culid = culid<String>("culid", TestCULIDSerializer)
    }

    class UlidEntity(id: EntityID<String>) : ULIDEntity<String>(id) {
        companion object : ULIDEntityClass<String, UlidEntity>(UlidTable)
        var ulid by UlidTable.ulid
    }

    class CulidEntity(id: EntityID<String>) : ULIDEntity<String>(id) {
        companion object : CULIDEntityClass<String, CulidEntity>(CulidTable)
        var culid by CulidTable.culid
    }
}
