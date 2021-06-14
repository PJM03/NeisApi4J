# NeisApi4J
<img src="https://img.shields.io/badge/release-v1.0.1-blue" /><br />
###### **NeisApi4J**는 [나이스 Open API](https://open.neis.go.kr/portal/mainPage.do) 를 쉽게 이용할 수 있게 해주는 라이브러리입니다.
## 사용 가능한 API
- [급식식단정보 API](https://open.neis.go.kr/portal/data/service/selectServicePage.do?infId=OPEN17320190722180924242823&infSeq=2)
- [학교기본정보 API](https://open.neis.go.kr/portal/data/service/selectServicePage.do?infId=OPEN17020190531110010104913&infSeq=2)
- 지속적인 업데이트 예정
## 사용법
- 라이브러리 추가방법
  - gradle
    > ```groovy
    > repositories {
    >     maven {
    >         url = "http://repo.pjm03.xyz/public"
    >     }
    > }
    > 
    > dependencies {
    >     implementation "com.github.pjm03:NeisApi4J:1.0.1"
    > }
    > ```
  - [jar 다운로드](https://github.com/PJM03/NeisApi4J/releases/tag/v1.0.1)
- Java
    > ```java
    > class Example {
    >   public static void main(String... args) {
    >       String key = /* API 인증키 */;
    >       int pIndex = 1; //페이지 위치
    >       int pSize = 10; //페이지 당 신청 수
    >       boolean showUri = true; //요청 Uri 출력 여부
    > 
    >       NeisAPI neisAPI = new NeisAPI(key, pIndex, pSize, showUri);
    >       
    >       // 학교기본정보 API
    >       List<SchoolInfo> schoolInfoList = neisAPI.getSchoolInfo(
    >               Arg.of(SchoolInfo.OptionalArgs.SCHOOL_NAME, "금오공업고등학교")); //'금오공업고등학교'의 정보 조회
    >       SchoolInfo schoolInfo = schoolInfoList.get(0);
    >       System.out.println(schoolInfo); //출력
    >       
    >       // 급식식단정보 API
    >       String eduOfficeCode = schoolInfo.getEducationOfficeCode(); //찾은 학교의 교육청 코드
    >       String schoolCode = schoolInfo.getSchoolCode(); //찾은 학교의 학교 코드
    >       List<SchoolMealInfo> schoolMealInfoList = neisAPI.getSchoolMealInfo(eduOfficeCode, schoolCode,
    >               Arg.of(SchoolMealInfo.OptionalArgs.MEAL_DATE, new Date())); //오늘 급식식단 조회
    >       schoolMealInfoList.forEach(System.out::println); //출력
    > 
    >       //지속적으로 다른 API 지원또한 업데이트 예정
    >   }
    > }
    > ```
- Kotlin
    > ```kotlin
    > fun main() {
    >   val key = /* API 인증키 */
    >   val pIndex = 1 //페이지 위치
    >   val pSize = 10 //페이지 당 신청 수
    >   val showUri = true //요청 Uri 출력 여부
    >   
    >   // 학교기본정보 API
    >   val neisAPI = NeisAPI(key, pIndex, pSize, showUri)
    >   val schoolInfo = neisAPI.getSchoolInfo(
    >           Arg.of(SchoolInfo.OptionalArgs.SCHOOL_NAME, "금오공업고등학교"))[0] //'금오공업고등학교'의 정보 조회
    >   println(schoolInfo)
    > 
    >   // 급식식단정보 API
    >   val eduOfficeCode = schoolInfo.educationOfficeCode
    >   val schoolCode = schoolInfo.schoolCode
    >   val schoolMealInfoList = neisAPI.getSchoolMealInfo(eduOfficeCode, schoolCode,
    >           Arg.of(SchoolMealInfo.OptionalArgs.MEAL_DATE, Date())) //오늘 급식식단 조회
    >   println(schoolMealInfoList) //출력
    > 
    >   //지속적으로 다른 API 지원또한 업데이트 예정
    > }
    > ```
