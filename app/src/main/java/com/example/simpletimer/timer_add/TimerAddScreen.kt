package com.example.simpletimer.timer_add

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simpletimer.ui.theme.Blue
import com.example.simpletimer.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun TimerAddScreen(
    onPopBackStack: () -> Unit,
    viewModel: TimerAddViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("")},
                backgroundColor = Blue,
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.onEvent(TimerAddEvent.OnCancelClick)
                    }) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "")

                    }
                },
                actions = {
                    TextButton(onClick = {
                        viewModel.onEvent(TimerAddEvent.OnSaveTimerClick)
                    }) {
                        Text(
                            text = "SET",
                            color = Color.White,
                        )
                    }
                }
            )
        }


    ) {

        Column() {



        }

    }

}