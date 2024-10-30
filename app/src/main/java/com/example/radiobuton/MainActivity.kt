package com.example.radiobuton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.radiobuton.ui.theme.RadiobutonTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RadiobutonTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CustomAppScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CustomAppScreen(modifier: Modifier = Modifier) {
    var buttonMessage by remember { mutableStateOf("") }
    var isProgressVisible by remember { mutableStateOf(false) }
    var isTextVisible by remember { mutableStateOf(false) }
    var displayMessage by remember { mutableStateOf("") }
    var isSwitchOn by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Opción 1") }
    var imageIndex by remember { mutableStateOf(0) }

    val images = listOf(R.drawable.imagen1, R.drawable.imagen2, R.drawable.imagen3)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "Icono estrella",
            tint = Color.Yellow,
            modifier = Modifier
                .size(48.dp)
                .align(Alignment.TopStart)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    buttonMessage = "Botón presionado"
                    isProgressVisible = true
                    imageIndex = (imageIndex + 1) % images.size
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(5000L)
                        isProgressVisible = false
                        buttonMessage = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Presionar", color = Color.White)
            }

            if (buttonMessage.isNotEmpty()) {
                Text(
                    text = buttonMessage,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            if (isProgressVisible) {
                CircularProgressIndicator(color = Color.Yellow)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isTextVisible,
                    onCheckedChange = { isTextVisible = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.Yellow,
                        uncheckedColor = Color.Gray
                    )
                )
                Text("Activar", color = Color.White)
            }

            if (isTextVisible) {
                Text(
                    text = displayMessage,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = isSwitchOn,
                    onCheckedChange = { isSwitchOn = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Yellow,
                        uncheckedThumbColor = Color.Gray
                    )
                )
                Text("Mostrar opciones", color = Color.White)
            }

            if (isSwitchOn) {
                Column(horizontalAlignment = Alignment.Start) {
                    RadioButtonGroup(
                        options = listOf("Opción 1", "Opción 2", "Opción 3"),
                        selectedOption = selectedOption,
                        onOptionSelected = {
                            selectedOption = it
                            displayMessage = "Seleccionaste: $it"
                        }
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.White)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = images[imageIndex]),
                    contentDescription = "Imagen cambiante"
                )
            }
        }
    }
}

@Composable
fun RadioButtonGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    options.forEach { option ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = option == selectedOption,
                onClick = { onOptionSelected(option) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.Yellow,
                    unselectedColor = Color.Gray
                )
            )
            Text(option, color = Color.White, modifier = Modifier.padding(start = 8.dp))
        }
    }
}



