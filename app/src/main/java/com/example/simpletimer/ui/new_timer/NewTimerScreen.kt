package com.example.simpletimer.ui.new_timer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.simpletimer.R
import com.example.simpletimer.ui.theme.Blue
import com.example.simpletimer.util.UiEvent
import kotlinx.coroutines.flow.collect

/***
 * the new timer screen including UI widgets, event trigger and receiver
 */

@Composable
fun NewTimerScreen(
    onPopBackStack: () -> Unit,
    onShowToastMessage: (UiEvent.ShowToastMessage) -> Unit,
    viewModel: NewTimerViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val length = viewModel.timeString.length

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = true) {
        // auto pop up the keyboard
        focusRequester.requestFocus()

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowToastMessage -> onShowToastMessage(event)
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
                        viewModel.onEvent(NewTimerEvent.OnCancelClick)
                    }) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "")

                    }
                },
                actions = {
                    TextButton(onClick = {
                        viewModel.onEvent(NewTimerEvent.OnSaveTimerClick)
                    }) {
                        Text(
                            text = stringResource(id = R.string.label_set),
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
            TextField(
                value = viewModel.timerLabel,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.placeholder_timer),
                        fontSize = 20.sp
                    )
                },
                onValueChange = {
                    viewModel.onEvent(NewTimerEvent.OnLabelChange(it))
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = LocalTextStyle.current.copy(
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = TextFieldValue(
                    text = viewModel.timeString,
                    selection = TextRange(length, length)
                ), // keep cursor at the end
                onValueChange = {
                    viewModel.onEvent(NewTimerEvent.OnTimeChange(it.text))
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
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = Color.Transparent
                )
            )
        }
    }

}