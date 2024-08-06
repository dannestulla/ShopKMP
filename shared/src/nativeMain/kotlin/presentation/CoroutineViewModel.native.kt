package presentation

import kotlinx.coroutines.CoroutineScope

actual abstract class CoroutineViewModel actual constructor() {
    actual val coroutineScope: CoroutineScope
        get() = TODO("Not yet implemented")

    actual fun dispose() {
    }

    protected actual open fun onCleared() {
    }

}