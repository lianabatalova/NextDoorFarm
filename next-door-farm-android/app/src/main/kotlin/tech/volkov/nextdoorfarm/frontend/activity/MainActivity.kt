package tech.volkov.nextdoorfarm.frontend.activity

import android.os.Bundle
import moxy.MvpAppCompatActivity
import tech.volkov.nextdoorfarm.R

class MainActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
