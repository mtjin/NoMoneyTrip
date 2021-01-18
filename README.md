# NoMoneyTrip
2020 스마트 관광앱 공모전 '무전일기' 안드로이드 앱
스토어 : https://play.google.com/store/apps/details?id=com.mtjin.nomoneytrip

시연영상1(첫 회원가입 부분만) : https://youtu.be/f2-8GOL8esE

시연영상2(전체적으로) : https://youtu.be/Ty2MfKSNdso


# 안드로이드 아키텍처 구조
MVVM 적용 : 시간과 작업 효율성 면에서 로그인과 바텀네비가 있는 메인(홈)화면 부분만 Repository 를 local과 remote로 나누고 나머지 부분은 Local DB는 필요없고 Remote 서버 API하고만 통신하기 때문에 Local과 Remote를 나누지 않고 Repository만 만들어 서버API와 통신하게 구현했습니다.
<img src="https://user-images.githubusercontent.com/37071007/103278060-ed3a8e00-4a0d-11eb-9047-76e80fb0f7ac.png" ></img>


# 사용기술
Android(Kotlin), Jetpack Navigation, MVVM, Room, AAC ViewModel, DataBinding, LiveData, Koin, RxJava2, RxKotlin, RxAndroid, Retrofit2, Firebase(RTDB , Storage, FCM, Auth, Crashlytics), AlarmManager, WorkManager, ViewPager2, Material Design, Lottie,  Kakao Login API, Tmap API, Tour API, Linkfy, Glide, Unit Test(Espresso, Mockito, Junit 4) 등


# Jetpack Navigation 설계
효율성 면에서 처음 로그인부분만 액티비티로 구성하고 나머지 메인액티비티에서는 Jeptack Navigation을 사용과 함께 모두 프래그먼트로 구성함으로써 싱글 액티비티 아키텍처로 구성했습니다.
<img src="https://user-images.githubusercontent.com/37071007/99547497-eece6a80-29fa-11eb-88d6-b128a51beee9.png" ></img>


# 데이터베이스 구조 설계 (timestamp 기반 String 타입 id 사용)
<img src="https://user-images.githubusercontent.com/37071007/95646136-63f77780-0b00-11eb-87dc-6b0207303803.png"/>


# Commit 메세지
-----------
**규칙 (2020-07-01 추가) :** [행동] 내용(한글) , 출처 : https://blog.ull.im/engineering/2019/03/10/logs-on-git.html

**FIX ->**
가장 자주 사용되는 커밋 로그 중 하나로 ‘Fix’가 있습니다. 보통 올바르지 않은 동작을 고친 경우에 사용합니다.

**ADD ->**
코드나 테스트, 예제, 문서 등의 추가가 있을 때 사용합니다

**REMOVE ->**
코드의 삭제가 있을 때 사용합니다. ‘Clean’이나 ‘Eliminate’를 사용하기도 합니다. 보통 A 앞에 ‘unnecessary’, ‘useless’, ‘unneeded’, ‘unused’, ‘duplicated’가 붙는 경우가 많습니다.

**IMPROVE ->**
향상이 있을 때 사용합니다. 호환성, 테스트 커버리지, 성능, 검증 기능, 접근성 등 다양한 것들이 목적이 될 수 있습니다.

**IMPLEMENT ->**
구현체를 완성시켰을 때 사용합니다.

**REVISE ->**
문서의 개정이 있을 때 주로 사용합니다.

**MOVE ->**
코드의 이동이 있을 때 사용합니다.

**RENAME ->**
이름 변경이 있을 때 사용합니다.


# 사진
------------
<img src="https://github.com/mtjin/NoMoneyTrip/blob/master/playstore_file/%ED%94%8C%EB%A0%88%EC%9D%B4%EC%8A%A4%ED%86%A0%EC%96%B4%EC%82%AC%EC%A7%84.jpg?raw=true" width="30%"></img>
<img src="https://github.com/mtjin/NoMoneyTrip/blob/master/playstore_file/%ED%94%8C%EB%A0%88%EC%9D%B4%EC%8A%A4%ED%86%A0%EC%96%B4%EC%82%AC%EC%A7%842.jpg?raw=true" width="30%"></img>
<img src="https://github.com/mtjin/NoMoneyTrip/blob/master/playstore_file/%ED%94%8C%EB%A0%88%EC%9D%B4%EC%8A%A4%ED%86%A0%EC%96%B4%EC%82%AC%EC%A7%843.jpg?raw=true" width="30%"></img>
<img src="https://github.com/mtjin/NoMoneyTrip/blob/master/playstore_file/%ED%94%8C%EB%A0%88%EC%9D%B4%EC%8A%A4%ED%86%A0%EC%96%B4%EC%82%AC%EC%A7%844.jpg?raw=true" width="30%"></img>
<img src="https://github.com/mtjin/NoMoneyTrip/blob/master/playstore_file/%ED%94%8C%EB%A0%88%EC%9D%B4%EC%8A%A4%ED%86%A0%EC%96%B4%EC%82%AC%EC%A7%845.jpg?raw=true" width="30%"></img>
<img src="https://github.com/mtjin/NoMoneyTrip/blob/master/playstore_file/%ED%94%8C%EB%A0%88%EC%9D%B4%EC%8A%A4%ED%86%A0%EC%96%B4%EC%82%AC%EC%A7%846.jpg?raw=true" width="30%"></img>


# 앱내 이미지
https://github.com/mtjin/NoMoneyTrip/blob/master/%EB%AC%B4%EC%A0%84%EC%9D%BC%EA%B8%B0%20%EC%95%B1.pdf

