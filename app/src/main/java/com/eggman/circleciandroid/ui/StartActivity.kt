package com.eggman.circleciandroid.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast
import com.eggman.circleciandroid.CircleApplication
import com.eggman.circleciandroid.R
import com.eggman.circleciandroid.session.Session
import javax.inject.Inject

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/4/16
 */
class StartActivity : AppCompatActivity(){
    @Inject
    lateinit var session: Session

    lateinit var etHome: EditText

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        Toast.makeText(this, "loaded user! ", Toast.LENGTH_LONG).show()

        CircleApplication.graph.inject(this)

        setContentView(R.layout.activity_home)

        etHome = findViewById(R.id.act_home_et_welcome) as EditText

        etHome.setText("Welcome " + session.getUser()?.name)
    }
}