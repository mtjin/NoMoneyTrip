package com.mtjin.nomoneytrip.api

import com.mtjin.nomoneytrip.data.local_page.TourIntroduceResponseBody
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    /* 관광지 검색
    *  http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=GU7pI9Qe5kD1WOmPjAuxSGYzbLGkUJXMnQmy8EyV18c%2FdMkdvBBO1gpgrNoX30AOF%2FyKVxQkdZ45c7hMLOBfzQ%3D%3D&contentTypeId=12&areaCode=1&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=12&pageNo=1&_type=json
    * */
    @GET("KorService/areaBasedList")
    fun getTourIntroduce(
        @Query("ServiceKey") ServiceKey: String = "GU7pI9Qe5kD1WOmPjAuxSGYzbLGkUJXMnQmy8EyV18c%2FdMkdvBBO1gpgrNoX30AOF%2FyKVxQkdZ45c7hMLOBfzQ%3D%3D",
        @Query("contentTypeId") contentTypeId: Int = 1,
        @Query("areaCode") areaCode: Int,
        @Query("listYN") listYN: String = "Y",
        @Query("MobileOS") MobileOS: String = "ETC",
        @Query("MobileApp") MobileApp: String = "TourAPI3.0_Guide",
        @Query("arrange") arrange: String = "A",
        @Query("numOfRows") numOfRows: Int = 12,
        @Query("pageNo") pageNo: Int = 1,
        @Query("_type") _type: String = "json"
    ): Single<TourIntroduceResponseBody>
}