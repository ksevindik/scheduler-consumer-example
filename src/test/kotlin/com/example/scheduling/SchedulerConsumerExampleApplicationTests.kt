package com.example.scheduling

import org.hamcrest.MatcherAssert
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.quartz.Scheduler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

class SchedulerConsumerExampleApplicationTests : BaseIntegrationTests() {

	@Autowired
	private lateinit var scheduler:Scheduler


	@Test
	fun contextLoads() {
		//openH2Console()
		Assertions.assertNotNull(scheduler)
	}

}
