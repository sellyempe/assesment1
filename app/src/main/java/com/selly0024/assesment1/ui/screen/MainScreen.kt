package com.selly0024.assesment1.ui.screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.selly0024.assesment1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    var age by remember { mutableStateOf("") }
    var fiberIntake by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("male") }
    var result by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("about")
                    }) {
                        Icon(Icons.Default.Info, contentDescription = "About")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fiber_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text(stringResource(R.string.input_age)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = fiberIntake,
                onValueChange = { fiberIntake = it },
                label = { Text(stringResource(R.string.input_fiber)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(stringResource(R.string.gender))
            Row {
                RadioButton(
                    selected = gender == "male",
                    onClick = { gender = "male" }
                )
                Text(stringResource(R.string.male), modifier = Modifier.padding(end = 16.dp))

                RadioButton(
                    selected = gender == "female",
                    onClick = { gender = "female" }
                )
                Text(stringResource(R.string.female))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                val ageInt = age.toIntOrNull()
                val fiber = fiberIntake.toIntOrNull()

                if (ageInt == null || fiber == null || ageInt <= 0 || fiber <= 0) {
                    Toast.makeText(context, context.getString(R.string.invalid_input), Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val recommendation = when {
                    gender == "male" && ageInt in 19..50 -> 38
                    gender == "male" -> 30
                    gender == "female" && ageInt in 19..50 -> 25
                    else -> 21
                }

                val status = if (fiber >= recommendation) {
                    context.getString(R.string.ideal)
                } else {
                    context.getString(R.string.low)
                }

                result = context.getString(R.string.fiber_result, recommendation, status)
            }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.calculate))
            }

            Spacer(modifier = Modifier.height(12.dp))

            result?.let {
                Text(it, color = Color(0xFF00695C))
                Spacer(modifier = Modifier.height(12.dp))

                Button(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, it)
                    }
                    context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_via)))
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(R.string.share))
                }
            }
        }
    }
}