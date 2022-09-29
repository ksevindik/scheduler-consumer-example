package com.example.scheduling.service

import com.example.scheduling.model.MainTask
import com.example.scheduling.model.SubTask

class SubTaskCreator {
    fun createSubTasks(mainTask: MainTask, partitionCount:Int) : List<SubTask> {
        //assigning sub tasks to each partition is similar to paging
        val pageSize = mainTask.range.last / partitionCount
        var remainingElements = mainTask.range.last % partitionCount
        var subTasks = mutableListOf<SubTask>()
        var start = 0
        var end = 0
        for(page in 1..partitionCount) {
            if(page == 1) {
                start = 1
                end = start + pageSize - 1
            } else {
                start = subTasks[subTasks.size-1].range.last + 1
                end = start + pageSize - 1
            }
            if(remainingElements>0) {
                remainingElements--
                end += 1
            }
            subTasks.add(SubTask(start..end))
        }
        return subTasks
    }
}