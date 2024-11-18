package com.maccha_koubou.transition_guidepost.service

import com.maccha_koubou.transition_guidepost.model.UserInfo
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun UserInfo.duration(): Int? {
    //Calculate user's GAHT duration if user's startDate is not a null
    return startDate?.let{
        (ChronoUnit.DAYS.between(startDate, LocalDate.now()) - stopDuration).toInt()
    }
}