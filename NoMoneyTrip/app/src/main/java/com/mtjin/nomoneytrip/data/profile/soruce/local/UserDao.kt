package com.mtjin.nomoneytrip.data.profile.soruce.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mtjin.nomoneytrip.data.login.User
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Completable

    @Query("SELECT * FROM user WHERE id = :uuid")
    fun getUser(uuid: String): Single<User>
}