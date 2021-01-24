package com.mtjin.nomoneytrip.views.profile_edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mtjin.nomoneytrip.data.profile_edit.source.ProfileEditRepository
import com.mtjin.nomoneytrip.views.getOrAwaitValue
import io.reactivex.Completable
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class ProfileEditViewModelTest {
    @get:Rule
    var rule: MockitoRule = MockitoJUnit.rule()

    @JvmField
    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: ProfileEditRepository

    private lateinit var viewModel: ProfileEditViewModel

    @Before
    fun setUp() {
        viewModel = ProfileEditViewModel(repository)
    }

    @After
    fun tearDown() {
    }


    @Test
    fun update_profile_should_editSuccessMsg_value_set_true_if_name_not_blank_and_success_result() {
        val name = "JackJackE"
        viewModel.name.value = name
        Mockito.`when`(repository.updateName(name))
            .thenReturn(Completable.complete())
        viewModel.updateProfile()
        viewModel.editSuccessMsg.getOrAwaitValue()
        Assert.assertEquals(viewModel.editSuccessMsg.value, true)
    }

}