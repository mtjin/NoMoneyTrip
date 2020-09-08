# NoMoneyTrip
2020 스마트 관광앱 공모전 '무전일기' 안드로이드 앱

# 구조
MVVM 적용 : 시간 관계상 DataSource 쪽 대부분을 Local, Remote 로 따로 나누지 않고 Repository 만 사용합니다. (대부분 Repository에서 서버의 데이터만 불러오도록 합니다.)

Commit 메세지
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
