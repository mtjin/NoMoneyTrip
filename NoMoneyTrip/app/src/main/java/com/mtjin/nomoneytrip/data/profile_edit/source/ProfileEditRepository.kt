package com.mtjin.nomoneytrip.data.profile_edit.source

import android.net.Uri
import io.reactivex.Completable

interface ProfileEditRepository {
    fun updateProfile(name: String, imageUri: Uri): Completable
    fun updateName(name: String): Completable
}