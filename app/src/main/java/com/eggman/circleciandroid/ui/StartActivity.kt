package com.eggman.circleciandroid.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
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

    lateinit var etHome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(this, "loaded user! ", Toast.LENGTH_LONG).show()

        CircleApplication.graph.inject(this)

        setContentView(R.layout.activity_home)

        etHome = findViewById(R.id.act_home_et_welcome) as TextView

        etHome.setText("Welcome " + session.getUser()?.name)
    }
}