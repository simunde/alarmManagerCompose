package com.msid.alarmmanagercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.msid.alarmmanagercompose.ui.theme.AlarmManagerComposeTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val scheduler = AlarmAndroidScheduler(this)
        var alarmItem: AlarmItem?=null
        enableEdgeToEdge()
        setContent {
            AlarmManagerComposeTheme {
               var secondsText by remember {
                   mutableStateOf("")
               }
                var message by remember {
                    mutableStateOf("")
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {

                    OutlinedTextField(
                        value = secondsText,
                        onValueChange = { secondsText = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(text = "Triggered alarm in seconds")
                        }
                    )

                    OutlinedTextField(value = message,
                        onValueChange = { message = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(text = message)
                        }
                    )

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        alarmItem = AlarmItem(
                            time = LocalDateTime.now().plusSeconds(
                                secondsText.toLong()),
                            message = message
                        )
                        alarmItem?.let(scheduler::schedule)
                        secondsText = ""
                        message = ""
                    }) {
                        Text(text = "Schedule")
                    }

                    Button(onClick = {

                        alarmItem?.let ( scheduler::cancel )
                    }) {
                        Text(text = "Cancel")
                    }

                }
            }
        }
    }
}

