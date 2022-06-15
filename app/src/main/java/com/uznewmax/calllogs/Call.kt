package com.uznewmax.calllogs

import java.util.*

/**
 * Created by Alisher Kazakbaev on 15.06.2022.
 */
data class Call(
    val phone: String,
    val callType: String,
    val date: Date,
    val duration: String
)
