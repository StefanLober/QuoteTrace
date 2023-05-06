package de.stefanlober.quotetrace.internal

import androidx.lifecycle.Observer

class EmptyEventObserver(private val onEventUnhandledContent: () -> Unit) : Observer<EmptyEvent> {
    override fun onChanged(value: EmptyEvent) {
        value.let {
            if (value.handle())
                onEventUnhandledContent()
        }
    }
}