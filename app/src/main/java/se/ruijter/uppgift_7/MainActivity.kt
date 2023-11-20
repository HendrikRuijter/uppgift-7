package se.ruijter.uppgift_7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import se.ruijter.uppgift_7.ui.theme.Uppgift7Theme

/**
 * Gör en layout i compose enligt nedan, ta screenshot och ladda upp.
 * Text överst "Start"
 * Text under med siffra
 * Två knappar bredvid varandra: "Plus" som ökar siffran och "Minus" som minskar siffran
 * Knapp under "Reset" som nollställer
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Uppgift7Theme {
                // StateApp("Start", modifier = Modifier.fillMaxSize())
                DecoupledStateApp(header = "Start", modifier = Modifier.fillMaxSize())
            }
        }
    }
}

/**
 * Stateless fun with callback
 * https://developer.android.com/jetpack/compose/state#state-hoisting
 */
@Composable
private fun DecoupledStateApp(header: String, modifier: Modifier = Modifier) {
//fun Decouple(header: String) {
    var counterState : Int by rememberSaveable { mutableStateOf(0) }
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        StateHeader(header, counterState)
        StateChange(counterState, onValueChange = { counterState = it })
    }
}

@Composable
private fun StateHeader(header: String, counterState: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = header,
            fontSize = 32.sp
        )
        Text(
            text = "$counterState",
            fontSize = 32.sp
        )
    }
}

@Composable
private fun StateChange(counterState: Int, onValueChange: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier.padding(16.dp)) {
                OutlinedButton(onClick = { onValueChange(counterState + 1) }) {
                    Text("Plus")
                }
                OutlinedButton(onClick = { onValueChange(counterState - 1) }) {
                    Text("Minus")
                }
            }
            Row {
                OutlinedButton(onClick = { onValueChange(0) }) {
                    Text("Reset")
                }
            }
        }
    }
}

/**
 * Stateful fun
 * https://developer.android.com/jetpack/compose/state#state-in-composables
 */
@Composable
private fun StateApp(header: String, modifier: Modifier = Modifier) {
    var counterState : Int by remember { mutableStateOf(0) }
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background
    ) {
        StateHeader(header, counterState)
        Row(verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.padding(16.dp)) {
                    OutlinedButton(onClick = { counterState += 1 }) {
                        Text("Plus")
                    }
                    OutlinedButton(onClick = { counterState -= 1 }) {
                        Text("Minus")
                    }
                }
                Row {
                    OutlinedButton(onClick = { counterState = 0 }) {
                        Text("Reset")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Uppgift7Theme {
            StateApp("Preview")
    }
}