package com.mtjin.nomoneytrip.views.email_signup

import android.content.Context
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
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
class EmailSignUpActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(EmailSignUpActivity::class.java)
    private val appContext: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun password_confirm_edit_text_show_error_message_if_it_empty() {
        Espresso.onView(withId(R.id.et_email))
            .perform(ViewActions.typeText("JackJackE@github.com"), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.et_pw))
            .perform(ViewActions.typeText("111111"), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.iv_signup)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.et_pw_confirm))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText(appContext.getString(R.string.enter_password_confirm_text))))
    }
}