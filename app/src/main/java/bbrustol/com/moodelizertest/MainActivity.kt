package bbrustol.com.moodelizertest

import android.graphics.drawable.GradientDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val MIN_VOLUME = 0
    private val MAX_VOLUME = 100
    private lateinit var mPlayer: MediaPlayer
    private var posXInit: Float = 0F
    private var posYInit: Float = 0F

    private var firstTap = false
    private var posXViewInit: Float = 0F
    private var posYViewInit: Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar!!.hide()
        setContentView(R.layout.activity_main)

        var listener = View.OnTouchListener(function = { view, motionEvent ->

            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                posXViewInit = motionEvent.rawX - view.width/2
                posYViewInit  = motionEvent.rawY - view.height

                posXInit = motionEvent.rawX

                if (!firstTap) {
                    firstTap = true
                    posYInit = motionEvent.rawY - view.height
                }
            }
            if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                view.x = motionEvent.rawX - view.width/2
                view.y = motionEvent.rawY - view.height
                line_width_cursor.x = view.x - (line_width_cursor.width/2) + (view.width/2)
                line_width_cursor.y = view.y - (line_width_cursor.height/2) + (view.height/2)
                line_height_cursor.x = view.x - (line_height_cursor.width/2) + (view.width/2)
                line_height_cursor.y = view.y - (line_height_cursor.height/2) + (view.height/2)

                tv_width.x = view.x + tv_width.width/2
                tv_width.y = view.y - 15
                tv_width.text = view.x.toString()

                tv_height.x = view.x - tv_height.width
                tv_height.y = view.y + 25
                tv_height.text = view.y.toString()

                val radian = Math.atan2((view.y - posYViewInit).toDouble(), (view.x - posXViewInit).toDouble())
                val degree = (radian * (180 / Math.PI) * -1) + 90

                img_bg.rotation = degree.toFloat()

                img_bg.x = view.x - (img_bg.height/2) + (view.height/2)
                img_bg.y = view.y - (img_bg.height/2) + (view.height/2)

                var percentage: Float = ((view.y / posYInit)/2) * 100
                when {
                    percentage <= MIN_VOLUME -> percentage = 0F
                    percentage >= MAX_VOLUME -> percentage = 100F
                }

                val volume = (1 - Math.log((MAX_VOLUME - percentage).toDouble()) / Math.log(MAX_VOLUME.toDouble())).toFloat()
                mPlayer.setVolume(volume, volume)
            }

            if (motionEvent.action == MotionEvent.ACTION_UP) {
                tv_width.text = ""
                tv_height.text = ""
            }
            true
        })

        cursor_point.setOnTouchListener(listener)

        val gd = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(-0x9e9d9f, -0xececed)
        )

        gd.cornerRadius = 0f

        ViewCompat.setBackground(img_bg, gd)

        mPlayer = MediaPlayer.create(this, R.raw.thunderstruck)
        mPlayer.start()
        mPlayer.seekTo(100)

//        val timer = Timer()
//        val mt = MyTimer()
//        timer.schedule(mt, 1000, 1000)
    }


}
