package com.mtjin.nomoneytrip.data.phone.source

import com.google.firebase.database.DatabaseReference
import com.mtjin.nomoneytrip.utils.TEL
import com.mtjin.nomoneytrip.utils.USER
import com.mtjin.nomoneytrip.utils.uuid
import io.reactivex.Completable

class PhoneAuthRepositoryImpl(private val database: DatabaseReference) : PhoneAuthRepository {
    override fun updateUserTel(tel: String): Completable {
        return Completable.create { emitter ->
            val map = HashMap<String, Any>()
            map[TEL] = "0" + tel
            database.child(USER).child(uuid).updateChildren(map).addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }

    }
}