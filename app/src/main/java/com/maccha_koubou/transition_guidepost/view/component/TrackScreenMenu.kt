package com.maccha_koubou.transition_guidepost.view.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.model.MedicationData
import com.maccha_koubou.transition_guidepost.ui.theme.Gray
import com.maccha_koubou.transition_guidepost.ui.theme.White
import kotlinx.coroutines.launch

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