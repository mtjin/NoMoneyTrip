package com.mtjin.nomoneytrip.data.community

import com.mtjin.nomoneytrip.data.home.Product
import com.mtjin.nomoneytrip.data.login.User

//리뷰 받아올때 리뷰아이템과 유저정보 조합해서 UserReview 클래스로 받아올 예정
data class UserReview(
    var user: User,
    var review: Review,
    var product: Product
)