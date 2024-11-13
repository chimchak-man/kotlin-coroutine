package coroutine

import kotlinx.coroutines.*

fun main(): Unit = runBlocking {
    val job = launch(Dispatchers.Default) {
        var i = 1
        var currentTimeMills = System.currentTimeMillis()

        while (i <= 5) {
            if (currentTimeMills <= System.currentTimeMillis()) {
                printWithThread("${i++}번째 출력")
                currentTimeMills += 1_000L
            }

            if (isActive) {
                throw CancellationException()
            }
        }
    }
    delay(500L)
    job.cancel()
}
