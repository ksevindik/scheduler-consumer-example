package com.example.scheduling.controller

import com.example.scheduling.job.MainTaskSplittingJob
import org.quartz.CalendarIntervalScheduleBuilder
import org.quartz.CronScheduleBuilder
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.quartz.TriggerKey
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Date

@RestController
@RequestMapping("/scheduler/jobs")
class ScheduledJobController(private val scheduler: Scheduler) {

    @PostMapping
    fun submitNewJob(@RequestBody request:NewJobRequestDTO) : ResponseEntity<Void> {
        val jobDetail = getOrCreateJobDetail(scheduler,"mainTaskSplitterJobDetail-${Date().time}", request)
        val trigger = getOrCreateTrigger(scheduler, "mainTaskSplitterJobDetailTrigger-${Date().time}")
        scheduler.scheduleJob(jobDetail,trigger)
        return ResponseEntity.ok().build()
    }

    private fun getOrCreateJobDetail(scheduler: Scheduler, jobName:String, request: NewJobRequestDTO) : JobDetail {
        var jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName))
        if(jobDetail == null) {
            jobDetail = JobBuilder
                .newJob(MainTaskSplittingJob::class.java)
                .usingJobData("start",request.start)
                .usingJobData("end",request.end)
                .withIdentity(jobName)
                .storeDurably()
                .requestRecovery(true)
                .build()
        }
        return jobDetail
    }

    private fun getOrCreateTrigger(scheduler: Scheduler, triggerName:String) : Trigger {
        var trigger = scheduler.getTrigger(TriggerKey.triggerKey(triggerName))
        if(trigger == null) {
            trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(triggerName)
                .withSchedule(SimpleScheduleBuilder
                    .simpleSchedule()
                    .withRepeatCount(0)
                    .withIntervalInSeconds(1))
                .build()
        }
        return trigger
    }
}

data class NewJobRequestDTO(val start:String,val end:String)