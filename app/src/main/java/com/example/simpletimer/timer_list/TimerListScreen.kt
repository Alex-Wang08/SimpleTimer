package com.example.simpletimer.timer_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simpletimer.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun TimerListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TimerListViewModel = hiltViewModel()
) {
    val timers = viewModel.timers
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.ShowSnackBar -> {

                }

                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(TimerListEvent.OnAddTimerClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = Color.Blue,
                    contentDescription = "Add"
                )
            }
        }
    ) {
        Text(text = "timer list screen")

    }








}