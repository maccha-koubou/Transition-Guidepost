package com.maccha_koubou.transition_guidepost.model

import java.time.LocalDateTime

/**
 * Class of a dosage data
 * unitWithoutInterval: The unit of each dose (NOT includes the interval)
 * intervalDays: Days of interval
 * dosagePerDay: How many doses each day
 * averageDosagePerDay: The average dosage in a day, = dosesPerDay * dosage / intervalDays
*/
class Dosage(var dosage: Float, var unitWithoutInterval: String, var intervalDays: Int, var dosesPerDay: Int) {
    val averageDosagePerDay = dosesPerDay * dosage / intervalDays
}

/**
 * Classes of single records
 */
interface DataRecord

class TestRecord(
    val data: Float,
    val time: LocalDateTime,
): DataRecord

class MedicationRecord(
    val data: Float,
    val time: LocalDateTime,
): DataRecord

class CycleRecord(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
): DataRecord

class TimePointRecord(
    val time: LocalDateTime
): DataRecord

class BreastRecord(
    val upperBust: Float,
    val lowerBust: Float,
    val time: LocalDateTime,
): DataRecord