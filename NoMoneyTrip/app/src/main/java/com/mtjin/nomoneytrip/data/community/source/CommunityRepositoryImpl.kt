package com.mtjin.nomoneytrip.data.community.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.reservation.Reservation
import com.mtjin.nomoneytrip.utils.RESERVATION
import com.mtjin.nomoneytrip.utils.USER_ID
import com.mtjin.nomoneytrip.utils.uuid
import io.reactivex.Single

class CommunityRepositoryImpl(private val database: DatabaseReference) : CommunityRepository {
    override fun requestMyReservations(): Single<List<Reservation>> {
        return Single.create<List<Reservation>> { emitter ->
            database.child(RESERVATION).orderByChild(USER_ID).equalTo(uuid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val resList = ArrayList<Reservation>()
                        for (snapshot2 in snapshot.children) {
                            snapshot2.getValue(Reservation::class.java)?.let {
                                resList.add(it)
                            }
                        }
                        emitter.onSuccess(resList)
                    }
                })
        }
    }
}