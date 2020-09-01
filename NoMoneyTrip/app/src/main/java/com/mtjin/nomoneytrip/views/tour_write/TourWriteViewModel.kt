package com.mtjin.nomoneytrip.views.tour_write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.tour_write.source.TourWriteRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class TourWriteViewModel(private val tourWriteRepository: TourWriteRepository) : BaseViewModel() {

    private val _pickImage = SingleLiveEvent<Unit>()
    private val _contentEmptyMsg = SingleLiveEvent<String>()
    val content: MutableLiveData<String> = MutableLiveData("")

    val pickImage: LiveData<Unit> get() = _pickImage
    val contentEmptyMsg: LiveData<String> get() = _contentEmptyMsg

    fun pickImage() {
        _pickImage.call()
    }

    fun writeComplete() {
        if (content.value.isNullOrBlank()) {
            _contentEmptyMsg.call()
        } else {
            tourWriteRepository.insertReview()
        }
    }
}