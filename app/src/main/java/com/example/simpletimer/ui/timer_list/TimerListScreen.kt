package com.example.simpletimer.ui.timer_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simpletimer.ui.timer_list.timer_item.TimerItem
import com.example.simpletimer.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun TimerListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onSendNotification: (UiEvent.SendNotification) -> Unit,
    viewModel: TimerListViewModel = hiltViewModel()
) {
    val timerList = viewModel.timersLiveData
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {

                }
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.SendNotification -> onSendNotification(event)

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
            itemsIndexed(timerList) { index, timer  ->
                TimerItem(
                    index = index,
                    timer = timer,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}