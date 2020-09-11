package com.mtjin.nomoneytrip.module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.mtjin.nomoneytrip.utils.REVIEW
import com.mtjin.nomoneytrip.utils.USER
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

val firebaseModule: Module = module {
    single<FirebaseAuth> { Firebase.auth }
    single<DatabaseReference> { Firebase.database.reference }
    single<StorageReference>(named(REVIEW)) { Firebase.storage.reference.child(REVIEW) }
    single<StorageReference>(named(USER)) { Firebase.storage.reference.child(USER) }
}