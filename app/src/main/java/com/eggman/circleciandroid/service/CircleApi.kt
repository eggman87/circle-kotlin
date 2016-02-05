package com.eggman.circleciandroid.service

import com.eggman.circleciandroid.model.Project
import com.eggman.circleciandroid.model.User
import retrofit.http.GET
import rx.Observable

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/4/16
 */
interface CircleApi {


    @GET("/api/v1/me")
    fun getUser():Observable<User>

    @GET("/api/v1/projects")
    fun getProjects():Observable<List<Project>>
}