package com.tymek805.exercise_06.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.tymek805.exercise_06.database.TransportType
import com.tymek805.exercise_06.viewmodels.ListViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemRow(item: MyItem, viewModel: ListViewModel, navController: NavController, clickable: Boolean = true) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .combinedClickable(
                onClick = {
                    if (clickable) {
                        viewModel.selectItem(item)
                        navController.navigate("details_screen")
                    }
                },
                onLongClick = { if (clickable) viewModel.deleteItem(item) }
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Transport Icon
        Icon(
            painter = painterResource(
                when (item.transportType) {
                    TransportType.TRAIN -> R.drawable.ic_train
                    TransportType.BUS -> R.drawable.ic_bus
                    TransportType.AIRPLANE -> R.drawable.ic_airplane
                    null -> TODO()
                }
            ),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Text Content
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.destination ?: "",
                fontWeight = FontWeight.Bold,
                color = Color.Red
            )
            Text(
                text = item.subText ?: "",
                fontSize = 12.sp,
            )
        }

        // Checkbox
        Checkbox(
            checked = item.checked,
            onCheckedChange = {
                if (clickable) viewModel.toggleChecked(item) },
        )
    }
}
