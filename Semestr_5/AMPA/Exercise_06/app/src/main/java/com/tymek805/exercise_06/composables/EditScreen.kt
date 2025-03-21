package com.tymek805.exercise_06.composables

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.tymek805.exercise_06.database.MyItem
import com.tymek805.exercise_06.database.TransportType
import com.tymek805.exercise_06.viewmodels.ListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(navController: NavController, viewModel: ListViewModel) {
    val item: MyItem = viewModel.selectedItem.value!!

    val context = LocalContext.current

    var currentRating by remember { mutableFloatStateOf(item.rating) }
    var isChecked by remember { mutableStateOf(item.checked) }
    var destination by remember { mutableStateOf(item.destination ?: "") }
    var time by remember { mutableStateOf(item.time.toString()) }
    var selectedTransportType by remember { mutableStateOf(item.transportType) }
    val transportTypes = TransportType.entries
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Details Screen") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = destination,
                onValueChange = { destination = it },
                label = { Text("Miasto") },
                modifier = Modifier.fillMaxWidth()
            )

            // Editable Time Input
            OutlinedTextField(
                value = time,
                onValueChange = { if (it.all { char -> char.isDigit() }) time = it },
                label = { Text("Czas podróży (min)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Transport Type Dropdown
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it },
                Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedTransportType?.name ?: "None",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Typ transportu") },
                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                        }
                    },
                    modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    transportTypes.forEach { type ->
                        DropdownMenuItem(text = { Text(type.name) }, onClick = {
                            selectedTransportType = type
                            expanded = false
                        })
                    }
                }
            }

            Text("Ocena połączenia:")
            RatingBar(
                rating = currentRating,
                { currentRating = it }
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Zagraniczne:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp))
                Checkbox(checked = isChecked, onCheckedChange = { isChecked = it })
            }

            Spacer(modifier = Modifier.weight(1f))
            BottomEditButtons(navController
            ) {
                Toast.makeText(context, "Item saved", Toast.LENGTH_SHORT).show()
                val updatedItem = item.copy(
                    destination = destination,
                    subText = "Czas połączenia: $time min",
                    time = time.toIntOrNull() ?: item.time,
                    transportType = selectedTransportType,
                    rating = currentRating,
                    checked = isChecked
                )
                if (item.id == 0)
                    viewModel.addItem(updatedItem)
                else
                    viewModel.updateItem(updatedItem)
                navController.navigate("list")
            }
        }
    }
}

@Composable
fun BottomEditButtons(navController: NavController, saveAction: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
        ) {
            Text(text = "Cancel")
        }

        Button(
            onClick = { saveAction() },
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
        ) {
            Text(text = "Save")
        }
    }
}
