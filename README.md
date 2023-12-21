
# 빨(8)리 잡아 - FastCatch
## 팀 - 이거 다 되면 8조?!
**프론트엔드** : 어준혁, 고솔미, 윤태관, 이예인, 정범환

**백엔드** : 박주현, 김현아, 이의인, 정병주
***
### 백엔드 팀원 소개 및 역할
|                            박주현                             |                            김현아                            |                            이의인                            |                            정병주                             |
|:----------------------------------------------------------:|:---------------------------------------------------------:|:---------------------------------------------------------:|:----------------------------------------------------------:|
|                            👑팀장                            |                            팀원                             |                            팀원                             |                             팀원                             |
|    [Programmer-may](https://github.com/Programmer-may)     |            [decten](https://github.com/decten)            |         [dldmldlsy](https://github.com/dldmldlsy)         |     [JeongByungJoo](https://github.com/JeongByungJoo)      |
| ![](https://avatars.githubusercontent.com/u/114227320?v=4) | ![](https://avatars.githubusercontent.com/u/52107658?v=4) | ![](https://avatars.githubusercontent.com/u/76683396?v=4) | ![](https://avatars.githubusercontent.com/u/129931655?v=4) |
|               숙박 도메인 API<br/>프로젝트 전체 인프라 구축                |                장바구니 도메인 API<br/>OpenAPI 연동                |           주문 도메인 API<br/>Spring Security 회원API            |                         회원 도메인 API                         |
공통 : API 명세서 작성, 테이블 설계, 팀 노션페이지 작성 및 정리, 단위/통합 테스트 코드 작성
***
## 🚩 개요
**프로젝트 내용**: 숙박 예약 서비스

**프로젝트 주제 및 필수 구현 기능 제안**: 야놀자

**프로젝트 목적**: 팀 협업, RESTful API 개발

**프로젝트 기간**: 2023년 11월 20일 (월) ~ 12월 01일(금)

### 기술 스택

- **언어**: Java 17
- **개발 환경**: Gradle, Spring Boot 3
- **라이브러리**: Spring Web, Spring Security, Spring Validation, JPA, JUnit5, Rest-Assured, Lombok, JWT
- **영속성**: MySQL 8
- **CI/CD**: GitHub, GitHub Actions
- **Infra**: AWS Route53, AWS ALB, AWS EC2, AWS RDS, AWS S3

### 프로젝트 파이프라인
![최종배포파이프라인.png](%EC%B5%9C%EC%A2%85%EB%B0%B0%ED%8F%AC%ED%8C%8C%EC%9D%B4%ED%94%84%EB%9D%BC%EC%9D%B8.png)

### 로컬 데이터베이스 H2 접속 경로

1. http://localhost:8080/h2-console 에 들어간다.
2. 아래 정보대로 입력 칸을 채우고 Connect를 누른다.

- Driver Class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:testdb
- User Name: sa
- Password: (빈칸)

# ⚖️ 컨벤션

----

### 1. 코딩 컨벤션

- 커스텀 구글 코딩 컨벤션을 사용합니다.

### 2. 브랜치 전략 - Git Flow

- 브랜치 전략으로 Git Flow를 사용합니다.
- 도메인 구현 및 설계는 모든 팀원의 Approve를 받아야 Merge 할 수 있습니다.

### <브랜치별 역할>

### `feature/#`

- 실제 작업을 하는 브랜치
- 이슈 번호가 1이라면 feature/1로 만들면 된다.
- 'develop'을 베이스 브랜치로 하여 만들어야 한다.
    - ( 브랜치 생성은 베이스 브랜치[ 체크아웃되어있는 브랜치 ]를 기준으로 만들어진다.)
- 작업이 완료되면 develop으로 Pull Request를 날린다.
- 본인을 제외한 조원의 Approve를 모두 받았다면 Merge한다.

### `develop`

- 테스트 서버에 자동 배포되는 브랜치
- 다음 버전 개발을 위해 release으로 가기 전 기능 코드들을 모아두는 브랜치
- 작성한 기능이 잘 작동되는지 확인하고, release으로 PR 및 Merge를 하면 된다.
- develop으로 Merge 하고 나서 자동 배포된 테스트 서버에서 자신의 API가 정상 작동하는지 꼭 테스트해야 한다.

### `release`

- 실제 서비스를 운영할 수 있는 메인 서버 자동 배포되는 브랜치
- release으로 Merge 하고 나서 자동 배포된 메인 서버에서 자신의 API가 정상 작동하는지 꼭 테스트해야 한다.

### `hotfix`

- 서버에서 테스트 후 오류가 발견됐을 때 바로 수정해서 Merge 할 수 있는 브랜치
- `hotfix/이슈번호`로 생성

### `main`

- 최종본을 갖는 브랜치

### 3. 테스트 코드

- **Service**: 단순 Repository 메서드 호출 이상의 로직을 수행하는 메서드에 대해 테스트 코드 작성
- **Controller**: public 메서드 테스트 코드 작성

### 4. 성공/오류 코드 번호

- **200** : 모든 성공
- **400** : 클라이언트가 입력을 잘못한 경우
- **401** : 인증 정보가 없는 경우
- **403** : 접근 권한이 없는 경우
- **500** : 서버 내부 에러

### 5. 기타 합의사항

- **협업 관련**
    - 정기 오프라인 회의 
    - 데일리 스크럼: 매일 오전 10:00 노션에 진행상황 공유
    - Discord 에서 프론트 및 백 회의사항 공유, 서로간의 질문사항 & 건의사항 소통
      <br><br>
- **커밋 메시지 관련**
    - 커밋 제목은 `prefix: 커밋 메시지` 형태로 합니다.
    - prefix의 목록과 각각의 용도는 IntelliJ 플러그인 commit message template 에 맞춰 작성
    - 커밋 내용을 자세하게 적습니다.

# 📋 기획안

---
### 프로젝트 기능 구현 설명
<details>
<summary>구현 기능 설명</summary>

1. Member - 회원가입 기능
   : 이메일, 비밀 번호, 이름, 닉네임, 생년월일을 통해 회원가입 (닉네임 중복 확인)

2. Member - 로그인, 로그아웃 기능
   : 이메일, 비밀번호를 통한 로그인 및 로그아웃 (리프레시 토큰을 이용한 토큰 재발급 기능 포함)

3. Accommodation - 전체 상품 목록 조회
   : DB에서 전체 혹은 카테고리별 상품 목록을 조회 (예약 마감인 경우 자체 마감 표시)

4. Accommodation - 개별 상품 조회
   : 전체 상품 목록에서 특정 상품 이미지를 클릭하면 DB에 저장해둔 해당 상품에 대한 상세 정보를 조회

5. Accommodation - 상품 옵션 선택
   : 날짜, 숙박 인원은 기본으로 포함, 이 외 상품별로 필요한 객실 옵션 적용

6. Cart - 장바구니 담기
   : 회원당 한 개씩 장바구니를 가지며 상품을 클릭하여 장바구니에 선택한 상품 담기

7. Cart - 장바구니 보기
   : 장바구니에 담아둔 객실 상품의 데이터에 따른 상품별 금액, 전체 주문 합계 등의 정보를 화면에 출력 (개별 삭제 가능)

8. Order - 주문하기
   : 장바구니의 주문하기 버튼 혹은 개별 상품 조회 페이지에서 주문하기 버튼을 누르면 주문하기 페이지로 이동하여 주문 가능

9. Order - 결제하기
   : 주문 페이지에서 14세 이상 동의 후 결제하기 버튼을 클릭하면, 주문한 것으로 처리

10. Order - 주문 결과 확인
    : 결제를 성공적으로 처리하면 주문한 상품(들)에 대한 주문 결과를 확인할 수 있는 주문 목록 페이지 이동 버튼 출력 및 결제 성공 혹은 결제 실패를 표시

11. Order - 주문 내역 확인
    : 주문 유형별 본인의 주문 내역을 3개씩 페이징하여 조회 (예약완료, 사용완료, 주문취소)

</details>

## ERD
![mini_project_8_최종_erd설계.png](mini_project_8_%EC%B5%9C%EC%A2%85_erd%EC%84%A4%EA%B3%84.png)

## API 설계
![최종API명세.png](%EC%B5%9C%EC%A2%85API%EB%AA%85%EC%84%B8.png)

프론트 엔드 분들과 오프라인 미팅에서 함께 협의 후 작성

## BackEnd 개발 규칙
메소드 네이밍 규칙, 패키지 네이밍 규칙, DTO 네이밍 규칙 등 

팀 노션 페이지에 팀원들과 함께 지킬 규칙들을 정하여 문서화 시켰습니다.




## API 별 PostMan 테스트 결과
<details>
<summary>PostMan 테스트 결과 사진</summary>

### 숙박 개별조회
![숙박개별조회.png](resultImage%2F%EC%88%99%EB%B0%95%EA%B0%9C%EB%B3%84%EC%A1%B0%ED%9A%8C.png)

### 숙박 전체조회
![숙박전체조회.png](resultImage%2F%EC%88%99%EB%B0%95%EC%A0%84%EC%B2%B4%EC%A1%B0%ED%9A%8C.png)

### 숙박 조회시 종료일시가 시작일시 보다 빠를 때
![종료일시가 시작일시보다 빠를때.png](resultImage%2F%EC%A2%85%EB%A3%8C%EC%9D%BC%EC%8B%9C%EA%B0%80%20%EC%8B%9C%EC%9E%91%EC%9D%BC%EC%8B%9C%EB%B3%B4%EB%8B%A4%20%EB%B9%A0%EB%A5%BC%EB%95%8C.png)

### 숙박 조회시 시작일시가 과거의 날짜일 때
![시작일시가 과거일때.png](resultImage%2F%EC%8B%9C%EC%9E%91%EC%9D%BC%EC%8B%9C%EA%B0%80%20%EA%B3%BC%EA%B1%B0%EC%9D%BC%EB%95%8C.png)

### 회원가입
![회원가입.png](resultImage%2F%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.png)

### 회원 정보 수정
![회원정보수정.png](resultImage%2F%ED%9A%8C%EC%9B%90%EC%A0%95%EB%B3%B4%EC%88%98%EC%A0%95.png)

### 회원 조회
![회원조회.png](resultImage%2F%ED%9A%8C%EC%9B%90%EC%A1%B0%ED%9A%8C.png)

### 로그인
![로그인.png](resultImage%2F%EB%A1%9C%EA%B7%B8%EC%9D%B8.png)

### 장바구니 아이템 등록
![장바구니 아이템 등록.png](resultImage%2F%EC%9E%A5%EB%B0%94%EA%B5%AC%EB%8B%88%20%EC%95%84%EC%9D%B4%ED%85%9C%20%EB%93%B1%EB%A1%9D.png)

### 장바구니 조회
![장바구니 조회.png](resultImage%2F%EC%9E%A5%EB%B0%94%EA%B5%AC%EB%8B%88%20%EC%A1%B0%ED%9A%8C.png)

### 장바구니 삭제
![장바구니 삭제.png](resultImage%2F%EC%9E%A5%EB%B0%94%EA%B5%AC%EB%8B%88%20%EC%82%AD%EC%A0%9C.png)

### 주문 생성
![주문 생성.png](resultImage%2F%EC%A3%BC%EB%AC%B8%20%EC%83%9D%EC%84%B1.png)

### 주문 목록 조회
![주문목록조회성공.png](resultImage%2F%EC%A3%BC%EB%AC%B8%EB%AA%A9%EB%A1%9D%EC%A1%B0%ED%9A%8C%EC%84%B1%EA%B3%B5.png)

### 로그인 안 했을시 주문 목록 조회 실패
![로그인안했을시 주문목록조회 실패.png](resultImage%2F%EB%A1%9C%EA%B7%B8%EC%9D%B8%EC%95%88%ED%96%88%EC%9D%84%EC%8B%9C%20%EC%A3%BC%EB%AC%B8%EB%AA%A9%EB%A1%9D%EC%A1%B0%ED%9A%8C%20%EC%8B%A4%ED%8C%A8.png)

### 주문 취소
![주문 취소.png](resultImage%2F%EC%A3%BC%EB%AC%B8%20%EC%B7%A8%EC%86%8C.png)

----
</details>


