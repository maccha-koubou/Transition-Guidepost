package com.maccha_koubou.transition_guidepost.model

// Classes of record series
open class RecordedData<T>(
    open val name: String,
    open var isDataActive: Boolean,
    open var isAlarmActive: Boolean,
    open var alarmCycle: AlarmCycle?
){
    open var dataList = mutableListOf<T>()
}

data class TestData(
    override val name: String,
    val unit: Map<String, Float>, // Name of the unit & Ratio of all types to the first type
    val recommendationValue: Pair<Float, Float>?, // Lower value & Higher value
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<TestRecord>(name, isDataActive, isAlarmActive, alarmCycle) {
}

data class MedicationData(
    override val name: String,
    val defaultDosage: Dosage,
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<MedicationRecord>(name, isDataActive, isAlarmActive, alarmCycle) {
    var currentDosage = defaultDosage
}

data class TimePointData(
    override val name: String,
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<TimePointRecord>(name, isDataActive, isAlarmActive, alarmCycle) {

}

data class BreastData(
    override val name: String,
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<BreastRecord>(name, isDataActive, isAlarmActive, alarmCycle) {

}

data class CycleData(
    override val name: String,
    override var isDataActive: Boolean,
    override var isAlarmActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<CycleRecord>(name, isDataActive, isAlarmActive, alarmCycle) {

}