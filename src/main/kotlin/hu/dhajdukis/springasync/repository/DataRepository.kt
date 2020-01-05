package hu.dhajdukis.springasync.repository

import hu.dhajdukis.springasync.entity.Data
import hu.dhajdukis.springasync.entity.State
import org.springframework.data.jpa.repository.JpaRepository

interface DataRepository : JpaRepository<Data, Long> {
    fun findByState(state: State): Set<Data>?
    fun findOneById(id: Long): Data
}
