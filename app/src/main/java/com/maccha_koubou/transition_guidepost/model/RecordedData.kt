package com.maccha_koubou.transition_guidepost.model

import java.time.LocalTime

// Class of a dosage data
// unitWithoutInterval: The unit of each dose (NOT includes the interval)
// interval: Days of interval
class Dosage(dosage: Float, unitWithoutInterval: String, interval: Int)

// Classes of single records
open class Record()

class CommonRecord(
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

open class RecordedData(
    open val name: String,
    open var isDataActive: Boolean,
    open var alarmCycle: AlarmCycle?,
){

}

data class TestData(
    override val name: String,
    val unit: Map<String, Float>, // Name of the unit & Ratio of all types to the first type
    val recommendationValue: Pair<Float, Float>?, // Lower value & Higher value
    override var isDataActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData(name, isDataActive, alarmCycle) {

}

data class MedicationData(
    override val name: String,
    val defaultDosage: Dosage,
    override var isDataActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData(name, isDataActive, alarmCycle) {
    var currentDosage = defaultDosage
}

data class TimePointData(
    override val name: String,
    override var isDataActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData(name, isDataActive, alarmCycle) {

}

data class BreastData(
    override val name: String,
    override var isDataActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData(name, isDataActive, alarmCycle) {

}

data class CycleData(
    override val name: String,
    override var isDataActive: Boolean,
    override var alarmCycle: AlarmCycle?,
): RecordedData(name, isDataActive, alarmCycle) {

}