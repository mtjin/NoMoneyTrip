package com.mtjin.nomoneytrip.data.master_login.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.master_login.MasterUser
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Completable

class MasterLoginRepositoryImpl(
    private val database: DatabaseReference,
    private val preferenceManager: PreferenceManager
) : MasterLoginRepository {
    override var masterIdInput: String
        get() = preferenceManager.masterIdInput
        set(value) {
            preferenceManager.masterIdInput = value
        }

    override var masterPwInput: String
        get() = preferenceManager.masterPwInput
        set(value) {
            preferenceManager.masterPwInput = value
        }

    override var masterProductIdPref: String
        get() = preferenceManager.masterPwInput
        set(value) {
            preferenceManager.masterPwInput = value
        }

    override fun requestMasterLogin(id: String, pw: String): Completable {
        return Completable.create { emitter ->
            database.child(MASTER_USER).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    emitter.onError(error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild(id)) {
                        snapshot.child(id).getValue(MasterUser::class.java)?.let {
                            if (it.pw != pw) {
                                emitter.onError(Throwable("비번 불일치"))
                                return
                            } else {
                                masterProductId = it.productId
                                val fcmMap = HashMap<String, Any>()
                                fcmMap[FCM] = fcm
                                database.child(PRODUCT).child(masterProductId)
                                    .updateChildren(fcmMap)
                                database.child(MASTER_USER).child(id).updateChildren(fcmMap)
                                masterIdInput = id
                                masterPwInput = pw
                                emitter.onComplete()
                                return
                            }
                        }
                        emitter.onError(Throwable("오류"))
                    } else {
                        emitter.onError(Throwable("없는 아이디"))
                    }
                }

            })
        }
    }

    override fun updateFCM() {
        database.child(MASTER_USER).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (masterIdInput.isBlank() || masterPwInput.isBlank()) {
                    return
                }
                if (snapshot.hasChild(masterIdInput)) {
                    snapshot.child(masterIdInput).getValue(MasterUser::class.java)?.let {
                        if (it.pw != masterPwInput) {
                            return
                        } else {
                            masterProductId = it.productId
                            masterProductIdPref = it.productId
                            val fcmMap = HashMap<String, Any>()
                            fcmMap[FCM] = fcm
                            database.child(PRODUCT).child(masterProductId)
                                .updateChildren(fcmMap)
                            database.child(MASTER_USER).child(masterIdInput).updateChildren(fcmMap)
                        }
                    }
                }
            }

        })
    }
}