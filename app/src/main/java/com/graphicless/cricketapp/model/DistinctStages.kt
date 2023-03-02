package com.graphicless.cricketapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DistinctStages(
    val stageId: Int,
    val stageName: String,
    val startingAt: String
):Parcelable
