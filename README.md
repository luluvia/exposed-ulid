# ULID in Exposed
<a href="https://opensource.org/licenses/MIT"><img src="https://img.shields.io/badge/License-MIT-blue.svg"/></a>
<a href="https://central.sonatype.com/artifact/net.lusade/exposed-ulid"><img src="https://staging.shields.io/maven-central/v/net.lusade/exposed-ulid"/></a>

[ULID](https://github.com/ulid/spec) (Universally Unique Lexicographically Sortable Identifier) data type integration
within [Exposed](https://github.com/JetBrains/Exposed), the Kotlin SQL Framework. This library works with PostgreSQL,
through the [pgx_ulid](https://github.com/pksunkara/pgx_ulid) extension. If other databases support ULID, this library
will be updated to support them.

## Installation

### Gradle

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'net.lusade:exposed-ulid:0.1.0'
}
```

### Gradle Kotlin DSL

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("net.lusade:exposed-ulid:0.1.0")
}
```

### Maven

```xml
<dependency>
    <groupId>net.lusade</groupId>
    <artifactId>exposed-ulid</artifactId>
    <version>0.1.0</version>
</dependency>
```

## Usage

### Setup

For every ULID column and ULID identity table, you must provide a ULID serializer. This must be an object that implements
the `ULIDSerializer` interface. The `ULIDSerializer` interface has two methods, `serialize` and `deserialize`. The `serialize`
method takes a `ULID` object and returns a `String`. The `deserialize` method takes a `String` and returns a `ULID` object.

```kotlin
// This is an example ULID serializer. You will need to provide your own.
object CustomULIDSerializer : ULIDSerializer {
    override fun <T> serialize(value: T): String {
        return value.toString()
    }

    override fun <T> deserialize(value: String): T {
        @Suppress("UNCHECKED_CAST")
        return value as T
    }
}
```

### ULID Column Type

```kotlin
object MyTable : IntIdTable() {
    val myId = ulid("my_id", CustomULIDSerializer)
}
```

### ULID Identity Table

Here, the `ULIDTable` class is used to create a table with a ULID primary key.

```kotlin
object MyTable : ULIDTable() {
    val myId = ulid("my_id", CustomULIDSerializer)
}
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.
