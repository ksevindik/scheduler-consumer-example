package com.example.scheduling.job

import com.example.scheduling.service.PingService
import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

@Component
class PingJob(private val pingService: PingService) : QuartzJobBean() {
    override fun executeInternal(context: JobExecutionContext) {
        pingService.ping()
    }
}