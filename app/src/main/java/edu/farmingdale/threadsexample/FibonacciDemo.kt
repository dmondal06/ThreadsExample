package edu.farmingdale.threadsexample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.text.NumberFormat
import java.util.Locale
@Composable
fun FibonacciDemoNoBgThrd() {
    var answer by remember { mutableStateOf("") }
    var textInput by remember { mutableStateOf("40") }
    val coroutineScope = rememberCoroutineScope()

    Column {
        Spacer(modifier = Modifier.height(20.dp))

        Row {
            TextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Number?") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                val num = textInput.toLongOrNull() ?: 0
                coroutineScope.launch(Dispatchers.Default) {
                    val fibNumber = fibonacci(num)
                    answer = NumberFormat.getNumberInstance(Locale.US).format(fibNumber)
                }
            }) {
                Text("Calculate")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Result: $answer")

        Spacer(modifier = Modifier.height(40.dp))
    }
}
fun fibonacci(n: Long): Long {
    return if (n <= 1) n else fibonacci(n - 1) + fibonacci(n - 2)
}
