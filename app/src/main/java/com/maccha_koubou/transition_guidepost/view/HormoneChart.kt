package com.maccha_koubou.transition_guidepost.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.storage.e2Data
import com.maccha_koubou.transition_guidepost.storage.tData
import com.maccha_koubou.transition_guidepost.ui.theme.Blue
import com.maccha_koubou.transition_guidepost.ui.theme.Gray
import com.maccha_koubou.transition_guidepost.ui.theme.LightPurple
import com.maccha_koubou.transition_guidepost.ui.theme.Pink
import com.maccha_koubou.transition_guidepost.ui.theme.Purple
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
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shape.dashedShape
import com.patrykandpatrick.vico.core.cartesian.Scroll
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.core.common.shape.Shape
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HormoneChart() {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction { lineSeries {
            series(x = e2Data.dataList.map { it.time.toLocalDate().toEpochDay() }, y = e2Data.dataList.map { it.data })
            series(x = tData.dataList.map { it.time.toLocalDate().toEpochDay() }, y = tData.dataList.map { it.data })
        } }
    }
    CartesianChartHost(
        chart = rememberCartesianChart(
            rememberLineCartesianLayer(
                LineCartesianLayer.LineProvider.series(
                    LineCartesianLayer.rememberLine(
                        fill = remember { LineCartesianLayer.LineFill.single(fill(Pink)) },
                        areaFill = null,
                        thickness = 2.dp,
                        pointProvider = remember {
                            LineCartesianLayer.PointProvider.single(
                                LineCartesianLayer.Point(
                                ShapeComponent(
                                    fill = fill(White),
                                    shape = CorneredShape(Corner.FullyRounded, Corner.FullyRounded, Corner.FullyRounded, Corner.FullyRounded),
                                    strokeFill = fill(Pink),
                                    strokeThicknessDp = 1.5f
                                ),
                                sizeDp = 10f
                            ))
                        },
                        // Make the line polygonal instead of curved
                        pointConnector = remember { LineCartesianLayer.PointConnector.cubic(0f) }
                    ),
                    LineCartesianLayer.rememberLine(
                        fill = remember { LineCartesianLayer.LineFill.single(fill(Blue)) },
                        areaFill = null,
                        thickness = 2.dp,
                        pointProvider = remember {
                            LineCartesianLayer.PointProvider.single(
                                LineCartesianLayer.Point(
                                ShapeComponent(
                                    fill = fill(White),
                                    strokeFill = fill(Blue),
                                    strokeThicknessDp = 1.5f
                                ),
                                sizeDp = 10f
                            ))
                        },
                        // Make the line polygonal instead of curved
                        pointConnector = remember { LineCartesianLayer.PointConnector.cubic(0f) }
                    )
                )
            ),
            startAxis = VerticalAxis.rememberStart(
                line = rememberAxisLineComponent(fill(Color.Transparent)),
                label = rememberAxisLabelComponent(Pink),
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
                label = rememberAxisLabelComponent(Blue),
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
            marker = rememberMarker()
        ),
        modelProducer = modelProducer,
        modifier = Modifier.fillMaxSize(),
        scrollState = rememberVicoScrollState(
            // Disable scroll but scroll to the end of the chart by default
            scrollEnabled = false,
            initialScroll = Scroll.Absolute.End,
        ),
        zoomState = rememberVicoZoomState(
            zoomEnabled = true,
            initialZoom = remember { Zoom.static(10f) },
            minZoom = remember { Zoom.static(10f) },
            maxZoom = remember { Zoom.static(10f) }
        )
    )
}