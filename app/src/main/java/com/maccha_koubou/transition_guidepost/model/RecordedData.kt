package com.maccha_koubou.transition_guidepost.model

import java.time.LocalTime

// Class of a dosage data
// unitWithoutInterval: The unit of each dose (NOT includes the interval)
// interval: Days of interval
class Dosage(dosage: Float, unitWithoutInterval: String, interval: Int)

// Classes of single records
open class Record()

class TestRecord(
    val data: Float,
    val time: LocalTime,
): Record()

class MedicationRecord(
    val data: Float,
    var dosage: Dosage,
    val time: LocalTime,
): Record()

class CycleRecord(
    val startTime: LocalTime,
    val endTime: LocalTime?,
): Record()

class TimePointRecord(
    val time: LocalTime
): Record()

class BreastRecord(
    val upperBust: Float,
    val lowerBust: Float,
    val time: LocalTime,
): Record()

// Classes of record series
open class RecordedData<T>(
    open val name: String,
    open var isDataActive: Boolean,
    open var alarmCycle: AlarmCycle?
){
    open var dataList = mutableListOf<T>()
}

data class TestData(
    override val name: String,
    val unit: Map<String, Float>, // Name of the unit & Ratio of all types to the first type
    val recommendationValue: Pair<Float, Float>?, // Lower value & Higher value
    override var isDataActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<TestRecord>(name, isDataActive, alarmCycle) {
}

data class MedicationData(
    override val name: String,
    val defaultDosage: Dosage,
    override var isDataActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<MedicationRecord>(name, isDataActive, alarmCycle) {
    var currentDosage = defaultDosage
}

data class TimePointData(
    override val name: String,
    override var isDataActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<TimePointRecord>(name, isDataActive, alarmCycle) {

}

data class BreastData(
    override val name: String,
    override var isDataActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<BreastRecord>(name, isDataActive, alarmCycle) {

}

data class CycleData(
    override val name: String,
    override var isDataActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData<CycleRecord>(name, isDataActive, alarmCycle) {

}