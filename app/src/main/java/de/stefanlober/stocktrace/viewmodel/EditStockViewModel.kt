package de.stefanlober.stocktrace.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditStockViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

}