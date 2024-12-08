package com.maccha_koubou.transition_guidepost.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maccha_koubou.transition_guidepost.model.TestRecord
import com.maccha_koubou.transition_guidepost.storage.e2Data
import com.maccha_koubou.transition_guidepost.storage.tData
import com.maccha_koubou.transition_guidepost.ui.theme.AddButtonColors
import com.maccha_koubou.transition_guidepost.ui.theme.Blue
import com.maccha_koubou.transition_guidepost.ui.theme.IconButtonColors
import com.maccha_koubou.transition_guidepost.ui.theme.LightPurple
import com.maccha_koubou.transition_guidepost.ui.theme.Pink
import com.maccha_koubou.transition_guidepost.ui.theme.Purple
import com.maccha_koubou.transition_guidepost.ui.theme.Typography
import com.maccha_koubou.transition_guidepost.ui.theme.White
import com.maccha_koubou.transition_guidepost.ui.theme.cardColors
import com.maccha_koubou.transition_guidepost.ui.theme.largeMainButtonColors
import com.maccha_koubou.transition_guidepost.view.component.AddDataIconButton
import com.maccha_koubou.transition_guidepost.view.component.ChartListSwitcher
import com.maccha_koubou.transition_guidepost.view.component.MainButton
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberEnd
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import com.patrykandpatrick.vico.core.common.shape.CutCornerTreatment
import com.patrykandpatrick.vico.core.common.shape.Shape
import java.time.LocalDateTime


const val hormoneCardTitle = "我的激素水平"

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Preview
@Composable
fun HormoneCard() {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(12.dp),
        colors = cardColors
    ) {
        when (e2Data.dataList.isEmpty() && tData.dataList.isEmpty()) {
            true -> EmptyHormoneContent()
            else -> HormoneContent()
        }
    }
}

@Preview
@Composable
fun EmptyHormoneContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {

            // Title of the card
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp, 4.dp ,0.dp)
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = hormoneCardTitle,
                    style = Typography.titleLarge
                )
            }

            // Add data button & Description
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp, 0.dp, 16.dp ,16.dp)
                    .border(1.dp, LightPurple, RoundedCornerShape(12.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Text(
                    text = "添加数据后，\n您就可以在这里\n通过图表追踪激素数据",
                    style = Typography.bodySmall,
                    textAlign = TextAlign.Center
                )
                MainButton(false, "添加数据", Icons.Filled.Add) { /* Add data */ }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Preview
@Composable
fun HormoneContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {

            // Title of the card and icon buttons
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 4.dp, 4.dp ,0.dp)
                    .height(48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                //Title
                Row(
                    Modifier.weight(1f)
                ) {
                    Text(
                        text = hormoneCardTitle,
                        style = Typography.titleLarge
                    )
                }

                Row {
                    // Chart Setting button
                    IconButton(
                        onClick = { /* Chart Setting Screen */ },
                        modifier = Modifier.size(48.dp),
                        colors = IconButtonColors
                    ) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "图表设置"
                        )
                    }
                    // Add Data button
                    AddDataIconButton {
                        /* Add data menu */

                        /*
                            !!!!!!!!!!!!!!!!!!!!!
                            !!!!Only for test!!!!
                            !!!!!!!!!!!!!!!!!!!!!
                         */
                        e2Data.dataList.apply {
                            add(TestRecord(1092.98f, LocalDateTime.of(2024, 3, 16, 0, 0, 0)))
                            add(TestRecord(606.02f, LocalDateTime.of(2024, 7, 15, 0, 0, 0)))
                        }

                        tData.dataList.apply {
                            add(TestRecord(25.02f, LocalDateTime.now()))
                        }

                        /*
                            !!!!!!!!!!!!!!!!!!!!!
                            !!!!Only for test!!!!
                            !!!!!!!!!!!!!!!!!!!!!
                         */
                    }
                }
            }

            // Chart
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp, 0.dp, 16.dp ,12.dp)
            ) {
                HormoneChart()
            }
        }
    }
}
