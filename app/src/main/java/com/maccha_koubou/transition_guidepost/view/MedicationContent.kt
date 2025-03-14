package com.maccha_koubou.transition_guidepost.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.storage.medicationItemList
import com.maccha_koubou.transition_guidepost.ui.theme.AddButtonColors
import com.maccha_koubou.transition_guidepost.ui.theme.IconButtonColors
import com.maccha_koubou.transition_guidepost.ui.theme.LightPurple
import com.maccha_koubou.transition_guidepost.ui.theme.Typography
import com.maccha_koubou.transition_guidepost.ui.theme.White
import com.maccha_koubou.transition_guidepost.ui.theme.largeMainButtonColors
import com.maccha_koubou.transition_guidepost.view.component.AddDataIconButton
import com.maccha_koubou.transition_guidepost.view.component.ChartListSwitcher
import com.maccha_koubou.transition_guidepost.view.component.MainButton
import com.maccha_koubou.transition_guidepost.view.component.TrackListItem
import kotlinx.coroutines.launch

const val medicationCardTitle = "我的药物使用"

@Preview
@Composable
fun MedicationCard() {
    Box(modifier = Modifier.fillMaxSize()) {
        when (medicationItemList.isEmpty()) {
            true -> EmptyMedicationContent()
            else -> MedicationContent()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EmptyMedicationContent() {
    var showAddDataMenu by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column {

            // Title of the card
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp, 4.dp, 0.dp)
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = medicationCardTitle,
                    style = Typography.titleLarge
                )
            }

            // Add data button & Description
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp, 0.dp, 16.dp, 16.dp)
                    .border(1.dp, LightPurple, RoundedCornerShape(12.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Text(
                    text = "添加数据后，\n您可以获取用药提醒，\n或通过图表追踪用药数据",
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Center
                )
                MainButton(false, "添加数据", Icons.Filled.Add) { showAddDataMenu = true }
            }
        }
    }

    // Show Add Data Screen
    if (showAddDataMenu) {
        ModalBottomSheet(
            onDismissRequest = {
                showAddDataMenu = false
            },
            sheetState = sheetState
        ) {
            // Sheet content
            Button(onClick = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showAddDataMenu = false
                    }
                }
            }) {
                Text("Hide bottom sheet")
            }
        }
    }
}

@Preview
@Composable
fun MedicationContent() {
    var isMedicationList by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {

            // Title of the card and icon buttons
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp, 4.dp, 0.dp)
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                //Title
                Row(
                    Modifier.weight(1f)
                ) {
                    Text(
                        text = medicationCardTitle,
                        style = Typography.titleLarge
                    )
                }

                Row {
                    ChartListSwitcher(isMedicationList, {state -> isMedicationList = state})
                    AddDataIconButton {
                        /* Add data menu */
                    }
                }
            }

            when (isMedicationList) {
                true ->
                    Column(
                        Modifier
                            .fillMaxSize()
                    ) {
                        for (item in medicationItemList) {
                            if (item.isDataActive) {
                                TrackListItem(item)
                            }
                        }
                    }
                false ->
                    Box(
                        Modifier
                            .fillMaxSize()
                            .padding(16.dp, 0.dp, 16.dp, 12.dp)
                            .border(1.dp, LightPurple) // Only for testing
                    ) {
                    }
            }
        }
    }
}