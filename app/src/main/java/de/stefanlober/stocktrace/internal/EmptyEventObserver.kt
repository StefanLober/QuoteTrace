package de.stefanlober.stocktrace.internal

import androidx.lifecycle.Observer

class EmptyEventObserver(private val onEventUnhandledContent: () -> Unit) : Observer<EmptyEvent> {
    override fun onChanged(event: EmptyEvent?) {
        event?.let {
            if (event.handle())
                onEventUnhandledContent()
        }
    }
}