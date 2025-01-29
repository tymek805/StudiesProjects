package com.tymek805.exercise_06.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.tymek805.exercise_06.database.MyItem
import com.tymek805.exercise_06.viewmodels.ListViewModel

@Composable
fun ListScreen(navController: NavController, listViewModel: ListViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val items by listViewModel.getAllItems().observeAsState(emptyList())
    println(items)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .systemBarsPadding(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val item = MyItem()
                    listViewModel.selectItem(item)
                    navController.navigate("edit_screen")
                }
            ) {
                Icon(Icons.Default.Add, "Add")
            }
        },
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "My list:", fontSize = 34.sp, modifier = Modifier.padding(16.dp))
            LazyColumn {
                items(items) { item ->
                    ItemRow(item, listViewModel, navController)
                }
            }
        }
    }
}
