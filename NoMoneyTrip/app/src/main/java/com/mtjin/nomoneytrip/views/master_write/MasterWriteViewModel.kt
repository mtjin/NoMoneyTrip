package com.mtjin.nomoneytrip.views.master_write

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.master_main.MasterProduct
import com.mtjin.nomoneytrip.data.master_write.MasterLetter
import com.mtjin.nomoneytrip.data.master_write.source.MasterWriteRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import com.mtjin.nomoneytrip.utils.extensions.getTimestamp
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MasterWriteViewModel(private val repository: MasterWriteRepository) : BaseViewModel() {
    lateinit var masterProduct: MasterProduct
    val content: MutableLiveData<String> = MutableLiveData("")

    private val _contentEmptyMsg = SingleLiveEvent<String>()
    private val _writeSuccess = SingleLiveEvent<Boolean>()

    val contentEmptyMsg: LiveData<String> get() = _contentEmptyMsg
    val writeSuccess: LiveData<Boolean> get() = _writeSuccess

    fun insertMasterLetter() {
        if (content.value.isNullOrBlank()) {
            _contentEmptyMsg.call()
        } else {
            compositeDisposable.add(
                repository.insertMasterLetter(
                    MasterLetter(
                        id = Firebase.database.reference.push().key.toString(),
                        userId = masterProduct.user.id,
                        productId = masterProduct.reservation.productId,
                        title = "",
                        content = content.value.toString(),
                        timestamp = getTimestamp()
                    ), masterProduct
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe {
                        showLottieProgress()
                    }
                    .doAfterTerminate {
                        hideLottieProgress()
                    }.subscribeBy(
                        onComplete = {
                            _writeSuccess.value = true
                        },
                        onError = {
                            Log.d(TAG, it.toString())
                            _writeSuccess.value = false
                        }
                    )
            )
        }
    }
}