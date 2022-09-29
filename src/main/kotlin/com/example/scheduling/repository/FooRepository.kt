package com.example.scheduling.repository

import com.example.scheduling.model.Foo
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

interface FooRepository : CrudRepository<Foo,Long> {
    @Modifying
    @Query("delete from Foo")
    @Transactional
    override fun deleteAll()
}