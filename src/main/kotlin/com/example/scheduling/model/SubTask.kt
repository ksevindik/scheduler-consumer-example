package com.example.scheduling.model

data class SubTask(val range:IntRange) {
    override fun toString(): String {
        return "${range.first}-${range.last}"
    }
}