package net.schwiz.marvel.core

import TestCoroutineRule
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.junit.runner.RunWith
import net.schwiz.marvel.core.TestEvents.*
import net.schwiz.marvel.core.TestActions.*
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


@RunWith(AndroidJUnit4::class)
class TestBaseViewModel {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val model = TestModel()

    private val testEvents = listOf(Event1, Event1, Event2)
    private val testActions = listOf(Test1, Test1, Test2)


    @Test
    fun testEventTransform(){
        val list = testCoroutineRule.asyncTest {
            model.actions.take(testEvents.size).toList()
        }
        model.acceptEvents(testEvents.asFlow())

        testCoroutineRule.runBlockingTest {
            list.await().let {
                println(it)
                assertEquals(testActions, it)
            }
        }
    }
}