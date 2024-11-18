package com.maccha_koubou.transition_guidepost.model

import java.time.DayOfWeek

open class AlarmCycle {

}

class EveryXMonths(val x: Int = 1, val cycle: CycleType = CycleType.MONTHLY): AlarmCycle() {

}

class EveryXWeeks(val x: Int = 1, val dayOfWeek: List<DayOfWeek>, val cycle: CycleType = CycleType.WEEKLY): AlarmCycle() {

}

class EveryXDays(val x: Int = 1, val cycle: CycleType = CycleType.DAILY): AlarmCycle() {

}

enum class CycleType {
    MONTHLY,
    WEEKLY,
    DAILY
}