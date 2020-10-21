package com.mtjin.nomoneytrip.views.email_login

import android.content.Context
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.mtjin.nomoneytrip.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailLoginActivityTest {
    @get:Rule
    val activityRule = ActivityTestRule(EmailLoginActivity::class.java)
    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun show_error_message_on_email_edit_text_if_email_edit_text_is_empty() {
        Espresso.onView(ViewMatchers.withId(R.id.iv_login)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.et_email))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText(appContext.getString(R.string.enter_email_text))))
    }

    @Test
    fun show_error_message_on_password_edit_text_if_password_edit_text_is_empty() {
        Espresso.onView(ViewMatchers.withId(R.id.et_email))
            .perform(ViewActions.typeText("JackJackE@github.com"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.iv_login)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.et_pw))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText(appContext.getString(R.string.enter_password_text))))
    }
}