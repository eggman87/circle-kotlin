package com.eggman.circleciandroid.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

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
                 val pushedAt:Calendar,
                 val addedAt:Calendar) : Parcelable {

    companion object {
        val CREATOR = object : Parcelable.Creator<Build> {
            override fun createFromParcel(parcel: Parcel): Build {
                return Build(parcel)
            }

            override fun newArray(size: Int): Array<Build?> {
                return arrayOfNulls(size)
            }
        }
    }

    protected constructor(parcelIn: Parcel) : this (
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readInt(),
            parcelIn.readString(),
            parcelIn.readSerializable() as Calendar,
            parcelIn.readSerializable() as Calendar
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(outcome)
        dest.writeString(status)
        dest.writeInt(buildNumber)
        dest.writeString(vcsRevision)
        dest.writeSerializable(pushedAt)
        dest.writeSerializable(addedAt)
    }
}