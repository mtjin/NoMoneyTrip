package com.mtjin.nomoneytrip.views.localpage

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtjin.nomoneytrip.data.community.Review
import com.mtjin.nomoneytrip.data.community.UserReview
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.local_page.TourIntroduce
import com.mtjin.nomoneytrip.data.local_page.source.LocalPageRepository
import com.mtjin.nomoneytrip.data.login.User
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
class LocalPageViewModelTest {
    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: LocalPageRepository

    private lateinit var viewModel: LocalPageViewModel

    @Before
    fun setUp() {
        viewModel = LocalPageViewModel(repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun request_reviews_then_user_review_list_should_set_if_it_null_or_empty() {
        val userReviews = ArrayList<UserReview>()
        viewModel.city = "city"
        Mockito.`when`(repository.requestReviews("city", 2))
            .thenReturn(Single.just(userReviews))
        viewModel.requestReviews()
        viewModel.userReviewList.getOrAwaitValue()
        Assert.assertNotNull(userReviews)
        Assert.assertEquals(viewModel.userReviewList.value?.size, 0)
        Assert.assertEquals(viewModel.userReviewList.value, userReviews)
    }

    @Test
    fun request_reviews_then_page_should_add_five() {
        val userReviews = ArrayList<UserReview>()
        viewModel.city = "city"
        val page = 2
        viewModel.page = page
        Mockito.`when`(repository.requestReviews("city", page))
            .thenReturn(Single.just(userReviews))
        viewModel.requestReviews()
        viewModel.userReviewList.getOrAwaitValue()
        Assert.assertEquals(viewModel.page, page + 5)
    }

    @Test
    fun request_reviews_then_user_review_list_should_not_set_if_last_review_id_is_same() {
        val userReviews = ArrayList<UserReview>()
        userReviews.add(UserReview(User(id = "1"), Review(id = "1"), Product()))
        userReviews.add(UserReview(User(id = "2"), Review(id = "2"), Product()))
        userReviews.add(UserReview(User(id = "3"), Review(id = "3"), Product()))
        userReviews.add(UserReview(User(id = "4"), Review(id = "4"), Product()))
        viewModel.city = "city"
        val page = 4
        viewModel.page = page
        Mockito.`when`(repository.requestReviews("city", page))
            .thenReturn(Single.just(userReviews))
        viewModel.requestReviews()
        viewModel.userReviewList.getOrAwaitValue()
        Assert.assertNotNull(userReviews)
        Assert.assertEquals(userReviews.size, viewModel.userReviewList.value?.size)
        Assert.assertEquals(userReviews, viewModel.userReviewList.value)
        Assert.assertEquals(userReviews.last().review, viewModel.lastUserReview.review)
        Assert.assertEquals(page + 5, viewModel.page)

        // 현재 리스트의 마지막데이터와 현재 준 데이터가 같다면 마지막 데이터이므로 리스트 데이터 갱신 X
        val prevReviewList = viewModel.userReviewList
        val prevLastUserReview = viewModel.lastUserReview
        Mockito.`when`(repository.requestReviews("city", viewModel.page))
            .thenReturn(Single.just(userReviews))
        viewModel.userReviewList.getOrAwaitValue()
        Assert.assertEquals(viewModel.userReviewList, prevReviewList)
        Assert.assertEquals(viewModel.lastUserReview, prevLastUserReview)
    }

    @Test
    fun request_tour_introduces_then_tour_introduce_list_should_set_if_success() {
        val areaCode = 1
        val tourList = ArrayList<TourIntroduce>()
        Mockito.`when`(repository.requestTourIntroduces(areaCode))
            .thenReturn(Single.just(tourList))
        viewModel.requestTourIntroduces(areaCode)
        viewModel.tourIntroduceList.getOrAwaitValue()
        Assert.assertEquals(tourList.size, viewModel.tourIntroduceList.value?.size)
    }

}