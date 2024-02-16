package com.example.photogallery


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DatabaseViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_view)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, DatabaseViewFragment.newInstance())
                .commit()
        }
    }


}