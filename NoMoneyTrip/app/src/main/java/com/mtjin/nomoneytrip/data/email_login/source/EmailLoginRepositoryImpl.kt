package com.mtjin.nomoneytrip.data.email_login.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.master_write.MasterLetter
import com.mtjin.nomoneytrip.utils.*
import com.mtjin.nomoneytrip.utils.extensions.getTimestamp
import io.reactivex.Completable

class EmailLoginRepositoryImpl(private val database: DatabaseReference) : EmailLoginRepository {
    override fun insertUser(user: User): Completable {
        return Completable.create { emitter ->
            database.child(USER).child(uuid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (!snapshot.exists()) {
                            database.child(USER).child(user.id).setValue(user)
                                .addOnSuccessListener {
                                    val signUpTimestamp =
                                        Firebase.database.reference.push().key.toString()
                                    database.child(MASTER_LETTER).child(uuid).child(signUpTimestamp)
                                        .setValue(
                                            MasterLetter(
                                                id = signUpTimestamp,
                                                title = "무전일기 이장",
                                                timestamp = getTimestamp(),
                                                content = "반갑습니다! 안전하고 멋진\n" +
                                                        "무전여행을 기대할게요 :)"
                                            )
                                        ).addOnSuccessListener {
                                            emitter.onComplete()
                                        }.addOnFailureListener {
                                            emitter.onError(it)
                                        }
                                }.addOnFailureListener {
                                    emitter.onError(it)
                                }
                        } else {
                            emitter.onComplete()
                        }
                    }

                })

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
                        if (fcm.isNotBlank()) {
                            map[FCM] = fcm
                            database.child(USER).child(uuid)
                                .updateChildren(map).addOnSuccessListener {
                                    emitter.onComplete()
                                }.addOnFailureListener {
                                    emitter.onError(it)
                                }
                        } else {
                            emitter.onComplete()
                        }
                    }
                })
        }
    }


}