package com.maccha_koubou.transition_guidepost.model

import java.time.LocalTime

// Class of a dosage data
// unitWithoutInterval: The unit of each dose (NOT includes the interval)
// interval: Days of interval
class Dosage(dosage: Float, unitWithoutInterval: String, interval: Int)

// Classes of single records
interface Record

class TestRecord(
    val data: Float,
    val time: LocalTime,
): Record

class MedicationRecord(
    val data: Float,
    var dosage: Dosage,
    val time: LocalTime,
): Record

class CycleRecord(
    val startTime: LocalTime,
    val endTime: LocalTime?,
): Record

class TimePointRecord(
    val time: LocalTime
): Record

class BreastRecord(
    val upperBust: Float,
    val lowerBust: Float,
    val time: LocalTime,
): Record