package net.lusade.exposed.ulid

import java.nio.ByteBuffer
import java.util.UUID

object TestCULIDSerializer : ULIDSerializer {
    // This is hacky, but it works for the purposes of testing.
    override fun <T> serialize(value: T): String {
        if (value.toString().matches(Regex("^[0-9A-HJKMNP-TV-Z]{26}$"))) {
            return "018a1493-20ec-45c4-f298-de73d1eac1d6"
        }

        error("Unexpected value of type ULID: $value of ${value!!::class.qualifiedName}")
    }

    override fun <T> deserialize(value: String): T {
        @Suppress("UNCHECKED_CAST")
        if (value == "018a1493-20ec-45c4-f298-de73d1eac1d6") {
            return "01H8A9687C8Q2F566YEF8YNGEP" as T
        }

        error("Unexpected value of type ULID: $value of ${value::class.qualifiedName}")
    }
}
