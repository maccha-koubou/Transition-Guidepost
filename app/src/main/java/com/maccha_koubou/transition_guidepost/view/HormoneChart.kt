package com.maccha_koubou.transition_guidepost.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.model.TestData
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
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HormoneChart() {
    val modelProducer = remember { CartesianChartModelProducer() }

    // Update the data series on the chart every 5s
    LaunchedEffect(Unit) {
        while (true) {
            modelProducer.runTransaction {
                lineSeries {
                    series(
                        x = chartDateSetting.hormoneChartFirstData().dataList.map {
                            it.time.toLocalDate().toEpochDay()
                        },
                        y = chartDateSetting.hormoneChartFirstData().dataList.map { it.data })
                }
                lineSeries {
                    series(
                        x = chartDateSetting.hormoneChartSecondData().dataList.map {
                            it.time.toLocalDate().toEpochDay()
                        },
                        y = chartDateSetting.hormoneChartSecondData().dataList.map { it.data })
                }
            }
            delay(5000L)
        }
    }

    // To only show the data within the customized date duration
    val zoomState = remember { Zoom { context, horizontalDimensions, bounds ->
        val totalContentWidth = horizontalDimensions.getScalableContentWidth(context)
        val containerWidth = bounds.width() - horizontalDimensions.unscalablePadding
        if (totalContentWidth == 0f || chartDateSetting.displayedDurationProportion == null) {
            1f
        } else {
            containerWidth / (totalContentWidth * chartDateSetting.displayedDurationProportion!!)
        }
    }}

    // The data name and unit label
    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = chartDateSetting.hormoneChartFirstData().name,
                    textAlign = TextAlign.Left,
                    style = Typography.labelMedium,
                    color = chartDateSetting.hormoneChartFirstData().color
                )
                Text(
                    text = chartDateSetting.hormoneChartFirstData().displayedUnit,
                    textAlign = TextAlign.Left,
                    style = Typography.labelSmall,
                    color = chartDateSetting.hormoneChartFirstData().color
                )
            }
            Row(Modifier.weight(1f)) {}
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = chartDateSetting.hormoneChartSecondData().name,
                    textAlign = TextAlign.Right,
                    style = Typography.labelMedium,
                    color = chartDateSetting.hormoneChartSecondData().color
                )
                Text(
                    text = chartDateSetting.hormoneChartSecondData().displayedUnit,
                    textAlign = TextAlign.Right,
                    style = Typography.labelSmall,
                    color = chartDateSetting.hormoneChartSecondData().color
                )
            }
        }

        // The chart itself
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(
                    LineCartesianLayer.LineProvider.series(
                        LineCartesianLayer.rememberLine(
                            fill = remember { LineCartesianLayer.LineFill.single(fill(chartDateSetting.hormoneChartFirstData().color)) },
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
                                            strokeFill = fill(chartDateSetting.hormoneChartFirstData().color),
                                            strokeThicknessDp = 1.5f
                                        ),
                                        sizeDp = 10f
                                    )
                                )
                            },
                            // Make the line polygonal instead of curved
                            pointConnector = remember { LineCartesianLayer.PointConnector.cubic(0f) }
                        )
                    ),
                    verticalAxisPosition = Axis.Position.Vertical.Start
                ),

                rememberLineCartesianLayer(
                    LineCartesianLayer.LineProvider.series(
                        LineCartesianLayer.rememberLine(
                            fill = remember { LineCartesianLayer.LineFill.single(fill(chartDateSetting.hormoneChartSecondData().color)) },
                            areaFill = null,
                            thickness = 2.dp,
                            pointProvider = remember {
                                // Point style (square) of the second data series
                                LineCartesianLayer.PointProvider.single(
                                    LineCartesianLayer.Point(
                                        ShapeComponent(
                                            fill = fill(White),
                                            strokeFill = fill(chartDateSetting.hormoneChartSecondData().color),
                                            strokeThicknessDp = 1.5f
                                        ),
                                        sizeDp = 10f
                                    )
                                )
                            },
                            // Make the line polygonal instead of curved
                            pointConnector = remember { LineCartesianLayer.PointConnector.cubic(0f) }
                        )
                    ),
                    verticalAxisPosition = Axis.Position.Vertical.End,
                ),
                startAxis = VerticalAxis.rememberStart(
                    line = rememberAxisLineComponent(fill(Color.Transparent)),
                    label = rememberAxisLabelComponent(chartDateSetting.hormoneChartFirstData().color),
                    tick = rememberAxisTickComponent(fill(Color.Transparent)),
                    tickLength = 0.dp,
                    guideline = rememberAxisGuidelineComponent(
                        fill = fill(LightPurple),
                        thickness = 1.dp,
                        shape = dashedShape(Shape.Rectangle, 10.dp, 0.dp)
                    )
                ),
                endAxis = VerticalAxis.rememberEnd(
                    line = rememberAxisLineComponent(fill(Color.Transparent)),
                    label = rememberAxisLabelComponent(chartDateSetting.hormoneChartSecondData().color),
                    tick = rememberAxisTickComponent(fill(Color.Transparent)),
                    tickLength = 0.dp,
                    guideline = rememberAxisGuidelineComponent(
                        fill = fill(LightPurple),
                        thickness = 1.dp,
                        shape = dashedShape(Shape.Rectangle, 10.dp, 0.dp)
                    )
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
                    guideline = rememberAxisGuidelineComponent(fill(Color.Transparent))
                ),
                // The recommendation range box of the first data series
                decorations = if (chartDateSetting.hormoneChartFirstData() is TestData) {
                    // Only show recommendation range box when the data is TestData
                    listOfNotNull(
                        if ((
                            // If the recommendation range is already set
                            chartDateSetting.hormoneChartFirstData() as TestData)
                                .recommendationValue != null
                            &&
                            // And the recommendation range can be shown in this chart
                            (chartDateSetting.hormoneChartFirstData() as TestData)
                                .recommendationValue!!.start <
                                    chartDateSetting.hormoneChartFirstData()
                                        .dataList.maxOf { it.data }) {
                            HorizontalBox(
                                y = {(
                                        // The range box's bottom edge is set as the lower edge of the recommendation range
                                        (
                                                chartDateSetting.hormoneChartFirstData() as TestData)
                                                    .recommendationValue!!.start.toDouble()
                                        )..(
                                                if ((chartDateSetting.hormoneChartFirstData() as TestData)
                                                        .recommendationValue!!.endInclusive <
                                                    chartDateSetting.hormoneChartFirstData()
                                                        .dataList.maxOf { it.data }) {
                                                    (chartDateSetting.hormoneChartFirstData() as TestData)
                                                        .recommendationValue!!.endInclusive.toDouble()
                                                } else {
                                                    chartDateSetting.hormoneChartFirstData()
                                                        .dataList.maxOf { it.data }.toDouble()
                                                }
                                        )
                                },
                                // The transparent color of the recommendation range box
                                box = ShapeComponent(
                                    fill(
                                        chartDateSetting.hormoneChartFirstData().color.copy(
                                            0.1f
                                        )
                                    )
                                )
                            )
                        } else { null },
                        // The recommendation range box of the second data series
                        HorizontalBox(
                            // The range of the recommendation value of this data series
                            y = { 7.0..14.0 },
                            // The transparent color of the recommendation range box
                            box = ShapeComponent(
                                fill(
                                    chartDateSetting.hormoneChartSecondData().color.copy(
                                        0.1f
                                    )
                                )
                            )
                        )
                    )
                } else { emptyList() },
                marker = rememberMarker()
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