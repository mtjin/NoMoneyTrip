package com.mtjin.nomoneytrip.data.local_page.source

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.mtjin.nomoneytrip.R
import com.mtjin.nomoneytrip.api.ApiInterface
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class LocalPageRepositoryTest {
    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var repository: LocalPageRepository

    @Mock
    lateinit var apiInterface: ApiInterface

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Firebase.initialize(appContext)
        repository = LocalPageRepositoryImpl(apiInterface, Firebase.database.reference)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun check_request_products() {
        repository.requestProducts(city = appContext.getString(R.string.choongnam_text))
            .test()
            .awaitDone(2000, TimeUnit.MILLISECONDS)
            .assertOf {
                it.assertSubscribed()
                it.assertNoErrors()
                it.assertComplete()
                Log.d("LocalPageRepositoryTest", it.values()[0].toString())
            }
    }
}