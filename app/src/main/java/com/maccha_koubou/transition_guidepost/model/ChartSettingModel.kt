package com.maccha_koubou.transition_guidepost.model

import com.maccha_koubou.transition_guidepost.storage.chartDateSetting
import com.maccha_koubou.transition_guidepost.storage.e2Data
import com.maccha_koubou.transition_guidepost.storage.tData
import java.time.Duration
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class chartDateSetting(
    var hormoneChartFirstData: () -> RecordedData<DataRecord>,
    var hormoneChartSecondData: () -> RecordedData<DataRecord>,
    var medicationChartFirstData: () -> RecordedData<DataRecord>?,
    var medicationChartSecondData: () -> RecordedData<DataRecord>?,
    var bodyChartFirstData: () -> RecordedData<DataRecord>?,
    var bodyChartSecondData: () -> RecordedData<DataRecord>?,
    var healthChartFirstData: () -> RecordedData<DataRecord>?,
    var healthChartSecondData: () -> RecordedData<DataRecord>?
) {
    // By default, the charts will show the data within 1 year before the date of the latest data
    var customizedDuration = 365L

    // User can set a start date of the chart
    var startDate: Long? = null
    var isStartDateAvaliable = false

    // Find the latest date of the data among all charts, it is also the end date of all charts
    val latestDateOfEachDataSeries get() = listOf(
        hormoneChartFirstData().dataList.maxOfOrNull {it.time.toLocalDate().toEpochDay()},
        hormoneChartSecondData().dataList.maxOfOrNull {it.time.toLocalDate().toEpochDay()},
        medicationChartFirstData()?.dataList?.maxOfOrNull {it.time.toLocalDate().toEpochDay()},
        medicationChartSecondData()?.dataList?.maxOfOrNull {it.time.toLocalDate().toEpochDay()},
        bodyChartFirstData()?.dataList?.maxOfOrNull {it.time.toLocalDate().toEpochDay()},
        bodyChartSecondData()?.dataList?.maxOfOrNull {it.time.toLocalDate().toEpochDay()},
        healthChartFirstData()?.dataList?.maxOfOrNull {it.time.toLocalDate().toEpochDay()},
        healthChartSecondData()?.dataList?.maxOfOrNull {it.time.toLocalDate().toEpochDay()}
    )
    val endDate get() = latestDateOfEachDataSeries.filterNotNull().maxOrNull()

    // Find the earliest date of the data among all charts
    val earliestDateOfEachDataSeries get() = listOf(
        hormoneChartFirstData().dataList.minOfOrNull {it.time.toLocalDate().toEpochDay()},
        hormoneChartSecondData().dataList.minOfOrNull {it.time.toLocalDate().toEpochDay()},
        medicationChartFirstData()?.dataList?.minOfOrNull {it.time.toLocalDate().toEpochDay()},
        medicationChartSecondData()?.dataList?.minOfOrNull {it.time.toLocalDate().toEpochDay()},
        bodyChartFirstData()?.dataList?.minOfOrNull {it.time.toLocalDate().toEpochDay()},
        bodyChartSecondData()?.dataList?.minOfOrNull {it.time.toLocalDate().toEpochDay()},
        healthChartFirstData()?.dataList?.minOfOrNull {it.time.toLocalDate().toEpochDay()},
        healthChartSecondData()?.dataList?.minOfOrNull {it.time.toLocalDate().toEpochDay()}
    )
    val earliestDateTotal get() = earliestDateOfEachDataSeries.filterNotNull().minOrNull()

    val finalStartDate get() = if (startDate != null && endDate != null && isStartDateAvaliable) {
        /*
        If user set the start date,
        and it is available and between the latest date and the earliest date,
        the final start date will be set the same as the customized start date,
        which means all charts will show the data from the customized start date to the date of the latest data

        However, if the customized start date is not between the latest date and the earliest date,
        to show the chart correctly, it will be almost not available
        */
        when {
            startDate!! >= endDate!! -> endDate!! - customizedDuration
            startDate!! in earliestDateTotal!! .. endDate!! -> startDate
            else -> earliestDateTotal
        }
    } else if (endDate != null) {
        // If there is not available customized start date,
        // the chart will show the data within a customized period before the date of the latest data
        maxOf(endDate!! - customizedDuration, earliestDateTotal!!)
    } else {
        // No endDate means there is no data at all, there will be no chart
        null
    }

    val displayedDuration get() = endDate?.minus(finalStartDate!!)?.plus(1L)
    val totalDuration get() = endDate?.minus(earliestDateTotal!!)?.plus(1L)

    // The proportion of the displayed duration
    // in the total duration between the earliest date and latest date
    val displayedDurationProportion: Float? get() =  displayedDuration?.toFloat()?.div(totalDuration!!.toFloat())
}