package com.example.simpletimer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/***
 * needed for hilt injection, need to be referenced in AndroidManifest
 */

@HiltAndroidApp
class SimpleTimerApp: Application()