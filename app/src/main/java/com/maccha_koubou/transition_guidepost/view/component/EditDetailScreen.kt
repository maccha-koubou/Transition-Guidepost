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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.maccha_koubou.transition_guidepost.ui.theme.Gray
import com.maccha_koubou.transition_guidepost.ui.theme.Purple
import com.maccha_koubou.transition_guidepost.ui.theme.White
import kotlinx.coroutines.launch

@Composable
fun EditDateScreen(onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        // Remove the dim of the Dialog
        (LocalView.current.parent as DialogWindowProvider)?.window?.setDimAmount(0f)
        // Keep the phone's status bar visible
        (LocalView.current.parent as DialogWindowProvider).window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(
                    // Avoid showing content under the navigation bar
                    bottom = 0.dp
                    /*with(LocalDensity.current) {
                        WindowInsets.navigationBars.getBottom(this).toDp()
                    }*/
                )
        ) {
            Column(
                modifier = Modifier.fillMaxSize().background(Purple)
            ) {
                Text("${
                    with(LocalDensity.current) {
                        WindowInsets.navigationBars.getBottom(this).toDp()
                    }
                }")
                Button(onClick = onDismiss) {
                    Text("关闭")
                }
            }
        }
    }
}