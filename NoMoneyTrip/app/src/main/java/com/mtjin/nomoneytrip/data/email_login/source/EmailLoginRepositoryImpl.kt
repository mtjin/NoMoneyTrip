package com.mtjin.nomoneytrip.data.email_login.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.utils.USER
import com.mtjin.nomoneytrip.utils.uuid
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