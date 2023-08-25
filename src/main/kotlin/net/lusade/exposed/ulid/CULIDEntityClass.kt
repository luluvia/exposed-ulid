package net.lusade.exposed.ulid

import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

abstract class CULIDEntityClass<T : Comparable<T>, out E : ULIDEntity<T>>(
    table: CULIDTable<T>,
    entityType: Class<E>? = null,
    entityCtor: ((EntityID<T>) -> E)? = null
) : EntityClass<T, E>(table, entityType, entityCtor)
