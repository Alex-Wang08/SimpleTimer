package com.example.simpletimer.ui.show_timer.timer_item

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpletimer.R
import com.example.simpletimer.data.TimerObject
import com.example.simpletimer.ui.show_timer.ShowTimerEvent

@Composable
fun TimerItem(
    timerObject: TimerObject,
    index: Int,
    onEvent: (ShowTimerEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = true) {
        onEvent(ShowTimerEvent.OnAutoStartTimer(timerObject, index))
    }
    Box(
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = timerObject.label,
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .clickable {
                                onEvent(ShowTimerEvent.OnDeleteTimerClick(timerObject, index))
                            }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = timerObject.currentTime,
                        fontSize = 60.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .clickable {
                                onEvent(ShowTimerEvent.OnTimerStateChange(timerObject, index))
                            }
                    ) {
                        if (timerObject.isRunning) {
                            Image(
                                painter = painterResource(R.drawable.ic_pause_circle_outline),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.ic_play_circle_outline),
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}