package com.example.simpletimer.timer_list

import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simpletimer.R
import com.example.simpletimer.data.Timer

@Composable
fun TimerItem(
    timer: Timer,
    onEvent: (TimerListEvent) -> Unit,
    modifier: Modifier = Modifier
) {
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
                        text = timer.label,
                        fontSize = 20.sp,
                        modifier = Modifier.weight(1f)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "00:00:00",
                        fontSize = 60.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Box(
                        modifier = Modifier.clickable {
                            /*todo: play or pause timer*/
                        }
                    ) {
                        if (timer.isTimerRunning) {
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