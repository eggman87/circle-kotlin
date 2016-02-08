package com.eggman.circleciandroid.session

import android.content.Context
import android.preference.PreferenceManager
import com.eggman.circleciandroid.model.User
import com.google.gson.Gson

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/4/16
 */
class JsonSharedPreferencesSesson(val context:Context) : Session {

    lateinit var session:SessionState
    var needsToSave:Boolean = false

    init {
        restoreStateFromPersistence()
    }

    companion object {
        const val KEY_SESSION = "session_state"
    }

    override fun getCircleToken(): String? {
        return session.circleToken
    }

    override fun setCircleToken(circleToken: String?) {
        session.circleToken = circleToken
        needsToSave = true
    }

    override fun onLifecyclePause() {
        if (needsToSave) {
            saveStateFromPersistence()
            needsToSave = false
        }
    }

    override fun setUser(user: User) {
        session.user = user
        needsToSave = true
    }

    override fun getUser(): User? {
        return session.user
    }

    private fun restoreStateFromPersistence() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val json = prefs.getString(KEY_SESSION, "")

        try {
            session = Gson().fromJson(json, SessionState::class.java)
        } catch(e:Exception) {
            session = SessionState()
        }

    }

    private fun saveStateFromPersistence() {
        val json = Gson().toJson(session)

        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putString(KEY_SESSION, json).apply()
    }

}