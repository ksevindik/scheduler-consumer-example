package com.example.scheduling.controller

import com.example.scheduling.model.SubTask
import com.example.scheduling.service.SubTaskExecutionService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.autoconfigure.jms.JmsProperties.AcknowledgeMode
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class SubTaskMessageConsumer (private val subTaskExecutionService: SubTaskExecutionService,
                              private val objectMapper: ObjectMapper) {
    @KafkaListener(topics = ["subtask-topic"], groupId = "subtask-consumer-group")
    fun handle(message:String, ack:Acknowledgment) {
        println("consuming message: $message")
        val subTask = objectMapper.readValue(message,SubTask::class.java)
        subTaskExecutionService.execute(subTask)
        ack.acknowledge()
    }
}