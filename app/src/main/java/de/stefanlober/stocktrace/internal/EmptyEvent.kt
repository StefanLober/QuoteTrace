package de.stefanlober.stocktrace.internal

open class EmptyEvent {
    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun handle(): Boolean {
        return if (hasBeenHandled)
            false
        else {
            hasBeenHandled = true
            true
        }
    }
}