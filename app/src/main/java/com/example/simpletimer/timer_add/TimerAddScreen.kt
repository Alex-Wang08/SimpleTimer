package com.example.simpletimer.timer_add

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TimerAddScreen(
    onPopBackStack: () -> Unit,
    viewModel: TimerAddViewModel = hiltViewModel()
) {
    Text(text = "add timer screen")
}