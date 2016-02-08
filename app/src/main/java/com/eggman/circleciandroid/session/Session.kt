package com.eggman.circleciandroid.session

import com.eggman.circleciandroid.model.User

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/4/16
 */
interface Session {

    fun getCircleToken():String?

    fun setCircleToken(circleToken:String?)

    fun onLifecyclePause()

    fun setUser(user:User)

    fun getUser():User?

}