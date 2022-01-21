package com.example.simpletimer.timer_list

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.room.util.TableInfo
import com.example.simpletimer.data.Timer

@Composable
fun TimerItem(
    timer: Timer,
    onEvent: (TimerListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = timer.label
        )
    }
}