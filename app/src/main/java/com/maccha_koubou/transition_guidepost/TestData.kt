package com.maccha_koubou.transition_guidepost

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import com.maccha_koubou.transition_guidepost.model.Dosage
import com.maccha_koubou.transition_guidepost.model.MedicationData
import com.maccha_koubou.transition_guidepost.model.TestRecord
import com.maccha_koubou.transition_guidepost.storage.e2Data
import com.maccha_koubou.transition_guidepost.storage.medicationItemList
import com.maccha_koubou.transition_guidepost.storage.tData
import com.maccha_koubou.transition_guidepost.ui.theme.LightBlue
import com.maccha_koubou.transition_guidepost.ui.theme.LightPink
import java.time.LocalDateTime

fun addTestData() {
    medicationItemList.apply {
        add(MedicationData("补佳乐", LightPink, Icons.Filled.Refresh, Dosage(1f, "mg", 1, 3), true, false, null))
        add(MedicationData("色普龙", LightBlue, Icons.Filled.Info, Dosage(12.5f, "mg", 2,1), true, false, null))
    }

    e2Data.dataList.apply {
        add(TestRecord(92.98f, LocalDateTime.of(2023, 3, 15, 0, 0, 0)))
        add(TestRecord(106.24f, LocalDateTime.of(2024, 3, 27, 0, 0, 0)))
        add(TestRecord(226.54f, LocalDateTime.of(2024, 5, 7, 0, 0, 0)))
        add(TestRecord(306.18f, LocalDateTime.of(2024, 5, 22, 0, 0, 0)))
        add(TestRecord(1006.02f, LocalDateTime.of(2024, 7, 15, 0, 0, 0)))
    }

    tData.dataList.apply {
        add(TestRecord(15.02f, LocalDateTime.of(2023, 3, 15, 0, 0, 0)))
        add(TestRecord(2.08f, LocalDateTime.of(2024, 3, 27, 0, 0, 0)))
        add(TestRecord(1.05f, LocalDateTime.of(2024, 5, 22, 0, 0, 0)))
        add(TestRecord(0.89f, LocalDateTime.of(2024, 7, 15, 0, 0, 0)))
    }
}