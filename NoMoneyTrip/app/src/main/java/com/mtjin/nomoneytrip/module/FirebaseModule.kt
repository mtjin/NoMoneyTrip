package com.mtjin.nomoneytrip.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.core.module.Module
import org.koin.dsl.module

val firebaseModule: Module = module {
    single<FirebaseAuth> { Firebase.auth }
}