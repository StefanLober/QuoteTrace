package de.stefanlober.stocktrace

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*

private val THREAD_POOL_EXECUTOR: Executor = ThreadPoolExecutor(5, 128, 1, TimeUnit.SECONDS, LinkedBlockingQueue())

class TaskRunner {
    private val executor: Executor = THREAD_POOL_EXECUTOR
    private val handler: Handler = Handler(Looper.getMainLooper())

    fun <R> executeAsync(callable: Callable<R>, callback: (R) -> Unit) {
        executor.execute {
            val result: R = callable.call()
            handler.post { callback(result) }
        }
    }
}