package com.maccha_koubou.transition_guidepost.model

// Classes of record series
interface RecordedData<T>{
    val name: String
    var isDataActive: Boolean
    var isAlarmActive: Boolean
    var alarmCycle: AlarmCycle?
    var dataList: MutableList<T>
}

data class TestData(
    override val name: String,
    val unit: Map<String, Float>, // Name of the unit & Ratio of all types to the first type
    val recommendationValue: Pair<Float, Float>?, // Lower value & Higher value
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<TestRecord> {
    override var dataList = mutableListOf<TestRecord>()
}

data class MedicationData(
    override val name: String,
    val defaultDosage: Dosage,
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<MedicationRecord> {
    var currentDosage = defaultDosage
    override var dataList = mutableListOf<MedicationRecord>()
}

data class TimePointData(
    override val name: String,
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<TimePointRecord> {
    override var dataList = mutableListOf<TimePointRecord>()
}

data class BreastData(
    override val name: String,
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<BreastRecord> {
    override var dataList = mutableListOf<BreastRecord>()
}

data class CycleData(
    override val name: String,
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<CycleRecord> {
    override var dataList = mutableListOf<CycleRecord>()
}