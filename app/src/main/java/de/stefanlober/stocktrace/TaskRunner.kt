package de.stefanlober.stocktrace

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*

private val THREAD_POOL_EXECUTOR: Executor = ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, LinkedBlockingQueue())

class TaskRunner {
    private val executor: Executor = THREAD_POOL_EXECUTOR
    private val handler: Handler = Handler(Looper.getMainLooper())

    fun executeAsync(callable: () -> Unit, callback: () -> Unit) {
        executor.execute {
            callable()
            handler.post { callback() }
        }
    }

    fun <R> executeAsync(data: R, callable: (R) -> Unit, callback: (R) -> Unit) {
        executor.execute {
            callable(data)
            handler.post { callback(data) }
        }
    }
}