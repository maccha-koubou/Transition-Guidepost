package com.maccha_koubou.transition_guidepost.model

import com.maccha_koubou.transition_guidepost.storage.e2Data
import com.maccha_koubou.transition_guidepost.storage.tData
import java.time.Duration
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class chartDateDuration(
    var hormoneChartFirstData: () -> RecordedData<DataRecord>,
    var hormoneChartSecondData: () -> RecordedData<DataRecord>,
    var medicationChartFirstData: () -> RecordedData<DataRecord>,
    var medicationChartSecondData: () -> RecordedData<DataRecord>,
    var bodyChartFirstData: () -> RecordedData<DataRecord>,
    var bodyChartSecondData: () -> RecordedData<DataRecord>,
    var healthChartFirstData: () -> RecordedData<DataRecord>,
    var healthChartSecondData: () -> RecordedData<DataRecord>
) {
    var customizedDuration = 365L
    var startDate: LocalDate? = null
    val duration: Long get() = startDate?.let {
        ChronoUnit.DAYS.between(startDate, LocalDate.now()).toLong()
    } ?: customizedDuration

    // The latest date of the chart
    var chartDateEnd: Long? =
        if (hormoneChartFirstData().dataList.size == 0) {
            hormoneChartSecondData().dataList.maxOfOrNull { it.time.toLocalDate().toEpochDay() }
        } else if (hormoneChartSecondData().dataList.size == 0) {
            hormoneChartFirstData().dataList.maxOf { it.time.toLocalDate().toEpochDay() }
        } else {
            maxOf(
                hormoneChartFirstData().dataList.maxOf { it.time.toLocalDate().toEpochDay() },
                hormoneChartSecondData().dataList.maxOf { it.time.toLocalDate().toEpochDay() }
            )
        }

    // The earliest date of the chart
    var chartDateStart: Long? =
        chartDateEnd?.let {
            if (hormoneChartFirstData().dataList.size == 0) {
                maxOf(
                    hormoneChartSecondData().dataList.minOf { it.time.toLocalDate().toEpochDay() },
                    chartDateEnd!! - 365L
                )
            } else if (hormoneChartSecondData().dataList.size == 0) {
                maxOf(
                    hormoneChartFirstData().dataList.minOf { it.time.toLocalDate().toEpochDay() },
                    chartDateEnd!! - 365L
                )
            } else {
                maxOf(
                    minOf(
                        hormoneChartFirstData().dataList.minOf { it.time.toLocalDate().toEpochDay() },
                        hormoneChartSecondData().dataList.minOf { it.time.toLocalDate().toEpochDay() }
                    ),
                    chartDateEnd!! - 365L
                )
            }
        }
}