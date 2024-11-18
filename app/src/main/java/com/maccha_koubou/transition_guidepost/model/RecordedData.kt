package com.maccha_koubou.transition_guidepost.model

open class RecordedData(
    open val name: String,
    open var data: Float,
    open val unit: Map<String, Float>, // Name of the unit & Ratio of all types to the first type
    open val recommendationValue: Pair<Int, Int>, // Lower value & Higher value
    open var isDataActivity: Boolean,
    open var isAlarmActivity: Boolean,
    open var alarmCycle: AlarmCycle,
) {

}

data class TestData(
    override val name: String,
    override var data: Float,
    override val unit: Map<String, Float>,
    override val recommendationValue: Pair<Int, Int>,
    override var isDataActivity: Boolean,
    override var isAlarmActivity: Boolean,
    override var alarmCycle: AlarmCycle,
): RecordedData(name, data, unit, recommendationValue, isDataActivity, isAlarmActivity, alarmCycle) {

}

data class MedicationData(
    override val name: String,
    override var data: Float,
    override val unit: Map<String, Float>,
    override val recommendationValue: Pair<Int, Int>,
    override var isDataActivity: Boolean,
    override var isAlarmActivity: Boolean,
    override var alarmCycle: AlarmCycle,
): RecordedData(name, data, unit, recommendationValue, isDataActivity, isAlarmActivity, alarmCycle) {

}

data class TimePointData(
    override val name: String,
    override var data: Float,
    override val unit: Map<String, Float>,
    override val recommendationValue: Pair<Int, Int>,
    override var isDataActivity: Boolean,
    override var isAlarmActivity: Boolean,
    override var alarmCycle: AlarmCycle,
): RecordedData(name, data, unit, recommendationValue, isDataActivity, isAlarmActivity, alarmCycle) {

}

data class BreastData(
    override val name: String,
    override var data: Float,
    override val unit: Map<String, Float>,
    override val recommendationValue: Pair<Int, Int>,
    override var isDataActivity: Boolean,
    override var isAlarmActivity: Boolean,
    override var alarmCycle: AlarmCycle,
): RecordedData(name, data, unit, recommendationValue, isDataActivity, isAlarmActivity, alarmCycle) {

}

data class CycleData(
    override val name: String,
    override var data: Float,
    override val unit: Map<String, Float>,
    override val recommendationValue: Pair<Int, Int>,
    override var isDataActivity: Boolean,
    override var isAlarmActivity: Boolean,
    override var alarmCycle: AlarmCycle,
): RecordedData(name, data, unit, recommendationValue, isDataActivity, isAlarmActivity, alarmCycle) {

}