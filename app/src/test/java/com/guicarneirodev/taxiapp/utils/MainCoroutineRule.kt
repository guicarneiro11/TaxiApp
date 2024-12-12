package com.guicarneirodev.taxiapp.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainCoroutineRule : TestWatcher() {
    private val testCoroutineDispatcher = UnconfinedTestDispatcher()
    private val testCoroutineScope = TestScope(testCoroutineDispatcher)

    override fun starting(description: Description) {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }

    fun runTest(block: suspend TestScope.() -> Unit) = testCoroutineScope.runTest { block() }
}