package com.example.scheduling

import com.example.scheduling.repository.FooRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import java.util.TimeZone

@SpringBootApplication
class SchedulerConsumerExampleApplication {
	init {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
	}

	@Autowired
	private lateinit var fooRepository: FooRepository

	@EventListener
	fun handle(event:ContextRefreshedEvent) {
		fooRepository.deleteAll()
	}
}

fun main(args: Array<String>) {
	runApplication<SchedulerConsumerExampleApplication>(*args)
}
