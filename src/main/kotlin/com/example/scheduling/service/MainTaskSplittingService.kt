package com.example.scheduling.service

import com.example.scheduling.model.MainTask
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class MainTaskSplittingService(
    private val kafkaTemplate: KafkaTemplate<String,String>,
    private val objectMapper: ObjectMapper) {

    fun splitMainTaskIntoSubTasks(mainTask:MainTask) {
        val subTasks = SubTaskCreator().createSubTasks(mainTask, partitionCount = 10)
        var partition : Int = 0
        subTasks.forEach {
            println("submitting sub task: $it")
            val message = objectMapper.writeValueAsString(it)
            val key = partition.toString()
            kafkaTemplate.send("subtask-topic",partition++,key,message).get()
        }
    }
}