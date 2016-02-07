package com.eggman.circleciandroid.model

import android.os.Parcel
import android.os.Parcelable
import com.eggman.circleciandroid.extension.createParcel
import com.google.gson.annotations.SerializedName

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/5/16
 */
data class Build(val outcome:String,
                 val status:String,
                 @SerializedName("buildNum")
                 val buildNumber:Int,
                 val vcsRevision:String,
                 val pushedAt:String,
                 val addedAt:String) : Parcelable {

    companion object {
        @JvmField
        val CREATOR = createParcel { Build(it) }
    }

    protected constructor(parcelIn: Parcel) : this (
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readInt(),
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(outcome)
        dest.writeString(status)
        dest.writeInt(buildNumber)
        dest.writeString(vcsRevision)
        dest.writeString(pushedAt)
        dest.writeString(addedAt)
    }
}