package com.eggman.circleciandroid.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by mharris on 2/4/16.
 * EggmanProjects.
 */
data class Project (val reponame:String,
                    val username:String,
                    val language:String,
                    val vcsUrl:String,
                    val branches:Map<String, Branch>) : Parcelable {

    companion object {
        val CREATOR = object : Parcelable.Creator<Project> {
            override fun createFromParcel(parcel: Parcel): Project {
                return Project(parcel)
            }

            override fun newArray(size: Int): Array<Project?> {
                return arrayOfNulls(size)
            }
        }
    }

    protected constructor(parcelIn: Parcel) : this (
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readString(),
            parcelIn.readString(),
            mapOf<String, Branch>().apply {
                parcelIn.readMap(this, Branch::class.java.classLoader)
            }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(reponame)
        dest.writeString(username)
        dest.writeString(language)
        dest.writeString(vcsUrl)
        dest.writeMap(branches)
    }
}