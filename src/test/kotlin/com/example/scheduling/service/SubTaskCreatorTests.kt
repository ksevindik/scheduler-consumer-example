package com.example.scheduling.service

import com.example.scheduling.model.MainTask
import com.example.scheduling.model.SubTask
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test

class SubTaskCreatorTests {
    @Test
    fun `it should create list of subtasks for given main task with range 1 to 999 and partition count 10`() {
        //given
        val mainTask = MainTask(1..999)
        val partitionCount = 10
        val creator = SubTaskCreator()
        //when
        val subTasks = creator.createSubTasks(mainTask,partitionCount)
        //then
        println(subTasks)
        MatcherAssert.assertThat(subTasks, Matchers.contains(
            SubTask(1..100),
            SubTask(101..200),
            SubTask(201..300),
            SubTask(301..400),
            SubTask(401..500),
            SubTask(501..600),
            SubTask(601..700),
            SubTask(701..800),
            SubTask(801..900),
            SubTask(901..999)))
    }

    @Test
    fun `it should create list of subtasks for given main task with range 1 to 77 and partition count 15`() {
        //given
        val mainTask = MainTask(1..77)
        val partitionCount = 15
        val creator = SubTaskCreator()
        //when
        val subTasks = creator.createSubTasks(mainTask,partitionCount)
        //then
        println(subTasks)
        MatcherAssert.assertThat(subTasks, Matchers.contains(
            SubTask(1..6),
            SubTask(7..12),
            SubTask(13..17),
            SubTask(18..22),
            SubTask(23..27),
            SubTask(28..32),
            SubTask(33..37),
            SubTask(38..42),
            SubTask(43..47),
            SubTask(48..52),
            SubTask(53..57),
            SubTask(58..62),
            SubTask(63..67),
            SubTask(68..72),
            SubTask(73..77)
        ))
    }
}