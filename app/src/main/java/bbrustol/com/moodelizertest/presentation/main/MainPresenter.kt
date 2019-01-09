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

    private fun verifyMinMaxColor(num: Float): Float {
        if (num >= 255) {
            return MAX_COLOR
        } else if (num <= 0) {
            return MIN_COLOR
        }
        return num
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

    override fun calcBGColor(view: View) {
        val colorXPercent: Float = ((view.x / posXInit) / 2)
        val colorYPercent: Float = ((((view.y / posYInit) / 2) * -1) + 1)

        var redStart = (colorXPercent * 1) * MAX_COLOR
        var greenStart = ((colorXPercent * colorYPercent) / 1) * MAX_COLOR
        var blueStart = (colorYPercent * 1) * MAX_COLOR

        var redEnd = ((colorXPercent * 1) * MAX_COLOR) * -1 + MAX_COLOR
        var greenEnd = (((colorXPercent * colorYPercent) / 1) * MAX_COLOR) * -1 + MAX_COLOR
        var blueEnd = ((colorYPercent * 1) * MAX_COLOR) * -1 + MAX_COLOR

        redStart = verifyMinMaxColor(redStart)
        greenStart = verifyMinMaxColor(greenStart)
        blueStart = verifyMinMaxColor(blueStart)
        redEnd = verifyMinMaxColor(redEnd)
        greenEnd = verifyMinMaxColor(greenEnd)
        blueEnd = verifyMinMaxColor(blueEnd)

        setGradient(redStart.toInt(), greenStart.toInt(), blueStart.toInt(),
                    redEnd.toInt() , greenEnd.toInt(), blueEnd.toInt())
    }

    override fun setup(activity: MainActivity) {
        mView = activity
        setGradient(100, 50, 127,
                     156, 206, 128)
    }
}