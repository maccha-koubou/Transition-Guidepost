package com.maccha_koubou.transition_guidepost.view

import android.os.Build
import androidx.annotation.Dimension
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.model.DataRecord
import com.maccha_koubou.transition_guidepost.model.TestData
import com.maccha_koubou.transition_guidepost.model.TestRecord
import com.maccha_koubou.transition_guidepost.storage.chartDateSetting
import com.maccha_koubou.transition_guidepost.storage.e2Data
import com.maccha_koubou.transition_guidepost.storage.tData
import com.maccha_koubou.transition_guidepost.ui.theme.Blue
import com.maccha_koubou.transition_guidepost.ui.theme.Gray
import com.maccha_koubou.transition_guidepost.ui.theme.LightPurple
import com.maccha_koubou.transition_guidepost.ui.theme.Pink
import com.maccha_koubou.transition_guidepost.ui.theme.Purple
import com.maccha_koubou.transition_guidepost.ui.theme.Typography
import com.maccha_koubou.transition_guidepost.ui.theme.White
import com.maccha_koubou.transition_guidepost.ui.theme.cardColors
import com.maccha_koubou.transition_guidepost.view.component.rememberMarker
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisLineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisTickComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberEnd
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shape.dashedShape
import com.patrykandpatrick.vico.core.cartesian.AutoScrollCondition
import com.patrykandpatrick.vico.core.cartesian.Scroll
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.decoration.HorizontalBox
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun HormoneChart() {
    val modelProducer = remember { CartesianChartModelProducer() }

    val timeRange = mutableListOf(chartDateSetting.earliestDateTotal!!, chartDateSetting.endDate!!)
    val timeRangeAssistance = mutableListOf(0,0) // Only used for scroll the chart to the latest day

    val data1Copy by remember { mutableStateOf(chartDateSetting.hormoneChartFirstData() as TestData) }
    val data2Copy by remember { mutableStateOf(chartDateSetting.hormoneChartSecondData() as TestData) }

    var data1 = data1Copy.copy()
    var data2 = data2Copy.copy()

    // Is each data series is not empty
    var isData1Available by remember { mutableStateOf(data1.dataList.isNotEmpty()) }
    var isData2Available by remember { mutableStateOf(data2.dataList.isNotEmpty()) }

    // Colors
    val color1 = data1.color
    val color2 = data2.color

    var recommendationRangeBox by remember { mutableStateOf(listOf<HorizontalBox>()) }

    // To only show the data within the customized date duration
    val zoomState = remember { Zoom { context, horizontalDimensions, bounds ->
        val totalContentWidth = horizontalDimensions.getScalableContentWidth(context)
        val containerWidth = bounds.width() - horizontalDimensions.unscalablePadding
        if (totalContentWidth == 0f || chartDateSetting.displayedDurationProportion == null) {
            1f
        } else {
            val testT = chartDateSetting
                .hormoneChartFirstData()
                .dataList.minOfOrNull {it.time.toLocalDate().toEpochDay()}
            containerWidth / (totalContentWidth * chartDateSetting.displayedDurationProportion!!)
        }
    }}

    // Common look of the axes
    val axisLine = rememberAxisLineComponent(fill(Color.Transparent))
    val axisGuideline = rememberAxisGuidelineComponent(
        fill = fill(LightPurple),
        thickness = 1.dp,
        shape = dashedShape(Shape.Rectangle, 10.dp, 0.dp),
        margins = Dimensions(1f, 0f)
    )

    // Update the data series on the chart every 0.3s
    LaunchedEffect(Unit) {
        while (true) {
            // Add and remove an item in these 2 lists
            // to force the chart scroll to the latest day
            timeRange.add(chartDateSetting.endDate!!)
            timeRangeAssistance.add(0)

            data1 = data1Copy.copy()
            data2 = data2Copy.copy()

            data1.dataList = removeUnshownDate(removeSameDate(data1.dataList))
            data2.dataList = removeUnshownDate(removeSameDate(data2.dataList))


            isData1Available = data1.dataList.isNotEmpty()
            isData2Available = data2.dataList.isNotEmpty()

            recommendationRangeBox = listOfNotNull(
                if (isData1Available) {
                    recommendationRangeBoxOrNull(
                        (data1)
                            .recommendationValue,
                        data1.dataList.maxOf { it.data },
                        color1,
                        Axis.Position.Vertical.Start
                    )
                } else { null },
                if (isData2Available) {
                    recommendationRangeBoxOrNull(
                        (data2)
                            .recommendationValue,
                        data2.dataList.maxOf { it.data },
                        color2,
                        Axis.Position.Vertical.End
                    )
                } else { null }
            )

            modelProducer.runTransaction {
                // The first series used to keep the chart
                // including the earliest and latest date of all data
                lineSeries {
                    series(
                        x = timeRange,
                        y = timeRangeAssistance
                    )
                }
                if (isData1Available) {
                    lineSeries {
                        series(
                            x = data1.dataList.map {
                                it.time.toLocalDate().toEpochDay()
                            },
                            y = data1.dataList.map { it.data }
                        )
                    }
                }
                if (isData2Available) {
                    lineSeries {
                        series(
                            x = data2.dataList.map {
                                it.time.toLocalDate().toEpochDay()
                            },
                            y = data2.dataList.map { it.data }
                        )
                    }
                }
            }

            timeRange.removeLast()
            timeRangeAssistance.removeLast()
            delay(300L)
        }
    }

    // The data name and unit label
    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = data1.name,
                    textAlign = TextAlign.Left,
                    style = Typography.labelMedium,
                    color = color1
                )
                Text(
                    text = data1.displayedUnit,
                    textAlign = TextAlign.Left,
                    style = Typography.labelSmall,
                    color = color1
                )
            }
            Row(Modifier.weight(1f)) {}
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = data2.name,
                    textAlign = TextAlign.Right,
                    style = Typography.labelMedium,
                    color = color2
                )
                Text(
                    text = data2.displayedUnit,
                    textAlign = TextAlign.Right,
                    style = Typography.labelSmall,
                    color = color2
                )
            }
        }

        // The chart itself
        Card(
            modifier = Modifier.wrapContentSize(),
            shape = RectangleShape,
            colors = cardColors
        ) {
            CartesianChartHost(
                chart = rememberCartesianChart(
                    *listOfNotNull(
                        // Nothing is shown in this layer
                        rememberLineCartesianLayer(
                            LineCartesianLayer.LineProvider.series(
                                LineCartesianLayer.rememberLine(
                                    fill = remember { LineCartesianLayer.LineFill.single(fill(Color.Transparent)) },
                                    areaFill = null,
                                    thickness = 0.dp
                                )
                            )
                        ),

                        if (isData1Available) {
                            rememberLineCartesianLayer(
                                LineCartesianLayer.LineProvider.series(
                                    LineCartesianLayer.rememberLine(
                                        fill = remember {
                                            LineCartesianLayer.LineFill.single(
                                                fill(
                                                    color1
                                                )
                                            )
                                        },
                                        areaFill = null,
                                        thickness = 2.dp,
                                        pointProvider = remember {
                                            // Point style (circle) of the first data series
                                            LineCartesianLayer.PointProvider.single(
                                                LineCartesianLayer.Point(
                                                    ShapeComponent(
                                                        fill = fill(White),
                                                        shape = CorneredShape(
                                                            Corner.FullyRounded,
                                                            Corner.FullyRounded,
                                                            Corner.FullyRounded,
                                                            Corner.FullyRounded
                                                        ),
                                                        strokeFill = fill(color1),
                                                        strokeThicknessDp = 1.5f
                                                    ),
                                                    sizeDp = 10f
                                                )
                                            )
                                        },
                                        // Make the line polygonal instead of curved
                                        pointConnector = remember {
                                            LineCartesianLayer.PointConnector.cubic(
                                                0f
                                            )
                                        }
                                    )
                                ),
                                verticalAxisPosition = Axis.Position.Vertical.Start
                            )
                        } else {
                            null
                        },

                        if (isData2Available) {
                            rememberLineCartesianLayer(
                                LineCartesianLayer.LineProvider.series(
                                    LineCartesianLayer.rememberLine(
                                        fill = remember {
                                            LineCartesianLayer.LineFill.single(
                                                fill(
                                                    color2
                                                )
                                            )
                                        },
                                        areaFill = null,
                                        thickness = 2.dp,
                                        pointProvider = remember {
                                            // Point style (square) of the second data series
                                            LineCartesianLayer.PointProvider.single(
                                                LineCartesianLayer.Point(
                                                    ShapeComponent(
                                                        fill = fill(White),
                                                        strokeFill = fill(color2),
                                                        strokeThicknessDp = 1.5f
                                                    ),
                                                    sizeDp = 10f
                                                )
                                            )
                                        },
                                        // Make the line polygonal instead of curved
                                        pointConnector = remember {
                                            LineCartesianLayer.PointConnector.cubic(
                                                0f
                                            )
                                        }
                                    )
                                ),
                                verticalAxisPosition = Axis.Position.Vertical.End,
                            )
                        } else {
                            null
                        }
                    ).toTypedArray(),
                    startAxis = VerticalAxis.rememberStart(
                        line = axisLine,
                        label = rememberAxisLabelComponent(color1),
                        tick = axisLine,
                        tickLength = 0.dp,
                        guideline = axisGuideline
                    ),
                    endAxis = VerticalAxis.rememberEnd(
                        line = axisLine,
                        label = rememberAxisLabelComponent(color2),
                        tick = axisLine,
                        tickLength = 0.dp,
                        guideline = axisLine
                    ),
                    bottomAxis = HorizontalAxis.rememberBottom(
                        line = rememberAxisLineComponent(fill(LightPurple), 1.dp),
                        valueFormatter = { _, x, _ ->
                            val date = LocalDate.ofEpochDay(x.toLong())
                            val formatter = DateTimeFormatter.ofPattern("M月d日")
                            date.format(formatter)
                        },
                        label = rememberAxisLabelComponent(Gray),
                        tick = rememberAxisTickComponent(fill(LightPurple), 2.dp),
                        guideline = rememberAxisGuidelineComponent(fill(Color.Transparent)),
                        itemPlacer = HorizontalAxis.ItemPlacer.aligned(90)
                    ),
                    // The recommendation range box of the first data series
                    decorations = recommendationRangeBox,
                    marker = rememberMarker(
                        data1,
                        data2
                    )
                ),
                modelProducer = modelProducer,
                modifier = Modifier.fillMaxSize(),
                scrollState = rememberVicoScrollState(
                    // Disable scroll but scroll to the end of the chart by default
                    scrollEnabled = false,
                    initialScroll = Scroll.Absolute.End,
                    autoScroll = Scroll.Absolute.End,
                    autoScrollCondition = AutoScrollCondition.OnModelSizeIncreased
                ),

                zoomState = rememberVicoZoomState(
                    zoomEnabled = true,
                    initialZoom = zoomState,
                    minZoom = zoomState,
                    maxZoom = zoomState
                )
            )
        }
    }
}

/**
 * Remove the data with the same date information,
 * and only leave the data in the latter place in the list
 */
private fun removeSameDate(list: MutableList<TestRecord>): MutableList<TestRecord> {
    val finalList = mutableListOf<TestRecord>()
    val existingTime = mutableListOf<LocalDate>()
    list.asReversed().forEach{
        if (it.time.toLocalDate() !in existingTime) {
            existingTime.add(it.time.toLocalDate())
            finalList.add(it)
        }
    }
    return finalList.asReversed()
}

/**
 * Remove the data that are too early to be shown in this chart
 * Only leave the latest one among all the date before the displayable range
 * to make sure that the line in the chart can extend beyond the left edge of the chart
 */
private fun removeUnshownDate(list: MutableList<TestRecord>): MutableList<TestRecord> {
    val latestUnshownDate = list
        .filter { it.time.toLocalDate().toEpochDay() <
                chartDateSetting.finalStartDate!! - chartDateSetting.customizedDuration * 0.1 }
        .maxByOrNull { it.time }
        ?.time
    return if (latestUnshownDate != null) {
        list
            .filter {
                it.time >= latestUnshownDate
            }.toMutableList()
    } else { list }
}

/**
 * This function can determine whether the recommendation range box
 * can be accommodated by the chart,
 * and only display the containable part of the recommendation range box
 *
 * If the recommendation data has not be set already, this function returns a null
 */
private fun recommendationRangeBoxOrNull(
    recommendationRange: ClosedFloatingPointRange<Float>?,
    largestDataInTheChart: Float?,
    color: Color,
    axis: Axis.Position.Vertical
): HorizontalBox? {
    // Only call the recommendation range box when the recommendation range is already set
    if (recommendationRange != null && largestDataInTheChart != null) {
        val recommendationLowerRange = recommendationRange.start
        val recommendationHigherRange = recommendationRange.endInclusive

        // This value is used to extend the recommendation range box
        // to a little higher position for a better look
        val largestDataInTheChartWithMarginal = largestDataInTheChart * 1.1f

        // If the recommendation range's lower edge can be shown in this chart
        if (recommendationLowerRange < largestDataInTheChartWithMarginal) {
            return HorizontalBox(
                y = {
                    // The lower edge of the range box is set as the lower edge of the recommendation range
                    (recommendationLowerRange.toDouble())..(
                            // Only use the recommendation range's higher edge
                            // when it can be shown inside this chart
                            minOf(recommendationHigherRange, largestDataInTheChartWithMarginal).toDouble()
                            )
                },
                // The transparent color of the recommendation range box
                box = ShapeComponent(fill(color.copy(0.1f))),
                verticalAxisPosition = axis
            )
        } else {
            return null
        }
    } else {
        return null
    }
}