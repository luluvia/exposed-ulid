package net.lusade.exposed.ulid

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

/**
 * Identity table with ULID primary key.
 * This version of the table uses UUID as the underlying data type.
 *
 * @param T The type of the ULID data.
 * @param name The name of the table.
 * @param columnName The name of the column.
 * @param serializer Class to serialize and deserialize the ULID data.
 * @param ulidGenerator Function to generate a ULID.
 */
open class CULIDTable<T : Comparable<T>>(
    name: String = "",
    columnName: String = "id",
    serializer: ULIDSerializer,
    ulidGenerator: () -> T
) : IdTable<T>(name) {
    final override val id: Column<EntityID<T>> = culid<T>(columnName, serializer).autoGenerate(ulidGenerator).entityId()
    final override val primaryKey = PrimaryKey(id)
}
