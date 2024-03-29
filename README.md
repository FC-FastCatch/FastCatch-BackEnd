﻿
# 빨(8)리 잡아 - FastCatch
## 팀 - 이거 다 되면 8조?!
**야놀자 테크스쿨 프론트엔드** : 어준혁, 고솔미, 윤태관, 이예인, 정범환

**야놀자 테크스쿨 백엔드** : 박주현, 김현아, 이의인, 정병주
***
### 백엔드 팀원 소개 및 역할
|                            박주현                             |                            김현아                            |                            이의인                            |                            정병주                             |
|:----------------------------------------------------------:|:---------------------------------------------------------:|:---------------------------------------------------------:|:----------------------------------------------------------:|
|                            👑팀장                            |                            팀원                             |                            팀원                             |                             팀원                             |
|    [Programmer-may](https://github.com/Programmer-may)     |            [decten](https://github.com/decten)            |         [dldmldlsy](https://github.com/dldmldlsy)         |     [JeongByungJoo](https://github.com/JeongByungJoo)      |
| ![](https://avatars.githubusercontent.com/u/114227320?v=4) | ![](https://avatars.githubusercontent.com/u/52107658?v=4) | ![](https://avatars.githubusercontent.com/u/76683396?v=4) | ![](https://avatars.githubusercontent.com/u/129931655?v=4) |
|               숙박 도메인 API<br/>프로젝트 전체 인프라 구축                |                장바구니 도메인 API<br/>OpenAPI 연동                |           주문 도메인 API<br/>Spring Security 회원API            |                         회원 도메인 API                         |
<br>
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

### 서버 인프라
![미니프로젝트 서버 인프라.png](%EB%AF%B8%EB%8B%88%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%20%EC%84%9C%EB%B2%84%20%EC%9D%B8%ED%94%84%EB%9D%BC.png)


### 로컬 데이터베이스 H2 접속 경로

1. http://localhost:8080/h2-console 에 들어간다.
2. 아래 정보대로 입력 칸을 채우고 Connect를 누른다.

- Driver Class: org.h2.Driver
- JDBC URL: jdbc:h2:mem:testdb
- User Name: sa
- Password: (빈칸)

### 프로젝트 테스트 가이드

<details>
<summary>매뉴얼</summary>
프로젝트 배포 사이트 : https://www.fastcatchapp.com

ID : aaaa@naver.com

PW: aaaa1111
</details>

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

## 에러 해결 방법

- 주요 에러 내용
  <details>
  <summary>미인증 예외처리</summary>

    1. 문제 상황
    - 유효하지 않은 액세스 토큰일 경우에 대해 따로 예외 처리를 해도 콘솔에만 출력되고 실제로는 적용이 안된 채, 500 에러만 전달됨.
    2. 원인
    - 회원 인증을 위한 액세스 토큰 유효성 검증은 컨트롤러로 가기 전에 앞단에서 필터링하던 중 발생하므로 기존에 예외를 다루던 ExceptionHandler로 처리불가
    - 필터에서 발생한 JWT 관련 예외를 처리하는 로직이 없기 때문에 프론트에게 전달이 안됐음.
    - 필터 발생 예외를 처리하는 EntryPoint에서는 발생한 예외를 AuthenticationException으로 잡아서 던지기 때문에 토큰 관련 JwtException은 EntryPoint로도 적용불가
    3. 해결방법
    - 시큐리티 필터 앞에 JWT에 관한 필터를 하나 더 만듦. (JwtExceptionFilter)
    - 기존 필터에서 유효성 검증하던 중 JwtException이 발생하면 잡아서 우리가 예외처리한대로 예외 띄워줌 (상태코드: 401, 에러 메세지: 토큰 기한 만료)
  </details>
  <br>
  <details>
  <summary>CORS 에러</summary>

    1. 문제 상황
    - 백엔드에서 로컬 테스트를 통과하여 테스트 서버에 JAR 파일을 배포하고 프론트 엔드의 로컬에서 통신을 하려고 하니 CORS 에러가 나타났다.
    2. 원인
    - API 호출은 Cross - Origin 정책이 아닌 Same - Origin 정책이다. 같은 Origin에서 오는 요청은 받을 수 있지만, 다른 Origin에서 오는 요청은 보안상 막아서 CORS 에러가 생기는 것이다.  서비스는 EC2 인스턴스 호스트주소에서 80 포트에서 돌아가는데, 프론트엔드에서 로컬로 테스트를 할 땐 클라이언트의 호스트는 localhost 와 5173 포트로 요청을 보내서 막는 것이었다.
    3. 해결방법
    - 프로젝트 Config 패키지 내에 WebConfig를 설정하여 localhost:5173 으로 오는 요청을 열어 두어 문제를 해결하였다.
  </details>
  <br>
  <details>
  <summary>Mixed Content 에러</summary>

    1. 문제 상황
    - 프론트엔드 배포 사이트와 서버를 연동하려고 하니 Mixed Content 에러가 발생하였다.
    2. 원인
    - HTTP에서 HTTPS 요청은 가능해도 HTTPS 에서 HTTP 요청은 보안의 문제로 되지 않기 때문에 API 통신이 HTTPS 프로토콜에서도 가능하게 해야했다.
    3. 해결방법
        1. 배포 서버(EC2) 내에  아파치 혹은 NGINX 를 구동시켜 프록시 서버를 만든 후 프록시 서버에서 HTTPS 요청은 받으면 해당 서비스의 HTTP 요청으로 리다이렉트 해주는 방법

        2. 배포 서버(EC2) 겉에 로드밸런서를 두어 도메인을 통해 들어온 요청을 로드밸런서를 통해 HTTPS 요청을 서비스의 HTTP 요청으로 리다이렉트 해주는 방법
           두번째 방법을 활용하여 도메인을 사서 EC2 인스턴스 겉에 로드밸런서를 두어 HTTPS 로 오는 요청을 서비스가 구동중인 포트 번호에 HTTP 요청으로 리다이렉트 시켜주어 해결하였다.
  </details>

## 팀원 개인 회고
<details>
<summary>김현아</summary>

1. 프로젝트 진행하면서 전반적으로 느낀점

Front와 Back의 통신 방식과 데이터 형식을 정확하게 정의하고, 문서화하고, 공유하고, 테스트 방법을 학습할 수 있었습니다.

또한 예외와 에러 상황들을 어떻게 처리하고, 유저에게 안내하고, 로그를 남기는 방법들을 구현하고, 피드백 받는 과정을 반복하면서 예외 상황과 에러 처리에 대한 능력을 향상시킬 수 있었습니다.

프로젝트를 통해 다양한 기술과 경험을 습득하고, 팀원들과의 협업과 소통을 통해 좋은 결과물을 만들 수 있어 뜻 깊었습니다

2. 프로젝트 진행하면서 어려웠던 점
- 장바구니 아이템을 아이템(룸)에 맞는 숙박으로 묶어서 출력

장바구니 아이템과 숙박(accommodation)은 연관 관계를 넣으면 안 되지만, 숙박-장바구니 아이템 메서드가 새로 필요했음

1. 처음 시도: dto에서 처리 하려 했으나 엔터티(숙박에서 장바구니 아이템)가 없기 때문에 실패
2. 해결: 서비스 코드에서 아이템이 숙박 호출하고 동일할 경우 기존 리스트에 추가 하도록 함
</details>
<br>
<details>
<summary>박주현</summary>

1. 프로젝트 진행하면서 전반적으로 느낀점

   짧은 시간동안 많은 것을 배운 프로젝트여서 너무 좋았다. 특히 프로젝트 전체 인프라쪽을 담당하였는데, 그저 공부만 했던 CS 네트워크 지식들을 실전에서 써볼 수 있는 기회였다. 팀원들이 테스트 코드, 체크스타일과 컨벤션 등을 필수적으로 지켜가며 프로젝트를 진행한 것이 많은 도움이 됐다고 하여 뿌듯했다.
   또한 매번 SSR 프로젝트만 하였는데, 프론트 엔드와 협업하여 프로젝트를 할 수 있어서 좋았다. 또한 팀원들이 팀장의 말을 잘 따라주고, 배정된 업무를 잘 해나가주어 정말 감사하고, 고마웠다.

2. 프로젝트 진행하면서 어려웠던 점

   처음으로 프론트엔드와 협업하여 프로젝트를 만들었기에, 배포 사이트를 연동할때 많은 네트워크 이슈를 겪었다. 특히 첫 경험한 CORS 에러와 Mixed Content 에러를 맞닥뜨렸는데, API 호출은 Same -Origin 정책을 사용하므로 프론트엔드에서 사용하는 호스트와 포트번호를 열어주어 해결하였다. Mixed Content 에러는 HTTPS 에서 보안이 되지 않은 HTTP 요청을 하여 발생한 에러였다. EC2 서버를 HTTPS 프로토콜 요청도 가능하게 하기 위해 도메인을 사고, 그앞에 로드밸런서를 두어 SSL 인증서를 설정해주어 HTTPS 요청이 오면 EC2 백엔드 서버로 포워딩 시켜주어 해결하였다.
</details>
<br>
<details>
<summary>이의인</summary>

1. 프로젝트 진행하면서 전반적으로 느낀점
- 9명의 개발자들이 모여서 하나의 서비스를 위해 의견을 조율하고 기획부터 배포까지 함께 하는 과정을 통해 꾸준한 소통과 일정 관리, 가독성 좋은 API명세서 작성의 중요성을 느꼈다.
- 변수명, 메서드명, URI, DB구조, 패키지 구조 등 더 RESTful한 설계, 확장가능한 설계를 위해 끊임없이 고민하고 의견 교류하는 과정을 통해 많이 배울 수 있었고, CI/CD, Checkstyle 적용을 통해 코드의 품질 및 개발의 효율성 모두 향상시킬 수 있었다.
- 특히 정확한 주문 처리 및 재고 파악을 위한 서비스로직 및 Security+JWT를 이용한 인증인가 구현에 도전하며 관련 기술에 대한 이해도를 높였고 기술적 성장을 이룰 수 있었다고 생각한다.

2. 프로젝트 진행하면서 어려웠던 점
- 특정 기술을 개발하는 데에 필요한 시간을 예상하지 못해 일정에 차질이 생긴 점이 아쉬웠다. 테스트가 성공하더라도 서버에 배포했을 때, 예상치 못한 이슈들을 겪으며 더 많은 시간이 필요했고 꼼꼼한 테스트 코드 작성의 중요성을 한 번 더 느낄 수 있었다.
- Spring Security를 맡아서 구현해내는 과정에서 API별로 접근 권한을 설정하고 필터를 설정하는 등 시큐리티 Configuration를 이해하는 데에 있어 어려움을 느꼈다. 특히, Spring Security를 적용한 후에 필터과정에서 발생하는 예외를 내가 구현한대로 처리되지않아 많은 시간을 허비했다.  JWT 예외 처리 Filter를 추가하고 해당 필터를 기존 필터보다 이전에 설정하는 방법을 통해 해결할 수 있었다.
</details>
<br>
<details>
<summary>정병주</summary>

1. 프로젝트 진행하면서 전반적으로 느낀점
- 각 도메인별로 코드의 로직을 이해할 수 있게 된 점

  → 이전 프로젝트를 진행하면서 팀원들이나 다른 로직을 보면 이해하기 어려웠으나 이번 프로젝트를 통해 전체적인 로직의 흐름을 파악하기가 수월해졌다.

- 지난 토이 프로젝트 등을 하면서 제대로 Git Commit이나 Convention에 대해 공부를 했지만 제대로 다뤄본 적은 없었는데, 이번 프로젝트를 통해 조금이나마 활용해볼 수 있어서 개념들이 구체화된 것 같다.
- H2, Postman을 활용한 로컬 및 배포 테스트 등 새로운 경험들을 직접 해볼 수 있게 되어 좋았다.

  → 사용 과정에서 MySQL 에 업데이트되는 정보 뿐 아니라, H2 등의 활용으로 새로운 경험을 할 수 있어 좋았다.

2. 프로젝트 진행하면서 어려웠던 점
- 로컬 테스트 및 배포 테스트 전반적인 경험 및 이해도 부족

  → Postman을 통해 로컬에서 테스트 및 점검은 가능했지만, H2 의 활용이나 배포에 대한 전반적인 이해 부족으로 흐름을 파악하기가 힘들었다.

- 테스트 코드 작성 및 프로젝트 전반적인 흐름 이해도 부족

  → 테스트 코드 작성의 어려움이 있고, 도메인 별로의 로직의 흐름은 파악할 수 있게 되었으나나, 아직 프로젝트 전체의 구조를 파악하는 데 어려움이 있다.

- Git 사용의 어려움 (merge conflict 해결, PR 등)

  → 공부해왔던 Git의 개념이나 Team Convention 등은 익숙해졌으나 아직 merge 시의 conflict 의 해결 등 팀 협업에서 필수적인 부분의 실력이 부족하다.
</details>


