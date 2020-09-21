package com.mtjin.nomoneytrip.data.master_login.source

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.master_login.MasterUser
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Completable

class MasterLoginRepositoryImpl(private val database: DatabaseReference) : MasterLoginRepository {
    override fun requestMasterLogin(id: String, pw: String): Completable {
        return Completable.create { emitter ->
            Log.d("AAAAAA", "????")
            database.child(MASTER_USER).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.d("AAAAAA", "000000")
                    emitter.onError(error.toException())
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild(id)) {
                        Log.d("AAAAAA", "AAAAA1111")
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
                                emitter.onComplete()
                                return
                            }
                        }
                        emitter.onError(Throwable("오류"))
                    } else {
                        Log.d("AAAAAA", "3333333")
                        emitter.onError(Throwable("없는 아이디"))
                    }
                }

            })
        }

    }

}