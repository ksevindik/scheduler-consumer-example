package com.example.scheduling.service

import org.springframework.stereotype.Service

@Service
class PingService {
    fun ping() {
        println("pinging...")
    }
}