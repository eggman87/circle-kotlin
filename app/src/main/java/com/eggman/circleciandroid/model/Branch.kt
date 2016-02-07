package com.eggman.circleciandroid.model

import android.os.Parcel
import android.os.Parcelable
import com.eggman.circleciandroid.extension.createParcel

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/5/16
 */
data class Branch(val pusherLogins:List<String>,
                  val lastNonSuccess:Build,
                  val lastSuccess:Build,
                  val recentBuilds:List<Build>) : Parcelable {

    companion object {
        val CREATOR = createParcel { Branch(it) }
    }

    protected constructor(parcelIn: Parcel) : this (
            listOf<String>().apply {
                parcelIn.readStringList(this)
            },
            parcelIn.readParcelable(Build::class.java.classLoader),
            parcelIn.readParcelable(Build::class.java.classLoader),
            listOf<Build>().apply {
                parcelIn.readTypedList(this, Build.CREATOR)
            }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeList(pusherLogins)
        dest.writeParcelable(lastNonSuccess, flags)
        dest.writeParcelable(lastSuccess, flags)
        dest.writeTypedList(recentBuilds)

    }
}