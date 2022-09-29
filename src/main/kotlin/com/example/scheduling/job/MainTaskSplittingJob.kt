package com.example.scheduling.job

import com.example.scheduling.model.MainTask
import com.example.scheduling.service.MainTaskSplittingService
import org.quartz.DisallowConcurrentExecution
import org.quartz.JobExecutionContext
import org.springframework.scheduling.quartz.QuartzJobBean
import org.springframework.stereotype.Component

@Component
@DisallowConcurrentExecution
class MainTaskSplittingJob(private val mainTaskSplittingService: MainTaskSplittingService) : QuartzJobBean(){
    override fun executeInternal(context: JobExecutionContext) {
        val mainTask = MainTask(1..999)
        mainTaskSplittingService.splitMainTaskIntoSubTasks(mainTask)
    }
}