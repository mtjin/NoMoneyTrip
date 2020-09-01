package com.mtjin.nomoneytrip.data.login.source.remote

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.kakao.auth.Session
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Completable

class LoginRemoteDataSourceImpl(private val database: DatabaseReference) : LoginRemoteDataSource {
    override fun kakaoLogin(): Session = Session.getCurrentSession()
    override fun insertUser(user: User): Completable {
        return Completable.create { emitter ->
            database.child(USER).child(user.id).setValue(user).addOnSuccessListener {
                emitter.onComplete()
            }.addOnFailureListener {
                emitter.onError(it)
            }
        }

    }

    override fun updateFCM(): Completable {
        return Completable.create { emitter ->
            database.child(USER)
                .orderByChild(ID)
                .equalTo(uuid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val map = HashMap<String, Any>()
                        map[FCM] = fcm
                        database.child(USER).child(uuid)
                            .updateChildren(map).addOnSuccessListener {
                                emitter.onComplete()
                            }.addOnFailureListener {
                                emitter.onError(it)
                            }
                    }
                })
        }
    }
}