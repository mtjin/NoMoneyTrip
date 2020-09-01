package com.mtjin.nomoneytrip.views.tour_write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.tour_write.source.TourWriteRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class TourWriteViewModel(private val tourWriteRepository: TourWriteRepository) : BaseViewModel() {

    private val _pickImage = SingleLiveEvent<Unit>()
    private val _contentEmptyMsg = SingleLiveEvent<String>()
    private val _content = MutableLiveData<String>()

    val pickImage: LiveData<Unit> get() = _pickImage
    val contentEmptyMsg: LiveData<String> get() = _contentEmptyMsg
    val content: LiveData<String> get() = _content

    fun pickImage() {
        _pickImage.call()
    }

    fun writeComplete() {
        if (_content.value.toString().isNullOrBlank()) {
            _contentEmptyMsg.call()
        } else {
            tourWriteRepository.insertReview()
        }
    }
}