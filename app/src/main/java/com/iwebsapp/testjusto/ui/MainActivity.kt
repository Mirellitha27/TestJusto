package com.iwebsapp.testjusto.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.iwebsapp.testjusto.R
import com.iwebsapp.testjusto.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}