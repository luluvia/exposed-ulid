package net.lusade.exposed.ulid

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll

class ULIDTests : ShouldSpec({
    beforeSpec {
        val jdbcUrl = "jdbc:postgresql://localhost:5432/test"
        val username = "test"
        val password = "test"

        Database.connect(jdbcUrl, driver = "org.postgresql.Driver", user = username, password = password)
    }

    context("ULIDTable") {
        should("create a table with a ULID primary key") {
            withUlidTable { tester ->
                tester.selectAll().count() shouldBe 1
                tester.selectAll().map { it[tester.id].value } shouldBe listOf("01H8A9687C8Q2F566YEF8YNGEP")
            }
        }
        should("create a table with a ULID column") {
            withUlidTable { tester ->
                tester.selectAll().count() shouldBe 1
                tester.selectAll().map { it[tester.ulid] } shouldBe listOf("01H8A9687C8Q2F566YEF8YNGEP")
            }
        }

        should("create a table with a custom serializer") {
            withUlidTableCustomSerializer { tester ->
                tester.selectAll().count() shouldBe 1
                tester.selectAll().map { it[tester.id].value } shouldBe listOf("01H8A9687C8Q2F566YEF8YNGES")
            }
        }
    }

    context("ULIDEntity") {
        should("create an entity with a ULID primary key") {
            withUlidEntity { entity ->
                entity.id.value shouldBe "01H8A9687C8Q2F566YEF8YNGEP"
            }
        }

        should("create an entity with a ULID column") {
            withUlidEntity { entity ->
                entity.ulid shouldBe "01H8A9687C8Q2F566YEF8YNGEP"
            }
        }
    }
})
