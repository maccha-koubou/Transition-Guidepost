package com.maccha_koubou.transition_guidepost.model

import java.time.LocalDate

data class UserInfo(
    var userName: String? = null,
    var startDate: LocalDate? = null,
    var stopDuration: Int = 0
)