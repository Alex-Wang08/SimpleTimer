package com.example.simpletimer

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simpletimer.ui.new_timer.NewTimerScreen
import com.example.simpletimer.ui.show_timer.ShowTimerScreen
import com.example.simpletimer.ui.theme.SimpleTimerTheme
import com.example.simpletimer.util.Routes
import dagger.hilt.android.AndroidEntryPoint

/****
 * navigation is applied that the two screens are place in MainActivity and controlled by NaVController
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //region Constants
    companion object {
        const val CHANNEL_ID = "channelId"
        const val CHANNEL_NAME = "channelName"
        const val NOTIFICATION_ID = 0
    }
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.statusBarColor = ContextCompat.getColor(this, R.color.blue)

        createNotificationChannel()
        val notificationManager = NotificationManagerCompat.from(this)

        setContent {
            SimpleTimerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.TIMER_LIST
                ) {
                    composable(Routes.TIMER_LIST) {
                        ShowTimerScreen(
                            onNavigate = {
                                navController.navigate(it.route)
                            },
                            onSendNotification = {
                                notificationManager.notify(NOTIFICATION_ID, createNotification(it.label))
                            },
                            onShowToastMessage = {
                                Toast.makeText(
                                    this@MainActivity,
                                    resources.getString(R.string.toast_massage_timer_already_is_running),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }

                    composable(Routes.TIMER_ADD) {
                        NewTimerScreen(
                            onPopBackStack = {
                                navController.popBackStack()
                            },
                            onShowToastMessage = {
                                Toast.makeText(
                                    this@MainActivity,
                                    resources.getString(R.string.toast_message_no_value_warning),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                }
            }
        }
    }

    //region Private Helpers
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.GREEN
                enableLights(true)
            }

            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(label: String): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(resources.getString(R.string.app_name))
            .setContentText(resources.getString(R.string.notification_content, label))
            .setSmallIcon(R.drawable.ic_alarm_on)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }
    //endregion
}
