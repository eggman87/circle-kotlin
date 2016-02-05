package com.eggman.circleciandroid.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/5/16
 */
data class Build(val outcome:String, val status:String, @SerializedName("buildNum") val buildNumber:Int,
                 val vcsRevision:String, val pushedAt:Calendar, val addedAt:Calendar) {

}