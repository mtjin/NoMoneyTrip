package com.mtjin.nomoneytrip.data.community

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.login.User

//리뷰 받아올때 리뷰아이템과 유저정보 조합해서 UserReview 클래스로 받아올 예정
@Entity(tableName = "userReview")
data class UserReview(
    @PrimaryKey var id: String = "",
    @Embedded(prefix = "user_") var user: User,
    @Embedded(prefix = "review_") var review: Review,
    @Embedded(prefix = "product_") var product: Product
)