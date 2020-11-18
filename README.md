# NoMoneyTrip
2020 스마트 관광앱 공모전 '무전일기' 안드로이드 앱
https://play.google.com/store/apps/details?id=com.mtjin.nomoneytrip

# 안드로이드 구조
MVVM 적용 : 시간과 작업 효율성 면에서 DataSource 쪽 로그인 부분을 제외하고 대부분을 Local, Remote 로 따로 나누지 않고 Repository 에서 서버API 나 Firebase 에서 불러오도록 했습니다.

# Jetpack Navigation 설계
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

