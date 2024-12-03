package com.maccha_koubou.transition_guidepost.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.ui.theme.Blue
import com.maccha_koubou.transition_guidepost.ui.theme.Pink
import com.maccha_koubou.transition_guidepost.ui.theme.White
import com.maccha_koubou.transition_guidepost.view.component.rememberMarker
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberEnd
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.CorneredShape

@Composable
fun HormoneChart() {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction { lineSeries {
            series(400, 1200, 800.85, 1600)
            series(x = listOf(1, 2, 3, 40), y = listOf(1300, 800, 1500, 2000))
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
            startAxis = VerticalAxis.rememberStart(),
            endAxis = VerticalAxis.rememberEnd(),
            bottomAxis = HorizontalAxis.rememberBottom(),
            marker = rememberMarker()
        ),
        modelProducer = modelProducer,
        modifier = Modifier.fillMaxSize(),
    )
}