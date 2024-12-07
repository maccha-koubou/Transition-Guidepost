package com.maccha_koubou.transition_guidepost.view.component

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.text.Layout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.model.DataRecord
import com.maccha_koubou.transition_guidepost.model.RecordedData
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
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.LineCartesianLayerDrawingModel
import com.patrykandpatrick.vico.core.cartesian.data.LineCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer.LineProvider
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
import com.patrykandpatrick.vico.core.common.data.CartesianLayerDrawingModelInterpolator
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.core.common.shape.Shape
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.roundToInt

// Customized Data Labels
@Composable
internal fun rememberMarker(
    data1: RecordedData<DataRecord>?,
    data2: RecordedData<DataRecord>?
): CartesianMarker {
    // The label style of the first data series
    val labelStyle1 =
        rememberTextComponent(
            color = White,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            padding = dimensions(8.dp, 2.dp),
            background = rememberShapeComponent(
                fill = fill(data1?.color ?: Color.Transparent),
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
                fill = fill(data2?.color ?: Color.Transparent),
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
                data1 = data1,
                data2 = data2
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
    val data1: RecordedData<DataRecord>?,
    val data2: RecordedData<DataRecord>?
) : DefaultCartesianMarker(labelStyle1, valueFormatter, labelPosition, indicator, indicatorSizeDp, guideline) {



    override fun drawOverLayers(
        context: CartesianDrawingContext,
        targets: List<CartesianMarker.Target>,
    ) {
        targets as List<LineCartesianLayerMarkerTarget>
        // Except the time range data from the targets list
        // to only draw guidelines and labels of the real data series
        /*val targetsWithoutTimeRangeAssistance = targets.filter {
            Fill(it.points[0].color) == (labelStyle1.background as ShapeComponent).fill
                    || Fill(it.points[0].color) == (labelStyle2.background as ShapeComponent).fill
        }*/
        //val targetsWithoutTimeRangeAssistance = targets.drop(0)
        with(context) {
            drawGuideline(targets/*WithoutTimeRangeAssistance*/)
            drawCustomizedLabel(
                context,
                targets/*WithoutTimeRangeAssistance*/,
                labelStyle1,
                labelStyle2,
                labelStyleDate,
                data1,
                data2
            )
        }
    }

    @SuppressLint("DefaultLocale")
    fun drawCustomizedLabel(
        context: CartesianDrawingContext,
        targets: List<CartesianMarker.Target>,
        labelStyle1: TextComponent,
        labelStyle2: TextComponent,
        labelStyleDate: TextComponent,
        data1: RecordedData<DataRecord>?,
        data2: RecordedData<DataRecord>?
    ) {
        targets as List<LineCartesianLayerMarkerTarget>
        val targetSize = targets.size

        var label1: LineCartesianLayerMarkerTarget? = null
        var label2: LineCartesianLayerMarkerTarget? = null
        var isTwoDataTheSame = false

        val minLabelDistance = label.getBounds(context, "").height()
        val dateLabelY = context.layerBounds.bottom + minLabelDistance - context.dpToPx(5f)
        val secondLowestLabelY = dateLabelY - minLabelDistance
        val thirdLowestLabelY = secondLowestLabelY - minLabelDistance

        targets.forEachIndexed { index, target ->

            // Draw date label
            val date = LocalDate.ofEpochDay(target.points[0].entry.x.toLong())
            val formatter = DateTimeFormatter.ofPattern("yyyy年M月d日")
            labelStyleDate.draw(
                context = context,
                text = date.format(formatter),
                x = target.canvasX,
                y = dateLabelY
            )

            if (data1 != null && data2 != null)
            {
                if (data1.dataList.any {
                    it.data == target.points[0].entry.y.toFloat()
                            &&
                            it.time.toLocalDate().toEpochDay() == target.points[0].entry.x.toLong() }
                ) {
                    label1 = target
                } else if (data2.dataList.any {
                        it.data == target.points[0].entry.y.toFloat()
                                &&
                                it.time.toLocalDate().toEpochDay() == target.points[0].entry.x.toLong() }
                ) {
                    label2 = target
                }
            }

            /*
            when (Fill(target.points[0].color)) {
                (labelStyle1.background as ShapeComponent).fill ->
                    label1 = target
                (labelStyle2.background as ShapeComponent).fill ->
                    label2 = target
                else -> {}
            }
             */
        }

        var label1Y = label1?.points?.get(0)?.canvasY
        var label2Y = label2?.points?.get(0)?.canvasY

        // Calculate the Y position of the two labels when they all exist
        if (
            label1Y != null
            && label2Y != null
            ) {
            if (label1Y == label2Y) {
                // If there are two labels and they are overlap
                // Make sure that there is enough distance between them,
                // and they are all have enough distance from the data label
                label1Y = minOf(label1Y - minLabelDistance / 2, thirdLowestLabelY)
                label2Y = minOf(label2Y + minLabelDistance / 2, secondLowestLabelY)

            } else if (abs(label1Y - label2Y) < minLabelDistance) {
                // If there are two labels and they are too close
                val labelOffset =
                    (minLabelDistance - abs(label1Y - label2Y)) / 2
                when {
                    // Move up the upper label and move down the lower one,
                    // and make sure that they are all have enough distance from the data label
                    label1Y >= label2Y -> {
                        label1Y = minOf(label1Y + labelOffset, secondLowestLabelY)
                        label2Y = minOf(label2Y - labelOffset, thirdLowestLabelY)
                    }
                    else -> {
                        label1Y = minOf(label1Y - labelOffset, thirdLowestLabelY)
                        label2Y = minOf(label2Y + labelOffset, secondLowestLabelY)
                    }
                }
            } else {
                label1Y = minOf(label1Y, secondLowestLabelY)
                label2Y = minOf(label2Y, secondLowestLabelY)
            }
        // Make sure that the label have enough distance from the data label
        } else if (label1Y != null) {
            label1Y = minOf(label1Y, secondLowestLabelY)
        } else if (label2Y != null) {
            label2Y = minOf(label2Y, secondLowestLabelY)
        }

        if (label1 != null) {
            labelStyle1.draw(
                context = context,
                text = String.format("%.2f", label1!!.points[0].entry.y),
                x = label1!!.canvasX,
                y = label1Y!!
            )
        }
        if (label2 != null) {
            labelStyle2.draw(
                context = context,
                text = String.format("%.2f", label2!!.points[0].entry.y),
                x = label2!!.canvasX,
                y = label2Y!!
            )
        }
    }
}

/*
class LineChartLayer(
    lineProvider: LineProvider,
    @SuppressLint("RestrictedApi") pointSpacingDp: Float = Defaults.POINT_SPACING,
    rangeProvider: CartesianLayerRangeProvider = CartesianLayerRangeProvider.auto(),
    verticalAxisPosition: Axis.Position.Vertical? = null,
    drawingModelInterpolator:
    CartesianLayerDrawingModelInterpolator<
            LineCartesianLayerDrawingModel.PointInfo,
            LineCartesianLayerDrawingModel,
            > =
        CartesianLayerDrawingModelInterpolator.default(),
    drawingModelKey: ExtraStore.Key<LineCartesianLayerDrawingModel>,
): LineCartesianLayer(
    lineProvider, pointSpacingDp, rangeProvider, verticalAxisPosition, drawingModelInterpolator, drawingModelKey
) {
    override fun CartesianDrawingContext.updateMarkerTargets(
        entry: LineCartesianLayerModel.Entry,
        canvasX: Float,
        canvasY: Float,
        lineFillBitmap: Bitmap,
    ) {
        if (canvasX <= layerBounds.left - 1 || canvasX >= layerBounds.right + 1) return
        val limitedCanvasY = canvasY.coerceIn(layerBounds.top, layerBounds.bottom)
        _markerTargets
            .getOrPut(entry.x) { listOf(MutableLineCartesianLayerMarkerTarget(entry.x, canvasX)) }
            .first()
            .points +=
            LineCartesianLayerMarkerTarget.Point(
                entry,
                limitedCanvasY,
                lineFillBitmap.getPixel(
                    canvasX
                        .roundToInt()
                        .coerceIn(ceil(layerBounds.left).toInt(), layerBounds.right.toInt() - 1),
                    limitedCanvasY.roundToInt(),
                ),
            )
    }
}*/