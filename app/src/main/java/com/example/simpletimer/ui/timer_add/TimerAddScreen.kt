package com.example.simpletimer.ui.timer_add

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simpletimer.ui.theme.Blue
import com.example.simpletimer.util.UiEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect

@Composable
fun TimerAddScreen(
    onPopBackStack: () -> Unit,
    viewModel: TimerAddViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val timeShow = viewModel.time.observeAsState(initial = "00:00:00")
    val length = timeShow.value.length

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = true) {
        // auto pop up the keyboard
        delay(300)
        focusRequester.requestFocus()

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
                title = { Text("") },
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

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "Timer",
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = TextFieldValue(text = timeShow.value, selection = TextRange(length, length)), // keep cursor at the end
                onValueChange = {
                    viewModel.onEvent(TimerAddEvent.OnTimeChange(it.text))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically)
                    .focusRequester(focusRequester),

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 60.sp
                ),

                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor =  Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color.Transparent
                )
            )
        }
    }

}