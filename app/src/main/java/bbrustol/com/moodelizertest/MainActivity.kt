package bbrustol.com.moodelizertest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar = supportActionBar
        actionBar!!.hide()
        setContentView(R.layout.activity_main)

        var listener = View.OnTouchListener(function = { view, motionEvent ->

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
            }

            if (motionEvent.action == MotionEvent.ACTION_UP) {
                tv_width.text = ""
                tv_height.text = ""
            }
            true
        })

        cursor_point.setOnTouchListener(listener)

    }
}
