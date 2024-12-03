package com.maccha_koubou.transition_guidepost.view.component

import android.annotation.SuppressLint
import android.text.Layout
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.maccha_koubou.transition_guidepost.ui.theme.Gray
import com.maccha_koubou.transition_guidepost.ui.theme.Pink
import com.maccha_koubou.transition_guidepost.ui.theme.Purple
import com.maccha_koubou.transition_guidepost.ui.theme.White
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.common.component.fixed
import com.patrykandpatrick.vico.compose.common.component.rememberLayeredComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.component.shadow
import com.patrykandpatrick.vico.compose.common.dimensions
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.shape.dashedShape
import com.patrykandpatrick.vico.compose.common.shape.markerCorneredShape
import com.patrykandpatrick.vico.core.cartesian.CartesianDrawingContext
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.core.cartesian.HorizontalDimensions
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.marker.CandlestickCartesianLayerMarkerTarget
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarkerValueFormatter
import com.patrykandpatrick.vico.core.cartesian.marker.ColumnCartesianLayerMarkerTarget
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarkerValueFormatter
import com.patrykandpatrick.vico.core.cartesian.marker.LineCartesianLayerMarkerTarget
import com.patrykandpatrick.vico.core.common.Defaults
import com.patrykandpatrick.vico.core.common.Fill
import com.patrykandpatrick.vico.core.common.Insets
import com.patrykandpatrick.vico.core.common.LayeredComponent
import com.patrykandpatrick.vico.core.common.VerticalPosition
import com.patrykandpatrick.vico.core.common.component.Component
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.component.Shadow
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.half
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.core.common.shape.MarkerCorneredShape
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlin.math.ceil
import kotlin.math.min

@Composable
internal fun rememberMarker(
    labelPosition: DefaultCartesianMarker.LabelPosition = DefaultCartesianMarker.LabelPosition.Top
): CartesianMarker {
    val label =
        rememberTextComponent(
            color = White,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            //padding = dimensions(8.dp, 4.dp),
            background = rememberShapeComponent(
                fill = fill(Pink),
                shape = CorneredShape(Corner.FullyRounded, Corner.FullyRounded, Corner.FullyRounded, Corner.FullyRounded)
            ),
            minWidth = TextComponent.MinWidth.fixed(40.dp),
        )
    val indicatorFrontComponent =
        rememberShapeComponent(fill(Purple), CorneredShape.Pill)
    val indicatorCenterComponent = rememberShapeComponent(shape = CorneredShape.Pill)
    val indicatorRearComponent = rememberShapeComponent(shape = CorneredShape.Pill)
    val indicator =
        rememberLayeredComponent(
            rear = indicatorRearComponent,
            front =
            rememberLayeredComponent(
                rear = indicatorCenterComponent,
                front = indicatorFrontComponent,
                padding = dimensions(5.dp),
            ),
            padding = dimensions(10.dp),
        )
    val guideline = rememberAxisGuidelineComponent(
        thickness = 3.dp,
        fill = fill(Gray),
        shape = dashedShape(Shape.Rectangle, 2.dp, 2.dp),
    )
    return remember(label, labelPosition, indicator, guideline) {
        object :
            ChartMarker(
                label = label,
                labelPosition = DefaultCartesianMarker.LabelPosition.AroundPoint,
                guideline = guideline,
            ) {
            /*override fun updateInsets(
                context: CartesianMeasuringContext,
                horizontalDimensions: HorizontalDimensions,
                model: CartesianChartModel,
                insets: Insets,
            ) {
                with(context) {
                    val baseShadowInsetDp =
                        CLIPPING_FREE_SHADOW_RADIUS_MULTIPLIER * LABEL_BACKGROUND_SHADOW_RADIUS_DP
                    var topInset = (6f - 2f).pixels + label.getHeight(context) + tickSizeDp.pixels
                    var bottomInset = (6f + LABEL_BACKGROUND_SHADOW_DY_DP).pixels
                    insets.ensureValuesAtLeast(top = 16f.pixels, bottom = 58f.pixels)
                }
            }*/
        }
    }
}

private const val LABEL_BACKGROUND_SHADOW_RADIUS_DP = 4f
private const val LABEL_BACKGROUND_SHADOW_DY_DP = 2f
private const val CLIPPING_FREE_SHADOW_RADIUS_MULTIPLIER = 1.4f


open class ChartMarker(
    label: TextComponent,
    valueFormatter: CartesianMarkerValueFormatter = DefaultCartesianMarkerValueFormatter(),
    labelPosition: LabelPosition = LabelPosition.Top,
    indicator: ((Int) -> Component)? = null,
    @SuppressLint("RestrictedApi") indicatorSizeDp: Float = Defaults.MARKER_INDICATOR_SIZE,
    guideline: LineComponent? = null,
) : DefaultCartesianMarker(label, valueFormatter, labelPosition, indicator, indicatorSizeDp, guideline) {

    @SuppressLint("RestrictedApi")
    override fun drawOverLayers(
        context: CartesianDrawingContext,
        targets: List<CartesianMarker.Target>,
    ) {
        with(context) {
            drawGuideline(targets)
            val halfIndicatorSize = indicatorSizeDp.half.pixels

            targets.forEach { target -> drawCustomizedLabel(context, target) }
        }
    }

    @SuppressLint("RestrictedApi")
    fun drawCustomizedLabel(
        context: CartesianDrawingContext,
        target: CartesianMarker.Target,
    ): Unit =
        when (target) {
            is LineCartesianLayerMarkerTarget -> {
                target.points.forEach { point ->
                    label.draw(
                        context = context,
                        text = point.entry.y.toString(),
                        x = target.canvasX,
                        y = point.canvasY,
                        /*verticalPosition = verticalPosition,
                        maxWidth = ceil(min(layerBounds.right - x, x - layerBounds.left).doubled).toInt(),
                    */)
                }
            }
            else -> {}
        }


        /*with(context) {
            val text = targets[0].x.toString()
            val targetX = targets[0].canvasX
            val labelBounds =
                label.getBounds(
                    context = context,
                    text = text,
                    maxWidth = layerBounds.width().toInt(),
                    outRect = tempBounds,
                )
            val halfOfTextWidth = labelBounds.width().half
            val x = overrideXPositionToFit(targetX, layerBounds, halfOfTextWidth)
            markerCorneredShape?.tickX = targetX
            val tickPosition: MarkerCorneredShape.TickPosition
            val y: Float = context.layerBounds.top - tickSizeDp.pixels
            val verticalPosition: VerticalPosition
            when (labelPosition) {
                LabelPosition.Top -> {
                    tickPosition = MarkerCorneredShape.TickPosition.Bottom
                    //y = context.layerBounds.top - tickSizeDp.pixels
                    verticalPosition = VerticalPosition.Top
                }
                LabelPosition.Bottom -> {
                    tickPosition = MarkerCorneredShape.TickPosition.Top
                    //y = context.layerBounds.bottom + tickSizeDp.pixels
                    verticalPosition = VerticalPosition.Bottom
                }
                LabelPosition.AroundPoint,
                LabelPosition.AbovePoint -> {
                    val topPointY =
                        targets.minOf { target ->
                            when (target) {
                                is CandlestickCartesianLayerMarkerTarget -> target.highCanvasY
                                is ColumnCartesianLayerMarkerTarget ->
                                    target.columns.minOf(ColumnCartesianLayerMarkerTarget.Column::canvasY)
                                is LineCartesianLayerMarkerTarget ->
                                    target.points.minOf(LineCartesianLayerMarkerTarget.Point::canvasY)
                                else -> error("Unexpected `CartesianMarker.Target` implementation.")
                            }
                        }
                    val flip =
                        labelPosition == LabelPosition.AroundPoint &&
                                topPointY - labelBounds.height() - tickSizeDp.pixels < context.layerBounds.top
                    tickPosition =
                        if (flip) MarkerCorneredShape.TickPosition.Top
                        else MarkerCorneredShape.TickPosition.Bottom
                    //y = topPointY + (if (flip) 1 else -1) * tickSizeDp.pixels
                    verticalPosition = if (flip) VerticalPosition.Bottom else VerticalPosition.Top
                }
            }
            markerCorneredShape?.tickPosition = tickPosition

            label.draw(
                context = context,
                text = text,
                x = x,
                y = y,
                verticalPosition = verticalPosition,
                maxWidth = ceil(min(layerBounds.right - x, x - layerBounds.left)).toInt(),
            )
        }*/

}