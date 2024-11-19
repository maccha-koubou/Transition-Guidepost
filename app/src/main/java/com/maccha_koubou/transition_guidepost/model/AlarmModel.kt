package com.maccha_koubou.transition_guidepost.model

import java.time.DayOfWeek

interface AlarmCycle {
    val x: Int
    val cycle: CycleType
}

class EveryXMonths(
    override val x: Int = 1,
    override val cycle: CycleType = CycleType.MONTHLY
): AlarmCycle {

}

class EveryXWeeks(
    override val x: Int = 1,
    val dayOfWeek: List<DayOfWeek>,
    override val cycle: CycleType = CycleType.WEEKLY
): AlarmCycle {

}

class EveryXDays(
    override val x: Int = 1,
    override val cycle: CycleType = CycleType.DAILY
): AlarmCycle {

}

enum class CycleType {
    MONTHLY,
    WEEKLY,
    DAILY
}