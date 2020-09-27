package com.mtjin.nomoneytrip.data.master_write.source

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.master_main.MasterProduct
import com.mtjin.nomoneytrip.data.master_write.MasterLetter
import com.mtjin.nomoneytrip.utils.MASTER_LETTER
import com.mtjin.nomoneytrip.utils.PRODUCT
import com.mtjin.nomoneytrip.utils.RESERVATION
import io.reactivex.Completable

class MasterWriteRepositoryImpl(private val database: DatabaseReference) : MasterWriteRepository {
    override fun insertMasterLetter(
        masterLetter: MasterLetter,
        masterProduct: MasterProduct
    ): Completable {
        return Completable.create { emitter ->
            database.child(PRODUCT).child(masterProduct.reservation.productId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.getValue(Product::class.java)?.let { product ->
                            masterLetter.title = product.title
                            database.child(MASTER_LETTER).child(masterProduct.user.id)
                                .child(masterLetter.id)
                                .setValue(masterLetter)
                                .addOnSuccessListener {
                                    val updateMap = HashMap<String, Any>()
                                    updateMap[MASTER_LETTER] = true
                                    database.child(RESERVATION).child(masterProduct.reservation.id)
                                        .updateChildren(updateMap).addOnSuccessListener {
                                            emitter.onComplete()
                                        }.addOnFailureListener {
                                            emitter.onError(it)
                                        }
                                }.addOnFailureListener {
                                    emitter.onError(it)
                                }
                        }

                    }

                })
        }
    }
}