package net.lusade.exposed.ulid

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.Table.Dual.clientDefault
import org.jetbrains.exposed.sql.vendors.PostgreSQLDialect
import org.jetbrains.exposed.sql.vendors.currentDialect
import org.postgresql.util.PGobject

/**
 * Column for storing ULID data in binary format.
 *
 * @param T The type of the ULID data.
 * @param serializer Class to serialize and deserialize the ULID data.
 */
class ULIDColumnType<T : Comparable<T>>(private val serializer: ULIDSerializer) : ColumnType() {
    override fun sqlType(): String = "ulid"

    override fun valueFromDB(value: Any): Any {
        return when {
            currentDialect is PostgreSQLDialect && value is PGobject && value.type == "ulid" -> serializer.deserialize<T>(value.value!!)
            value is String && value.matches(ulidRegex) -> serializer.deserialize(value)
            else -> error("Unexpected value of type ULID: $value of ${value::class.qualifiedName}")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun notNullValueToDB(value: Any) = when (currentDialect) {
        is PostgreSQLDialect -> PGobject().apply {
            type = sqlType()
            this.value = serializer.serialize(value as T)
        }
        else -> error("Unsupported dialect: ${currentDialect.name}")
    }

    @Suppress("UNCHECKED_CAST")
    override fun nonNullValueToString(value: Any): String = "'${serializer.serialize(value as T)}'"

    companion object {
        private val ulidRegex = Regex("^[0-9A-HJKMNP-TV-Z]{26}$")
    }
}

/**
 * Creates a column, with the specified [name], for storing ULID data in binary format.
 *
 * @param T The type of the ULID data.
 * @param name The name of the column.
 * @param serializer Class to serialize and deserialize the ULID data.
 * @return The column.
 */
fun <T : Comparable<T>> Table.ulid(
    name: String,
    serializer: ULIDSerializer
): Column<T> = registerColumn(name, ULIDColumnType<T>(serializer))

/**
 * ULID column will auto generate its value on a client side just before an insert.
 *
 * @param T The type of the ULID data.
 * @param ulidGenerator Function to generate the ULID data.
 * @return The column.
 */
fun <T : Comparable<T>> Column<T>.autoGenerate(ulidGenerator: () -> T): Column<T> = clientDefault { ulidGenerator() }
