package bbrustol.com.moodelizertest

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.MotionEvents
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.filters.MediumTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import bbrustol.com.moodelizertest.presentation.main.MainActivity
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class MainTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun alerDialogCancelandOk() {
        onView(withId(R.id.cursor_point)).perform(touchDownAndUp(0F, 300F))

        onView(withId(R.id.cursor_point)).perform(touchDownAndUp(300F, 600F))
    }

    fun touchDownAndUp(x: Float, y: Float): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return isDisplayed()
            }

            override fun getDescription(): String {
                return "Send touch events."
            }

            override fun perform(uiController: UiController, view: View) {
                // Get view absolute position
                val location = IntArray(2)
                view.getLocationOnScreen(location)

                // Offset coordinates by view position
                val coordinates = floatArrayOf(x + location[0], y + location[1])
                val precision = floatArrayOf(1f, 1f)

                // Send down event, pause, and send up
                val down = MotionEvents.sendDown(uiController, coordinates, precision).down
                uiController.loopMainThreadForAtLeast(200)
                MotionEvents.sendUp(uiController, down, coordinates)
            }
        }
    }
}

