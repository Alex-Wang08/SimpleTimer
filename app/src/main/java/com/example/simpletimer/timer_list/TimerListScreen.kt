package com.example.simpletimer.timer_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
            when (event) {
                is UiEvent.ShowSnackBar -> {

                }

                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Timer") }
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(TimerListEvent.OnAddTimerClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(timers) { timer ->
                TimerItem(
                    timer = { timer },
                    onEvent = viewModel::onEvent
                )
            }
        }
    }
}