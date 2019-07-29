package io.mapwize.mapwizeuicomponents

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.activity_my_splash.*
import java.util.*

class MySplash : AppCompatActivity() {
    private var timer: Timer? = null
    private var progressBar: ProgressBar? = null
    private var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_splash)

        //For Full Screen View
        this.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        progressBar = findViewById<View>(R.id.ProgressBar) as ProgressBar
        progressBar!!.setProgress(0)

        val period: Long = 100
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                //this repeats every 100 ms
                if (i < 100) {
                    runOnUiThread { }
                    progressBar!!.setProgress(i)
                    i += 5
                } else {
                    //closing the timer
                    timer!!.cancel()
                    val intent = Intent(applicationContext, Main2Activity::class.java)
                    startActivity(intent)
                    // close this activity
                    finish()
                }
            }
        }, 0, period)
    }
}
