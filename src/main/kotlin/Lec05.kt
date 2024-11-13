package coroutine

import kotlinx.coroutines.*

fun example6(): Unit = runBlocking {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ -> printWithThread("예외") }
    val job = CoroutineScope(Dispatchers.Default)
        .launch(coroutineExceptionHandler) {
        throw IllegalStateException()
    }

    delay(1_000L)
}

fun example5(): Unit = runBlocking {
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, _ -> printWithThread("예외") }
    val job = launch(coroutineExceptionHandler) { // 부모 코루틴이 있으면 handler 동작하지 않음
        throw IllegalStateException()
    }

    delay(1_000L)
}

fun example4(): Unit = runBlocking {
    val job = async(SupervisorJob()) { // SupervisorJob 으로 만들면 예외를 부모에게 전파하지 않음
        throw IllegalStateException()
    }

    delay(1_000L)
    job.await()
}

fun example3(): Unit = runBlocking {
    val job = async {
        throw IllegalStateException()
    }

    delay(1_000L)
//    job.await() // 자식 async 코루틴의 경우 부모에게 예외를 전파하기 때문에 await 을 호출하지 않아도 runBlocking 이 예외를 터트린다.
}

fun example2(): Unit = runBlocking {
    val job = CoroutineScope(Dispatchers.Default).async {
        throw IllegalStateException()
    }

    delay(1_000L)
    job.await() // await 을 호출하지 않으면 예외 발생하지 않음
}

fun example1(): Unit = runBlocking {
    val job = CoroutineScope(Dispatchers.Default).launch {
        throw IllegalStateException()
    }

    delay(1_000L)
}
