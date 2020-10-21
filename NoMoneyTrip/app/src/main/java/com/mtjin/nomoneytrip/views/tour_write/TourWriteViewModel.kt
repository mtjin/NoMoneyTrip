package com.mtjin.nomoneytrip.views.tour_write

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mtjin.nomoneytrip.base.BaseViewModel
import com.mtjin.nomoneytrip.data.community.Review
import com.mtjin.nomoneytrip.data.reservation_history.ReservationProduct
import com.mtjin.nomoneytrip.data.tour_write.source.TourWriteRepository
import com.mtjin.nomoneytrip.utils.SingleLiveEvent
import com.mtjin.nomoneytrip.utils.TAG
import com.mtjin.nomoneytrip.utils.getTimestamp
import com.mtjin.nomoneytrip.utils.uuid
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class TourWriteViewModel(private val tourWriteRepository: TourWriteRepository) : BaseViewModel() {
    lateinit var imageUri: Uri
    lateinit var reservationProduct: ReservationProduct
    var rating: Float = 0f
    private var isComplete: Boolean = true

    private val _pickImage = SingleLiveEvent<Unit>()
    private val _contentEmptyMsg = SingleLiveEvent<String>()
    private val _writeSuccess = SingleLiveEvent<Boolean>()
    private val _imageEmptyMsg = SingleLiveEvent<String>()
    val content: MutableLiveData<String> = MutableLiveData("")

    val pickImage: LiveData<Unit> get() = _pickImage
    val contentEmptyMsg: LiveData<String> get() = _contentEmptyMsg
    val writeSuccess: LiveData<Boolean> get() = _writeSuccess
    val imageEmptyMsg: LiveData<String> get() = _imageEmptyMsg

    fun pickImage() {
        _pickImage.call()
    }

    fun writeComplete() {
        when {
            content.value.isNullOrBlank() -> _contentEmptyMsg.call()
            !this::imageUri.isInitialized -> _imageEmptyMsg.call()
            else -> {
                if (!isComplete) {
                    return
                }
                reservationProduct.product.ratingList.add(rating)
                compositeDisposable.add(
                    tourWriteRepository.insertReview(
                        imageUri = imageUri,
                        reservationProduct = reservationProduct,
                        review = Review(
                            id = "",
                            userId = uuid,
                            productId = reservationProduct.product.id,
                            reservationId = reservationProduct.reservation.id,
                            timestamp = getTimestamp(),
                            image = "",
                            content = content.value.toString(),
                            recommendList = ArrayList<String>(),
                            city = reservationProduct.product.city
                        )
                    ).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe {
                            isComplete = false
                            showLottieProgress()
                        }
                        .doAfterTerminate {
                            isComplete = true
                            hideLottieProgress()
                            reservationProduct.product.ratingList.removeAt(reservationProduct.product.ratingList.size - 1)
                        }
                        .subscribeBy(
                            onError = {
                                Log.d(
                                    TAG,
                                    "TourWriteViewModel writeComplete() error -> $it"
                                )
                                _writeSuccess.value = false
                            },
                            onComplete = {
                                _writeSuccess.value = true
                            }
                        )
                )
            }
        }
    }
}