package com.srijith.swipeablescreendemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_landing_screen.*

class LandingScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_screen)

        button.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
    }
}
