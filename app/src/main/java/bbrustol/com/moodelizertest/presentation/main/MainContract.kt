package bbrustol.com.moodelizertest.presentation.main

import android.view.MotionEvent
import android.view.View

interface MainContract {
    interface ViewContract {
        fun setBG (bg: HashMap<String, Int>)
    }

    interface Presenter {
        fun onActionDown(motionEvent: MotionEvent, view: View)
        fun calcBGColor(view: View)
        fun setup(activity: MainActivity)
    }
}