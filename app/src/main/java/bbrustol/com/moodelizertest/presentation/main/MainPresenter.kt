package bbrustol.com.moodelizertest.presentation.main

import android.view.MotionEvent
import android.view.View

class MainPresenter : MainContract.Presenter {
    private lateinit var mView: MainContract.ViewContract

    private var posXInit: Float = 0F
    private var posYInit: Float = 0F

    private var firstTap = false
    private var posXViewInit: Float = 0F
    private var posYViewInit: Float = 0F

    companion object {
        private const val MIN_COLOR: Float = 0F
        private const val MAX_COLOR: Float = 255F
    }

    private fun setGradient(redStart: Int, greenStart: Int, blueStart: Int, redEnd: Int, greenEnd: Int, blueEnd: Int) {
        val gradient = HashMap<String, Int>()
        gradient["rs"] = redStart
        gradient["gs"] = greenStart
        gradient["bs"] = blueStart
        gradient["re"] = redEnd
        gradient["ge"] = greenEnd
        gradient["be"] = blueEnd

        mView.setBG(gradient)
    }

    override fun onActionDown(motionEvent: MotionEvent, view: View) {
        posXViewInit = motionEvent.rawX - view.width / 2
        posYViewInit = motionEvent.rawY - view.height

        if (!firstTap) {
            firstTap = true
            posXInit = motionEvent.rawX
            posYInit = motionEvent.rawY - view.height
        }
    }

    override fun onActionMove(motionEvent: MotionEvent, view: View) {

    }

    override fun calcBGColor(view: View) {
        val colorXPercent: Float = ((view.x / posXInit) / 2)
        val colorYPercent: Float = ((((view.y / posYInit) / 2) * -1) + 1)

        var redStart = (colorXPercent * 1) * MAX_COLOR
        var greenStart = ((colorXPercent * colorYPercent) / 1) * MAX_COLOR
        var blueStart = (colorYPercent * 1) * MAX_COLOR

        var redEnd = ((colorXPercent * 1) * MAX_COLOR) * -1 + MAX_COLOR
        var greenEnd = (((colorXPercent * colorYPercent) / 1) * MAX_COLOR) * -1 + MAX_COLOR
        var blueEnd = ((colorYPercent * 1) * MAX_COLOR) * -1 + MAX_COLOR

        if (redStart >= 255) {
            redStart = MAX_COLOR
        }else if (redStart <= 0) {
            redStart = MIN_COLOR
        }

        if (greenStart >= 255) {
            greenStart = MAX_COLOR
        }else if (greenStart <= 0) {
            greenStart = MIN_COLOR
        }

        if (blueStart >= 255) {
            blueStart = MAX_COLOR
        }else if (blueStart <= 0) {
            blueStart = MIN_COLOR
        }

        if (redEnd >= 255) {
            redEnd = MAX_COLOR
        }else if (redEnd <= 0) {
            redEnd = MIN_COLOR
        }

        if (greenEnd >= 255) {
            greenEnd = MAX_COLOR
        }else if (greenEnd <= 0) {
            greenEnd = MIN_COLOR
        }

        if (blueEnd >= 255) {
            blueEnd = MAX_COLOR
        }else if (blueEnd <= 0) {
            blueEnd = MIN_COLOR
        }

        setGradient(redStart.toInt(), greenStart.toInt(), blueStart.toInt(),
                    redEnd.toInt() , greenEnd.toInt(), blueEnd.toInt())
    }

    override fun setup(activity: MainActivity) {
        mView = activity
        setGradient(100, 50, 127,
                     156, 206, 128)
    }
}