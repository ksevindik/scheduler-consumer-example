package com.example.scheduling.service

import com.example.scheduling.model.Foo
import com.example.scheduling.model.SubTask
import com.example.scheduling.repository.FooRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubTaskExecutionService (private val fooRepository: FooRepository) {
    fun execute(subTask: SubTask) {
        for(i in subTask.range) {
            var foo = fooRepository.findById(i.toLong()).orElse(null)
            if(foo == null) {
                foo = Foo()
                foo.id = i.toLong()
                foo.status = "completed!"
                foo = fooRepository.save(foo)
            }
        }
    }
}