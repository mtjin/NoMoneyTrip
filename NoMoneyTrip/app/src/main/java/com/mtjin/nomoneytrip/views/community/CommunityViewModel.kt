package com.mtjin.nomoneytrip.views.community

import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.community.source.CommunityRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class CommunityViewModel(private val repository: CommunityRepository) : BaseViewModel() {

    private val _goReview = SingleLiveEvent<Unit>()

    val goReview: LiveData<Unit> get() = _goReview

    fun goReview() {

    }
}