package com.mtjin.nomoneytrip.data.profile.soruce

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.utils.USER
import com.mtjin.nomoneytrip.utils.uuid
import io.reactivex.Single

class ProfileRepositoryImpl(private val database: DatabaseReference) : ProfileRepository {
    override fun requestProfile(): Single<User> {
        return Single.create<User> { emitter ->
            database.child(USER).child(uuid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.getValue(User::class.java)?.let {
                            emitter.onSuccess(it)
                        }
                    }

                })
        }
    }
}
