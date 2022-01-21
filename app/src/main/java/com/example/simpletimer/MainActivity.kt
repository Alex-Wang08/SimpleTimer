package com.example.simpletimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.simpletimer.timer_add.TimerAddScreen
import com.example.simpletimer.timer_list.TimerListScreen
import com.example.simpletimer.ui.theme.SimpleTimerTheme
import com.example.simpletimer.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.statusBarColor = ContextCompat.getColor(this,R.color.blue)

        setContent {
            SimpleTimerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.TIMER_LIST
                ) {
                    composable(Routes.TIMER_LIST) {
                        TimerListScreen(onNavigate = { navController.navigate(it.route) })
                    }

                    composable(Routes.TIMER_ADD) {
                        TimerAddScreen(onPopBackStack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}
