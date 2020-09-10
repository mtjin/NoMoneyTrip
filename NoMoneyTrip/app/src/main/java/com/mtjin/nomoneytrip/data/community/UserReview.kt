package com.mtjin.nomoneytrip.data.community

//TODO:: 리뷰 받아올때 리뷰아이템과 유저정보 조합해서 UserReview 클래스로 받아올 예정
class UserReview(
    var id: String = "",
    var userId: String = "",
    var productId: String = "",
    var timestamp: Long = 0,
    var image: String = "",
    var title: String = "",
    var content: String = "",
    var recommend: Long = 0,
    var local: String = "",
    var name: String = "",
    var userImage: String = ""
)