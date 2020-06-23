package com.mtjin.nomoneytrip.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object Fb {
    var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
}