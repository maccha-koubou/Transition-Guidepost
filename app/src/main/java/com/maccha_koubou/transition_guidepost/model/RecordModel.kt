package com.maccha_koubou.transition_guidepost.model

import java.time.LocalDateTime

// Class of a dosage data
// unitWithoutInterval: The unit of each dose (NOT includes the interval)
// interval: Days of interval
class Dosage(var dosage: Float, var unitWithoutInterval: String, var interval: Int)

// Classes of single records
interface DataRecord

class TestRecord(
    val data: Float,
    val time: LocalDateTime,
): DataRecord

class MedicationRecord(
    val data: Float,
    var dosage: Dosage,
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