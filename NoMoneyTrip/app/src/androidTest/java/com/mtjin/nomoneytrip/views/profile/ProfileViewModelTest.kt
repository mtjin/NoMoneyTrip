package com.mtjin.nomoneytrip.views.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtjin.nomoneytrip.data.community.Review
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.profile.soruce.ProfileRepository
import com.mtjin.nomoneytrip.views.getOrAwaitValue
import io.reactivex.Single
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest {
    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: ProfileRepository

    private lateinit var viewModel: ProfileViewModel

    private var userReviews = ArrayList<UserReview>()

    @Before
    fun setUp() {
        userReviews.add(UserReview(User(id = "1"), Review(), Product()))
        userReviews.add(UserReview(User(id = "2"), Review(), Product()))
        userReviews.add(UserReview(User(id = "3"), Review(), Product()))
        Mockito.`when`(repository.requestMyReviews())
            .thenReturn(Single.just(userReviews))
        viewModel = ProfileViewModel(repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun requestMyReviewsShouldUserReviewListLiveDataSetIfSuccess() {
        viewModel.requestMyReviews()
        viewModel.userReviewList.getOrAwaitValue()
        Assert.assertEquals(viewModel.userReviewList.value, userReviews)
    }

}