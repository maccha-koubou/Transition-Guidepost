package com.maccha_koubou.transition_guidepost.view.component

import android.annotation.SuppressLint
import android.text.Layout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.ui.theme.Blue
import com.maccha_koubou.transition_guidepost.ui.theme.Gray
import com.maccha_koubou.transition_guidepost.ui.theme.Pink
import com.maccha_koubou.transition_guidepost.ui.theme.White
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.common.component.fixed
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.dimensions
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shape.dashedShape
import com.patrykandpatrick.vico.core.cartesian.CartesianDrawingContext
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarkerValueFormatter
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarkerValueFormatter
import com.patrykandpatrick.vico.core.cartesian.marker.LineCartesianLayerMarkerTarget
import com.patrykandpatrick.vico.core.common.Defaults
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.component.Component
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.core.common.shape.Shape
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs

// Customized Data Labels
@Composable
internal fun rememberMarker(): CartesianMarker {
    // The label style of the first data series
    val labelStyle1 =
        rememberTextComponent(
            color = White,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            padding = dimensions(8.dp, 2.dp),
            background = rememberShapeComponent(
                fill = fill(Pink),
                shape = CorneredShape(Corner.FullyRounded, Corner.FullyRounded, Corner.FullyRounded, Corner.FullyRounded)
            ),
            minWidth = TextComponent.MinWidth.fixed(40.dp),
        )
    // The label style of the second data series
    val labelStyle2 =
        rememberTextComponent(
            color = White,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            padding = dimensions(8.dp, 2.dp),
            background = rememberShapeComponent(
                fill = fill(Blue),
                shape = CorneredShape(Corner.FullyRounded, Corner.FullyRounded, Corner.FullyRounded, Corner.FullyRounded)
            ),
            minWidth = TextComponent.MinWidth.fixed(40.dp),
        )
    // The label style of the date label
    val labelStyleDate =
        rememberTextComponent(
            color = White,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            padding = dimensions(8.dp, 2.dp),
            background = rememberShapeComponent(
                fill = fill(Gray),
                shape = CorneredShape(Corner.FullyRounded, Corner.FullyRounded, Corner.FullyRounded, Corner.FullyRounded)
            ),
            minWidth = TextComponent.MinWidth.fixed(40.dp),
        )

    val guideline = rememberAxisGuidelineComponent(
        thickness = 3.dp,
        fill = fill(Gray),
        shape = dashedShape(Shape.Rectangle, 2.dp, 2.dp),
    )

    return remember(labelStyle1, labelStyle2, guideline) {
        object :
            ChartMarker(
                labelStyle1 = labelStyle1,
                labelStyle2 = labelStyle2,
                labelStyleDate = labelStyleDate,
                labelPosition = LabelPosition.AroundPoint,
                guideline = guideline,
            ) {}
    }
}

private const val LABEL_BACKGROUND_SHADOW_RADIUS_DP = 4f
private const val LABEL_BACKGROUND_SHADOW_DY_DP = 2f
private const val CLIPPING_FREE_SHADOW_RADIUS_MULTIPLIER = 1.4f

open class ChartMarker(
    val labelStyle1: TextComponent,
    val labelStyle2: TextComponent,
    val labelStyleDate: TextComponent,
    valueFormatter: CartesianMarkerValueFormatter = DefaultCartesianMarkerValueFormatter(),
    labelPosition: LabelPosition = LabelPosition.Top,
    indicator: ((Int) -> Component)? = null,
    @SuppressLint("RestrictedApi") indicatorSizeDp: Float = Defaults.MARKER_INDICATOR_SIZE,
    guideline: LineComponent? = null,
) : DefaultCartesianMarker(labelStyle1, valueFormatter, labelPosition, indicator, indicatorSizeDp, guideline) {



    override fun drawOverLayers(
        context: CartesianDrawingContext,
        targets: List<CartesianMarker.Target>,
    ) {
        with(context) {
            drawGuideline(targets)
            drawCustomizedLabel(context, targets, labelStyle1, labelStyle2, labelStyleDate)
        }
    }

    @SuppressLint("DefaultLocale")
    fun drawCustomizedLabel(
        context: CartesianDrawingContext,
        targets: List<CartesianMarker.Target>,
        labelStyle1: TextComponent,
        labelStyle2: TextComponent,
        labelStyleDate: TextComponent
    ) {
        targets as List<LineCartesianLayerMarkerTarget>
        val targetSize = targets.size
        targets.forEachIndexed { index, target ->
            val minLabelDistance = label.getBounds(context, "").height()
            target.points.forEachIndexed { index, point ->

                val date = LocalDate.ofEpochDay(point.entry.x.toLong())
                val formatter = DateTimeFormatter.ofPattern("yyyy年M月d日")

                labelStyleDate.draw(
                    context = context,
                    text = date.format(formatter),
                    x = target.canvasX,
                    y = context.layerBounds.bottom + minLabelDistance - context.dpToPx(5f)
                )

                val thisLabel: TextComponent
                // Use the point's color as the label's color
                when (Fill(point.color)) {
                    (labelStyle1.background as ShapeComponent).fill ->
                        thisLabel = labelStyle1

                    else ->
                        thisLabel = labelStyle2
                }

                thisLabel.draw(
                    context = context,
                    text = String.format("%.2f", point.entry.y),
                    x = target.canvasX,
                    // Avoid labels overlap or become too close
                    y = if (target.points.size == 2) {
                        // If there are two labels and they are overlap
                        if (target.points[0].canvasY == target.points[1].canvasY) {
                            when (index) {
                                // Move up the first label and move down the other
                                0 -> point.canvasY + minLabelDistance / 2
                                else -> point.canvasY - minLabelDistance / 2
                            }
                            // If there are two labels and they are too close
                        } else if (abs(target.points[0].canvasY - target.points[1].canvasY) < minLabelDistance) {
                            val labelOffset =
                                (minLabelDistance - abs(target.points[0].canvasY - target.points[1].canvasY)) / 2
                            when {
                                // Move up the Upper label and move down the lower one
                                point.canvasY >= target.points[0].canvasY && point.canvasY >= target.points[1].canvasY ->
                                    point.canvasY + labelOffset

                                else -> point.canvasY - labelOffset
                            }
                        } else {
                            point.canvasY
                        }
                    } else {
                        point.canvasY
                    }
                )
            }
        }
    }
}