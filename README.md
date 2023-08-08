# wanted-pre-onboarding-backend
원티드 프리온보딩 백엔드 인턴십 선발 과제입니다.

필수 API 요구사항 이외에 사용자와 게시글 서비스 단위 테스트 코드 추가 및 클라우드 환경(AWS)에 배포 환경을 설계하고 애플리케이션을 배포하였습니다.

<br>

## 1. 성명
<h3>은지일</h3>

<br>

## 2. 배포된 API 주소와 설계한 AWS 환경 그림
AWS Elastic Beanstalk를 활용하여 쉽고 빠르게 배포

[API 주소] http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com

<img width="811" alt="스크린샷 2023-08-08 오후 4 17 44" src="https://github.com/sarasa0310/wanted-pre-onboarding-backend/assets/129481038/da85db15-890f-402d-a514-d6bd7471ebce">

<br>

## 3. 애플리케이션의 실행 방법(API 엔드포인트 포함)
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
- 게시글 수정은 게시글 작성자만 할 수 있습니다.
- 게시글 수정을 하기 위해서는 요청 헤더에 발급받은 JWT AccessToken을 포함시켜서 보내주세요.

**7. 특정 게시글 삭제**
- Method: DELETE
- URL: http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/articles/{articleId}
- 삭제하고 싶은 게시글의 ID를 URL 마지막에 붙여서 요청을 보내주세요.
- 게시글 삭제는 게시글 작성자만 할 수 있습니다.
- 게시글 삭제를 하기 위해서는 요청 헤더에 발급받은 JWT AccessToken을 포함시켜서 보내주세요.

<br>

## 4. 데이터베이스 테이블 구조
![원티드 인턴십 과제 ERDPNG](https://github.com/sarasa0310/wanted-pre-onboarding-backend/assets/129481038/32c04d88-c082-4d0b-b41e-d2123f09c44f)

<br>

## 5. API 동작 촬영 데모 영상 링크

## 6. 구현 방법 및 이유

<br>

## 7. API 명세(요청, 응답 포함)
Postman의 Documentation 기능을 이용하여 API 명세를 작성하였습니다.

[배포된 API 문서 링크] https://documenter.getpostman.com/view/24688565/2s9XxzvD4b

<img width="1440" alt="스크린샷 2023-08-08 오후 5 35 14" src="https://github.com/sarasa0310/wanted-pre-onboarding-backend/assets/129481038/25dd134e-0e92-4a99-97cf-9fba3ce5071c">

