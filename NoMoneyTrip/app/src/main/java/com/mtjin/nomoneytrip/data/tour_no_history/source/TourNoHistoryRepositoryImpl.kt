package com.mtjin.nomoneytrip.data.tour_no_history.source

import com.google.firebase.database.DatabaseReference

class TourNoHistoryRepositoryImpl(private val database: DatabaseReference) :
    TourNoHistoryRepository