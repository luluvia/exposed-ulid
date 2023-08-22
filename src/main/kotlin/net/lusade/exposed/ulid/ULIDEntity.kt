package net.lusade.exposed.ulid

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID

abstract class ULIDEntity<T : Comparable<T>>(id: EntityID<T>) : Entity<T>(id)
