# wanted-pre-onboarding-backend
원티드 프리온보딩 백엔드 인턴십 선발 과제입니다.

필수 API 요구사항 이외에도 **사용자와 게시글 서비스 단위 테스트 코드 추가** 및 **클라우드 환경(AWS)에 배포 환경을 설계하고 애플리케이션을 배포**하였습니다.

<br>

## 1. 성명
<h3>은지일</h3>

<br>

## 2. 구현 방법 및 이유
- 최신 백엔드 개발 트렌드를 따라가기 위해 가장 최신 LTS 버전인 **Java 17**과 **Spring Boot 3.1.2**를 기반으로 애플리케이션을 구현하였습니다.

- 구현 순서:
  1. 엔티티 클래스(Member, Article)
  2. 도메인 별 Controller, Service, Repository 및 기타 필요 Component(mapper, dto) 구현
  3. Spring Security 적용(비밀번호 암호화 및 JWT 인증)
  4. 단위 테스트 코드 추가
  5. RDS(MySQL 8.0.33) 생성
  6. AWS 배포(Elastic Beanstalk 활용)
  7. 리팩토링

- 최신 Java와 Spring Boot 버전을 기반으로 애플리케이션 구현한 덕분에 Java 14에서 도입된 **record**를 활용하여 DTO를 간결하게 구현할 수 있었지만, 비밀번호 암호화 및 JWT 인증을 위해 도입한 최신 **Spring Security 6.1** 버전에서 많은 업데이트가 있어 학습 및 반영에 어려움이 있었습니다.

  - **마주쳤던 문제**: 기존에 사용했던 antMatchers 메서드가 deprecated되고 새롭게 통일된 requestMatchers 메서드 패턴에서 오류가 발생했고, Spring Security 설정을 해주는 SecurityFilterChain Bean 등록 메서드에서 Lambda DSL 방식이 기본으로 도입되어 기존에 사용했던 메서드 체이닝 방식에서의 전환에 어려움을 겪었습니다.

  - **해결 방법**: requetMatchers 관련 오류는 Spring 공식 사이트의 문서(https://spring.io/security/cve-2023-34035)를 참고하여 antMatcher로 다시 감싸주는 방식으로 해결하였으며, Lambda DSL 방식은 구글에서 검색한 모범 사례들 및 Spring 공식 사이트의 문서를 참고하여 적용하였습니다.

- 버전관리 및 협업능력을 키우기 위하여 **Git-Flow** 전략을 채택하여 feature 브랜치에서 개발한 기능을 develop 브랜치에 순차적으로 merge하는 방식으로 애플리케이션을 점진적으로 개발하였고, **유다시티 커밋 컨벤션**을 채택하여 커밋 메시지를 체계화하고 자세하게 작성하여 팀원들이 내가 한 작업을 쉽게 확인할 수 있도록 하였습니다.

<img width="1440" alt="스크린샷 2023-08-09 오전 11 50 33" src="https://github.com/sarasa0310/wanted-pre-onboarding-backend/assets/129481038/027653e9-4f39-4bd2-be0f-52ebff80d5ff">

<br>
<br>

## 3. 배포된 API 주소와 설계한 AWS 환경 그림
애플리케이션을 빠르게 배포하고 버전 관리도 쉽게 할 수 있도록 AWS Elastic Beanstalk를 활용하여 배포하였습니다.

[API 주소] http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com

![캡처](https://github.com/sarasa0310/wanted-pre-onboarding-backend/assets/129481038/aaadb650-5bf8-4c1f-8f91-63e4b19641da)

<br>

## 4. API 동작 촬영 데모 영상 링크
Postman을 활용해 AWS 환경에 배포된 API 동작을 테스트하였습니다.
정상 동작 촬영 영상은 Youtube 사이트에서 시청하실 수 있습니다.
https://youtu.be/7qup0gXP7eM

<br>

## 5. 애플리케이션의 실행 방법(API 엔드포인트 포함)
각각의 엔드포인트로 HTTP 요청을 보냄으로써, 애플리케이션을 실행시킬 수 있습니다.
게시글 목록 조회 및 특정 게시글 조회는 Chrome, Safari, Edge와 같은 웹 브라우저를 이용하여 응답을 받을 수 있습니다.
그 외 요청은 Postman과 같은 클라이언트 툴을 이용해주세요.

**1. 회원가입**
- Method: POST
- URL: http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/members
- 이메일과 비밀번호로 회원가입을 할 수 있습니다.
- 이메일은 @를 포함하여 일반적인 이메일 양식으로 작성해주세요.
- 비밀번호는 8자 이상으로 작성해주세요.
- 회원가입이 성공한 경우 응답 헤더의 Location에서 회원의 ID를 알 수 있습니다.

**2. 로그인**
- Method: POST
- URL: http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/login
- 회원가입한 이메일과 비밀번호로 로그인을 할 수 있습니다.
- 게시글을 생성, 수정, 삭제하기 위해서는 로그인을 할 필요가 있습니다.
- 로그인이 성공한 경우 응답 헤더의 Authorization에서 JWT AccessToken을 확인할 수 있습니다.

**3. 게시글 생성**
- Method: POST
- URL: http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/articles
- 요청 본문에 제목과 본문 내용을 담아주세요.
- 게시글 생성을 하기 위해서는 요청 헤더에 발급받은 JWT AccessToken을 포함시켜서 보내주세요.

**4. 게시글 목록 조회**
- Method: GET
- URL: http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/articles
- Pagination이 적용되어 있습니다.
- page와 size를 별도로 지정하지 않으면 최신순으로 10개씩 조회가 됩니다.
- page와 size를 변경하고 싶다면, 쿼리 파라미터에 page와 size를 붙여서 요청을 보내주세요.

**5. 특정 게시글 조회**
- Method: GET
- URL: http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/articles/{articleId}
- 조회하고 싶은 게시글의 ID를 URL 마지막에 붙여서 요청을 보내주세요.
- 응답 본문에서 대상 게시글의 ID, 제목, 내용을 확인할 수 있습니다.

**6. 특정 게시글 수정**
- Method: PATCH
- URL: http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/articles/{articleId}
- 수정하고 싶은 게시글의 ID를 URL 마지막에 붙여서 요청을 보내주세요.
- 제목이나 내용을 선택적으로 수정할 수도 있고, 한번에 모두 수정할 수도 있습니다.
- 요청 본문에 수정하고 싶은 내용을 포함하여 요청을 보내주세요.
- 게시글 수정은 게시글 작성자만 할 수 있습니다.
- 게시글 수정을 하기 위해서는 요청 헤더에 발급받은 JWT AccessToken을 포함시켜서 보내주세요.

**7. 특정 게시글 삭제**
- Method: DELETE
- URL: http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/articles/{articleId}
- 삭제하고 싶은 게시글의 ID를 URL 마지막에 붙여서 요청을 보내주세요.
- 게시글 삭제는 게시글 작성자만 할 수 있습니다.
- 게시글 삭제를 하기 위해서는 요청 헤더에 발급받은 JWT AccessToken을 포함시켜서 보내주세요.

<br>

## 6. 데이터베이스 테이블 구조
![원티드 인턴십 과제 ERDPNG](https://github.com/sarasa0310/wanted-pre-onboarding-backend/assets/129481038/32c04d88-c082-4d0b-b41e-d2123f09c44f)

<br>

## 7. API 명세(요청, 응답 포함)
Postman의 Documentation 기능을 이용하여 API 명세를 작성하였습니다.

[배포된 API 문서 링크] https://documenter.getpostman.com/view/24688565/2s9XxzvD4b

<img width="1440" alt="스크린샷 2023-08-08 오후 5 35 14" src="https://github.com/sarasa0310/wanted-pre-onboarding-backend/assets/129481038/25dd134e-0e92-4a99-97cf-9fba3ce5071c">
