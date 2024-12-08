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
    val displayedUnit: String
    var isDataActive: Boolean
    var isAlarmActive: Boolean
    var alarmCycle: AlarmCycle?
    var dataList: MutableList<@UnsafeVariance T>

    fun copy(): RecordedData<T>
}

interface BodyData

data class TestData(
    override val name: String,
    override var color: Color,
    override var icon: ImageVector,
    val unit: Map<String, Float>, // Name of the unit & Ratio of all types to the first type
    val recommendationValue: ClosedFloatingPointRange<Float>?, // Lower value & Higher value
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<TestRecord>, BodyData {
    override var dataList = mutableListOf<TestRecord>()
    var unitIndex = 0
    override val displayedUnit get()= unit.keys.toList()[unitIndex]

    constructor(
        thisData: TestData,
        dataList: MutableList<TestRecord> = thisData.dataList,
        unitIndex: Int = thisData.unitIndex
    ): this(
        thisData.name,
        thisData.color,
        thisData.icon,
        thisData.unit,
        thisData.recommendationValue,
        thisData.isDataActive,
        thisData.isAlarmActive,
        thisData.alarmCycle
    ) {
        this.dataList = dataList
        this.unitIndex = unitIndex
    }

    override fun copy(): TestData {
        return TestData(this)
    }
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
    override val displayedUnit get() = "${currentDosage.dosage}${currentDosage.unitWithoutInterval}"

    fun addCommonRecord() {
        val newRecord = MedicationRecord(currentDosage.dosage, LocalDateTime.now())
        dataList.add(newRecord)
    }

    constructor(
        thisData: MedicationData,
        currentDosage: Dosage = thisData.currentDosage,
        dataList: MutableList<MedicationRecord> = thisData.dataList,
    ): this(
        thisData.name,
        thisData.color,
        thisData.icon,
        thisData.defaultDosage,
        thisData.isDataActive,
        thisData.isAlarmActive,
        thisData.alarmCycle
    ) {
        this.dataList = dataList
    }

    override fun copy(): MedicationData {
        return MedicationData(this)
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
    override val displayedUnit = ""

    constructor(
        thisData: TimePointData,
        dataList: MutableList<TimePointRecord> = thisData.dataList
    ): this(
        thisData.name,
        thisData.color,
        thisData.icon,
        thisData.isDataActive,
        thisData.isAlarmActive,
        thisData.alarmCycle
    ) {
        this.dataList = dataList
    }

    override fun copy(): TimePointData {
        return TimePointData(this)
    }
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
    override val displayedUnit = ""

    constructor(
        thisData: BreastData,
        dataList: MutableList<BreastRecord> = thisData.dataList
    ): this(
        thisData.name,
        thisData.color,
        thisData.icon,
        thisData.isDataActive,
        thisData.isAlarmActive,
        thisData.alarmCycle
    ) {
        this.dataList = dataList
    }

    override fun copy(): BreastData {
        return BreastData(this)
    }
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
    override val displayedUnit = ""

    constructor(
        thisData: CycleData,
        dataList: MutableList<CycleRecord> = thisData.dataList
    ): this(
        thisData.name,
        thisData.color,
        thisData.icon,
        thisData.isDataActive,
        thisData.isAlarmActive,
        thisData.alarmCycle
    ) {
        this.dataList = dataList
    }

    override fun copy(): CycleData {
        return CycleData(this)
    }
}