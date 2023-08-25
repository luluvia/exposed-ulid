package net.lusade.exposed.ulid

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun withUlidTable(
    statement: Transaction.(tester: ULIDTestsData.UlidTable) -> Unit
) {
    val tester = ULIDTestsData.UlidTable

    transaction {
        SchemaUtils.create(tester)

        tester.insert { it[ulid] = "01H8A9687C8Q2F566YEF8YNGEP" }

        statement(tester)

        SchemaUtils.drop(tester)
    }
}

fun withUlidEntity(
    statement: Transaction.(entity: ULIDTestsData.UlidEntity) -> Unit
) {
    val table = ULIDTestsData.UlidTable

    transaction {
        SchemaUtils.create(table)

        val entity = ULIDTestsData.UlidEntity.new("01H8A9687C8Q2F566YEF8YNGEP") { ulid = "01H8A9687C8Q2F566YEF8YNGEP" }

        statement(entity)

        SchemaUtils.drop(table)
    }
}

fun withUlidTableCustomSerializer(
    statement: Transaction.(tester: ULIDTestsData.UlidTableCustomSerializer) -> Unit
) {
    val tester = ULIDTestsData.UlidTableCustomSerializer

    transaction {
        SchemaUtils.create(tester)

        tester.insert { it[id] = "01H8A9687C8Q2F566YEF8YNGEP" }

        statement(tester)

        SchemaUtils.drop(tester)
    }
}

fun withCulidTable(
    statement: Transaction.(tester: ULIDTestsData.CulidTable) -> Unit
) {
    val tester = ULIDTestsData.CulidTable

    transaction {
        SchemaUtils.create(tester)

        tester.insert { it[culid] = "01H8A9687C8Q2F566YEF8YNGEP" }

        statement(tester)

        SchemaUtils.drop(tester)
    }
}

fun withCulidEntity(
    statement: Transaction.(entity: ULIDTestsData.CulidEntity) -> Unit
) {
    val table = ULIDTestsData.CulidTable

    transaction {
        SchemaUtils.create(table)

        val entity = ULIDTestsData.CulidEntity.new("01H8A9687C8Q2F566YEF8YNGEP") { culid = "01H8A9687C8Q2F566YEF8YNGEP" }

        statement(entity)

        SchemaUtils.drop(table)
    }
}
