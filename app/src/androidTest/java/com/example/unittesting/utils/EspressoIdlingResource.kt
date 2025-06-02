package com.example.unittesting.utils

import androidx.test.espresso.idling.CountingIdlingResource
import kotlinx.coroutines.CoroutineDispatcher
import java.util.concurrent.atomic.AtomicInteger

object EspressoIdlingResource {
    val countingIdlingResource = CountingIdlingResource("RetrofitCall")

    fun increment() = countingIdlingResource.increment()
    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }

    fun getIdlingResource() = countingIdlingResource
}

class IdlingDispatcher(private val dispatcher: CoroutineDispatcher) : CoroutineDispatcher() {
    private val counter = AtomicInteger(0)

    override fun dispatch(context: kotlin.coroutines.CoroutineContext, block: Runnable) {
        EspressoIdlingResource.increment()
        dispatcher.dispatch(context) {
            try {
                block.run()
            } finally {
                EspressoIdlingResource.decrement()
            }
        }
    }
}