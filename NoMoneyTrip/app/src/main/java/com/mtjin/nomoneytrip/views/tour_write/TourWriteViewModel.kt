package com.mtjin.nomoneytrip.views.tour_write

import androidx.lifecycle.LiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.tour_write.source.TourWriteRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class TourWriteViewModel(private val tourWriteRepository: TourWriteRepository) : BaseViewModel() {

    private val _pickImage = SingleLiveEvent<Unit>()

    val pickImage: LiveData<Unit> get() = _pickImage

    fun pickImage() {
        _pickImage.call()
    }
}