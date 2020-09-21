package com.mtjin.nomoneytrip.data.master_main

import com.mtjin.nomoneytrip.data.login.User
import com.mtjin.nomoneytrip.data.reservation.Reservation

data class MasterProduct(
    var reservation: Reservation = Reservation(),
    var user: User = User()
)