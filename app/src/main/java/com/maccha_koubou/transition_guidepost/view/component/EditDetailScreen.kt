package com.maccha_koubou.transition_guidepost.view.component

import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.maccha_koubou.transition_guidepost.model.MedicationData
import com.maccha_koubou.transition_guidepost.storage.medicationItemList
import com.maccha_koubou.transition_guidepost.storage.user
import com.maccha_koubou.transition_guidepost.ui.theme.Gray
import com.maccha_koubou.transition_guidepost.ui.theme.Purple
import com.maccha_koubou.transition_guidepost.ui.theme.Typography
import com.maccha_koubou.transition_guidepost.ui.theme.White
import com.maccha_koubou.transition_guidepost.ui.theme.titleBarColors
import com.maccha_koubou.transition_guidepost.view.localNavController
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EditDateScreen() {
    val navController = localNavController.current

    // The Title Bar will be pinned at the top of the screen even scrolled
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var showDatePicker by remember { mutableStateOf(false) }

    // The millis of today, used for banning user from selecting a future date
    val todayMillis = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

    Column() {
        TopAppBar(
            title = { Text(text = "记录激素肯定治疗天数", style = Typography.titleLarge) },
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate("track_screen")
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "返回"
                    )
                }
            },
            colors = titleBarColors,
            scrollBehavior = scrollBehavior
        )
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp, 0.dp, 16.dp, 16.dp)) {
            DetailItem(true, false, "治疗开始日期") {
                InputButton(
                    true,
                    it,
                    user.startDate?.toString() ?: "请填写治疗开始日期"
                ) {
                    showDatePicker = true
                }
            }
        }
    }

    if (showDatePicker) {
        startDatePicker { state -> showDatePicker = state }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun startDatePicker(state: (Boolean) -> Unit) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = { state(false) },
        confirmButton = {
            MainButton(false, "确定") {
                datePickerState.selectedDateMillis?.let {
                    // Transfer the datemillis into LocalDate
                        time -> user.startDate = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()
                } ?: { state(false) }
                state(false)
            }
        },
        dismissButton = {
            SecondaryButton(false, "取消") { state(false) }
        }
    ) {}
}