package com.mtjin.nomoneytrip.data.alarm.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.alarm.Alarm
import com.mtjin.nomoneytrip.utils.ALARM
import com.mtjin.nomoneytrip.utils.uuid
import io.reactivex.Single

class AlarmRepositoryImpl(private val database: DatabaseReference) : AlarmRepository {
    override fun requestAlarms(): Single<List<Alarm>> {
        return Single.create<List<Alarm>> { emitter ->
            database.child(ALARM).child(uuid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val alarmList = ArrayList<Alarm>()
                        for (snapshot2 in snapshot.children) {
                            snapshot2.getValue(Alarm::class.java)?.let {
                                if(alarmList.isNotEmpty()){
                                    alarmList.add(alarmList.size-1, it)
                                }else{
                                    alarmList.add(it)
                                }
                            }
                        }
                        emitter.onSuccess(alarmList)
                    }

                })
        }
    }
}