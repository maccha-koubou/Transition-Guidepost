package com.maccha_koubou.transition_guidepost.model

import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class UserInfo(
    var userName: String? = null,
    var startDate: LocalDate? = null,
    var stopDuration: Int = 0
) {
    fun duration(): Int? {
        // Calculate user's GAHT duration if user's startDate is not a null
        return startDate?.let {
            (ChronoUnit.DAYS.between(startDate, LocalDate.now()) - stopDuration).toInt()
        }
    }
}