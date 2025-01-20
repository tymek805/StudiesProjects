package com.tymek805.exercise_06

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tymek805.exercise_06.composables.BottomNavigationBar
import com.tymek805.exercise_06.composables.ListScreen
import com.tymek805.exercise_06.composables.MainScreen
import com.tymek805.exercise_06.ui.theme.AppTheme

data class TabBarItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            AppTheme {
                val swipeTab = TabBarItem("swipe", "Swipe", Icons.AutoMirrored.Default.ArrowBack)
                val mainTab = TabBarItem("main", "Main", Icons.Default.Home)
                val listTab = TabBarItem("list", "List", Icons.AutoMirrored.Default.ArrowForward)

                val tabBarItems = listOf(swipeTab, mainTab, listTab)
                val navController = rememberNavController()

                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold(bottomBar = { BottomNavigationBar(tabBarItems, navController) }) {
                        innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = mainTab.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(swipeTab.route) {
                                Text(swipeTab.label)
                            }
                            composable(mainTab.route) {
                                MainScreen()
                            }
                            composable(listTab.route) {
                                ListScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}