package com.example.simpletimer.ui.show_timer

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simpletimer.R
import com.example.simpletimer.ui.show_timer.timer_item.TimerItem
import com.example.simpletimer.util.UiEvent
import kotlinx.coroutines.flow.collect

/***
 * the show timer screen including UI widgets, event trigger and receiver
 */

@Composable
fun ShowTimerScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    onSendNotification: (UiEvent.SendNotification) -> Unit,
    onShowToastMessage: (UiEvent.ShowToastMessage) -> Unit,
    viewModel: ShowTimerViewModel = hiltViewModel()
) {
    val timerList = viewModel.timersLiveData
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.loadTimerList()

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowToastMessage -> onShowToastMessage(event)
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
                title = { Text(text = stringResource(R.string.placeholder_timer)) }
            )
        },

        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(ShowTimerEvent.OnAddTimerClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(timerList) { index, timerObject ->
                TimerItem(
                    index = index,
                    timerObject = timerObject,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}