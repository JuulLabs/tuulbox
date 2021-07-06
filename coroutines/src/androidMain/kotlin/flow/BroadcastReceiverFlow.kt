package com.juul.tuulbox.coroutines.flow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.juul.tuulbox.coroutines.applicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@Suppress("EXPERIMENTAL_API_USAGE")
public fun broadcastReceiverFlow(
    intentFilter: IntentFilter
): Flow<Intent> = callbackFlow {
    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                trySend(intent)
            }
        }
    }
    applicationContext.registerReceiver(broadcastReceiver, intentFilter)
    awaitClose { applicationContext.unregisterReceiver(broadcastReceiver) }
}
