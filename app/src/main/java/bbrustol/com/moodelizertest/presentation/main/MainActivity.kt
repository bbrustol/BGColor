package bbrustol.com.moodelizertest.presentation.main

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import bbrustol.com.moodelizertest.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.ViewContract {

    private lateinit var mPresenter: MainPresenter

    private var onTouchListener = View.OnTouchListener(function = { view, motionEvent ->
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                mPresenter.onActionDown(motionEvent, view)
            }

            MotionEvent.ACTION_MOVE -> {
                view.x = motionEvent.rawX - view.width / 2
                view.y = motionEvent.rawY - view.height

                line_width_cursor.x = view.x - (line_width_cursor.width/2) + (view.width/2)
                line_width_cursor.y = view.y - (line_width_cursor.height/2) + (view.height/2)
                line_height_cursor.x = view.x - (line_height_cursor.width/2) + (view.width/2)
                line_height_cursor.y = view.y - (line_height_cursor.height/2) + (view.height/2)

                mPresenter.calcBGColor(view)
            }
        }
        true
    })

    private fun setup() {
        val actionBar = supportActionBar
        actionBar!!.hide()
        setContentView(R.layout.activity_main)

        mPresenter = MainPresenter()
        mPresenter.setup(this)

        cursor_point.setOnTouchListener(onTouchListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    override fun setBG (bg: HashMap<String, Int>) {
        val gd = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(
                Color.rgb(bg["rs"]!!, bg["gs"]!!, bg["bs"]!!),
                Color.rgb(bg["re"]!!, bg["ge"]!!, bg["be"]!!)
            )
        )

        ViewCompat.setBackground(img_bg, gd)
    }
}
