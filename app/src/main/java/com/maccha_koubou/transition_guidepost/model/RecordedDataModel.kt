package com.maccha_koubou.transition_guidepost.model

import android.graphics.drawable.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDateTime

// Classes of record series
interface RecordedData<out T: DataRecord>{
    val name: String
    var color: Color
    var icon: ImageVector
    var isDataActive: Boolean
    var isAlarmActive: Boolean
    var alarmCycle: AlarmCycle?
    var dataList: MutableList<@UnsafeVariance T>
}

interface BodyData

data class TestData(
    override val name: String,
    override var color: Color,
    override var icon: ImageVector,
    val unit: Map<String, Float>, // Name of the unit & Ratio of all types to the first type
    val recommendationValue: Pair<Float, Float>?, // Lower value & Higher value
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<TestRecord>, BodyData {
    override var dataList = mutableListOf<TestRecord>()
}

data class MedicationData(
    override val name: String,
    override var color: Color,
    override var icon: ImageVector,
    val defaultDosage: Dosage,
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<MedicationRecord> {
    var currentDosage = defaultDosage
    override var dataList = mutableListOf<MedicationRecord>()

    fun addCommonRecord() {
        val newRecord = MedicationRecord(currentDosage.dosage, LocalDateTime.now())
        dataList.add(newRecord)
    }
}

data class TimePointData(
    override val name: String,
    override var color: Color,
    override var icon: ImageVector,
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<TimePointRecord>, BodyData {
    override var dataList = mutableListOf<TimePointRecord>()
}

data class BreastData(
    override val name: String,
    override var color: Color,
    override var icon: ImageVector,
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<BreastRecord>, BodyData {
    override var dataList = mutableListOf<BreastRecord>()
}

data class CycleData(
    override val name: String,
    override var color: Color,
    override var icon: ImageVector,
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<CycleRecord>, BodyData {
    override var dataList = mutableListOf<CycleRecord>()
}