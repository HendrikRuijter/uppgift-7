package se.ruijter.uppgift_7

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import se.ruijter.uppgift_7.ui.theme.Uppgift7Theme

/**
 * Gör en layout i compose enligt nedan, ta screenshot och ladda upp.
 * Text överst "Start"
 * Text under med siffra
 * Två knappar bredvid varandra: "Plus" som ökar siffran och "Minus" som minskar siffran
 * Knapp under "Reset" som nollställer
 * https://github.com/HendrikRuijter/uppgift-7/tree/main
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val title = "Start"

        super.onCreate(savedInstanceState)
        setContent {
            Uppgift7Theme {
                // StateApp(header = title, modifier = Modifier.fillMaxSize())
                DecoupledStateApp(header = title, modifier = Modifier.fillMaxSize())
            }
        }
    }
}

/**
 * Stateful fun with callback
 * https://developer.android.com/jetpack/compose/state#state-hoisting
 */
@Suppress("SameParameterValue")
@Composable
private fun DecoupledStateApp(header: String, modifier: Modifier = Modifier) {
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
    @Composable
    fun TextElement(description: String) {
        Text(
            text = description,
            style = MaterialTheme.typography.headlineMedium,
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextElement(description = header)
        TextElement(description = "$counterState")
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
@Suppress("SameParameterValue")
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

@Preview(
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Preview(
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
    name = "Light")
@Composable
fun DefaultPreview() {
    Uppgift7Theme {
        StateApp("Preview")
        // DecoupledStateApp(header = "Start")
    }
}