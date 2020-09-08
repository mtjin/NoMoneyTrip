package com.mtjin.nomoneytrip.data.reservation_history.source

import com.google.firebase.database.DatabaseReference

class ReservationHistoryRepositoryImpl(private val database: DatabaseReference) :
    ReservationHistoryRepository