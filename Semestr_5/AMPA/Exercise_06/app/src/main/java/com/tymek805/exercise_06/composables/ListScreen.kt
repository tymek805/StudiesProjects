package com.tymek805.exercise_06.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ListScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .systemBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("main") }
            ) {
                Icon(Icons.Default.Add, "Add")
            }
        }
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "My list:", fontSize = 34.sp, modifier = Modifier.padding(16.dp))
        }
    }
}
//Scaffold(
//floatingActionButton = {
//    FloatingActionButton(
//        onClick = { navController.navigate("main") }
//    ) {
//        Icon(Icons.Filled.Add, "")
//    }
//},
//floatingActionButtonPosition = FabPosition.End
//) { innerPadding ->
//    Column (
//        modifier = Modifier
//            .padding(innerPadding)
//            .fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "My list:", fontSize = 34.sp, modifier = Modifier.padding(16.dp))
//    }
//}
//@Composable
//fun ListScreen(viewModel: ListViewModel = viewModel(), onAdd: () -> Unit, onItemClick: (MyItem) -> Unit) {
//    val items = viewModel.getAllItems() ?: listOf()
//
//    Column {
//        Text(text = "List", style = MaterialTheme.typography.h4, modifier = Modifier.padding(16.dp))
//        LazyColumn {
//            items(items) { item ->
//                Row(modifier = Modifier.fillMaxWidth().clickable { onItemClick(item) }.padding(16.dp)) {
//                    Image(painter = getTransportIcon(item.transportType), contentDescription = "Transport")
//                    Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
//                        Text(text = item.destination, style = MaterialTheme.typography.h6)
//                        Text(text = item.subText)
//                    }
//                    Checkbox(checked = item.checked, onCheckedChange = null)
//                }
//            }
//        }
//        FloatingActionButton(onClick = onAdd, modifier = Modifier.padding(16.dp)) {
//            Icon(painter = painterResource(R.drawable.ic_add), contentDescription = "Add")
//        }
//    }
//}