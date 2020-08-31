package com.mtjin.nomoneytrip.data.email_login.source

import com.google.firebase.database.DatabaseReference
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.utils.USER
import io.reactivex.Completable

class EmailLoginRepositoryImpl(private val database: DatabaseReference) : EmailLoginRepository {
    override fun insertUser(user: User): Completable {
        return Completable.create { emitter ->
            database.child(USER).child(user.id).setValue(user).addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }
    }

}