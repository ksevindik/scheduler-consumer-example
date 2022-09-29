package com.example.scheduling.config

import com.example.scheduling.job.MainTaskSplittingJob
import com.example.scheduling.job.PingJob
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class QuartzConfig {
    @Bean
    fun pingJobDetail() : JobDetail {
        return JobBuilder
            .newJob(PingJob::class.java)
            .withIdentity("pingJobDetail")
            .storeDurably()
            .build()
    }

    @Bean
    fun pingJobDetailTrigger(pingJobDetail: JobDetail) : Trigger {
        return TriggerBuilder
            .newTrigger()
            .forJob(pingJobDetail)
            .withIdentity("pingJobDetailTrigger")
            .withSchedule(CronScheduleBuilder.cronSchedule("0/15 * * * * ?"))
            .build()
    }
}