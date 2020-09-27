package com.mtjin.nomoneytrip.views.master_write

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.utils.SingleLiveEvent

class MasterWriteViewModel : BaseViewModel(){
    val content: MutableLiveData<String> = MutableLiveData("")

    private val _contentEmptyMsg = SingleLiveEvent<String>()
    private val _writeSuccess = SingleLiveEvent<Boolean>()

    val contentEmptyMsg: LiveData<String> get() = _contentEmptyMsg
    val writeSuccess: LiveData<Boolean> get() = _writeSuccess
}