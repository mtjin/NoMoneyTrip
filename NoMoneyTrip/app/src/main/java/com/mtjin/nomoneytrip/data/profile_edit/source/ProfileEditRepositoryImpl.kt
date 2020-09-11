package com.mtjin.nomoneytrip.data.profile_edit.source

import android.net.Uri
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.mtjin.nomoneytrip.utils.*
import io.reactivex.Completable

class ProfileEditRepositoryImpl(
    private val database: DatabaseReference,
    private val storage: StorageReference
) : ProfileEditRepository {
    override fun updateProfile(name: String, imageUri: Uri): Completable {
        return Completable.create { emitter ->
            database.child(USER)
                .orderByChild(NAME)
                .equalTo(name)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists() && !snapshot.hasChild(uuid)) {
                            emitter.onError(Throwable(ERR_DUPLICATE_NAME))
                        } else {
                            val storageRef = storage.child("$uuid.png")
                            val uploadTask = storageRef.putFile(imageUri)
                            uploadTask.continueWithTask { task ->
                                if (!task.isSuccessful) {
                                    task.exception?.let { throw it }
                                }
                                storageRef.downloadUrl
                            }.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val downloadUri = task.result.toString()
                                    val hashMap = hashMapOf<String, Any>(
                                        NAME to name,
                                        IMAGE to downloadUri
                                    )
                                    database.child(USER).child(uuid).updateChildren(hashMap)
                                        .addOnSuccessListener {
                                            emitter.onComplete()
                                        }.addOnFailureListener {
                                        emitter.onError(it)
                                    }
                                } else {
                                    Log.d(TAG, task.exception.toString())
                                    emitter.onError(Throwable(ERR_UPLOAD_IMAGE))
                                }
                            }
                        }
                    }
                })
        }
    }

    override fun updateName(name: String): Completable {
        return Completable.create { emitter ->
            database.child(USER)
                .orderByChild(NAME)
                .equalTo(name)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists() && !snapshot.hasChild(uuid)) {
                            emitter.onError(Throwable(ERR_DUPLICATE_NAME))
                        } else {
                            val hashMap = hashMapOf<String, Any>(
                                NAME to name
                            )
                            database.child(USER).child(uuid).updateChildren(hashMap)
                                .addOnSuccessListener {
                                    emitter.onComplete()
                                }.addOnFailureListener {
                                emitter.onError(it)
                            }
                        }
                    }
                })
        }
    }
}
