package com.maccha_koubou.transition_guidepost.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.model.MedicationData
import com.maccha_koubou.transition_guidepost.model.TestRecord
import com.maccha_koubou.transition_guidepost.storage.chartSetting
import com.maccha_koubou.transition_guidepost.storage.e2Data
import com.maccha_koubou.transition_guidepost.storage.tData
import com.maccha_koubou.transition_guidepost.ui.theme.Gray
import com.maccha_koubou.transition_guidepost.ui.theme.White
import com.maccha_koubou.transition_guidepost.view.localNavController
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegretMedicationRecordMenu(item: MedicationData, state: (Boolean) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            state(false)
        },
        sheetState = sheetState,
        containerColor = White,
        contentColor = Gray
    ) {
        Column(
            modifier = Modifier.padding(16.dp, 0.dp)
        ) {
            MenuItem("撤销本次用药记录", Icons.Filled.ArrowBack) {
                item.dataList.let {
                    if (it.isNotEmpty()) {
                        it.removeAt(it.size - 1)
                    }
                }
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        state(false)
                    }
                }
            }
            SecondaryButton(true, "取消") {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        state(false)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHormoneDataMenu(state: (Boolean) -> Unit) {
    var showInputHormoneScreen by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            state(false)
        },
        sheetState = sheetState,
        containerColor = White,
        contentColor = Gray
    ) {
        Column(
            modifier = Modifier.padding(16.dp, 0.dp)
        ) {
            MenuItem("手动输入数据", Icons.Filled.Create) {
                showInputHormoneScreen = true

                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        state(false)
                    }
                }
            }
            SecondaryButton(true, "取消") {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        state(false)
                    }
                }
            }
        }
    }

    if (showInputHormoneScreen) {
        val navController = localNavController.current
        navController.navigate("input_hormone_screen")
    }
}