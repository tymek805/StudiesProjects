package com.tymek805.exercise_06.composables

import android.widget.RatingBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tymek805.exercise_06.R
import com.tymek805.exercise_06.database.MyItem
import com.tymek805.exercise_06.viewmodels.ListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController, viewModel: ListViewModel) {
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
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val myItem: MyItem = viewModel.selectedItem.value!!
            DetailRow(label = "Cel podróży:", value = myItem.destination ?: "Unknown")
            DetailRow(label = "Czas połączenia:", value = myItem.time.toString() + " min")
            DetailRow(label = "Typ transportu:", value = myItem.transportType?.name ?: "None")

            Text("Ocena połączenia:")
            RatingBar(
                rating = myItem.rating,
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Zagraniczne:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 8.dp))
                Checkbox(checked = myItem.checked, onCheckedChange = {})
            }

            ItemRow(item = myItem, viewModel = viewModel, navController = navController)
            Spacer(modifier = Modifier.weight(1f))
            BottomDetailsButtons(navController)
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
//        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = value,
            fontSize = 18.sp,
//            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun RatingBar(
    rating: Float,
    onRatingChanged: (Float) -> Unit = {},
    stars: Int = 5
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..stars) {
            Icon(
                painter = painterResource(if (i <= rating) R.drawable.ic_star else R.drawable.ic_star_border),
                contentDescription = "Star $i",
                modifier = Modifier
                    .size(56.dp)
                    .clickable {onRatingChanged(i.toFloat()) },
                tint = Color(0xFFEFBF04)
            )
        }
    }
}

@Composable
fun BottomDetailsButtons(navController: NavController) {
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
            Text(text = "Back")
        }

        Button(
            onClick = { navController.navigate("edit_screen") },
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
        ) {
            Text(text = "Modify")
        }
    }
}