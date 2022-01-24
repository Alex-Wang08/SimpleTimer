package com.example.simpletimer

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/***
 * hold data for communication between the two screens
 */
@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {
    var hasDatasetChanged: Boolean = false
}