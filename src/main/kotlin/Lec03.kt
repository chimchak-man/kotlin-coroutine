package coroutine

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main(): Unit = runBlocking {
    val times = measureTimeMillis {
        val job1 = async { apiCall1() }
        val job2 = async { apiCall2(job1.await()) }
        printWithThread(job2.await())
    }

    printWithThread("소요시간: ${times}")
}

suspend fun apiCall1(): Int {
    delay(1_000L)
    return 1
}

suspend fun apiCall2(num: Int): Int {
    delay(1_000L)
    return num + 2
}

fun asyncExample1(): Unit = runBlocking {
    val job = async {
        delay(2_000L)
        3 + 5
    }
    printWithThread("await 전")
    val result = job.await()
    printWithThread("await 후")
    printWithThread(result)
}

fun joinExample(): Unit = runBlocking {
    val job1 = launch {
        delay(1_000L)
        printWithThread("Job1 end")
    }
    job1.join()
    val job2 = launch {
        delay(1_000L)
        printWithThread("Job2 end")
    }
}

fun cancelExample(): Unit = runBlocking {
    val job = launch {
        (1..5).forEach {
            printWithThread(it)
            delay(500)
        }
    }
    delay(1_000L)
    job.cancel()
    printWithThread("JOB CANCEL")
}

fun startExample(): Unit = runBlocking {
    val job = launch(start = CoroutineStart.LAZY) {
        printWithThread("hello launch")
    }

    delay(1_000L)
    job.start()
}

fun example() {
    runBlocking {
        printWithThread("START")
        launch {
            printWithThread("DELAY LAUNCH START")
            delay(2_000L)
            printWithThread("DELAY LAUNCH END")
        }
        launch {
            printWithThread("ADD LAUNCH START")
            printWithThread("${1 + 2 + 3}")
            printWithThread("ADD LAUNCH END")
        }
        printWithThread("delay")
        delay(1_000L)
    }

    printWithThread("END")
}
