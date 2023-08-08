# wanted-pre-onboarding-backend
원티드 프리온보딩 백엔드 인턴십 선발 과제

<br>

## 1. 성명
<h3>은지일</h3>

<br>

## 2. 배포된 API 주소와 설계한 AWS 환경 그림
AWS Elastic Beanstalk를 활용하여 쉽고 빠르게 배포

[API 주소] http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com

<img width="811" alt="스크린샷 2023-08-08 오후 4 17 44" src="https://github.com/sarasa0310/wanted-pre-onboarding-backend/assets/129481038/da85db15-890f-402d-a514-d6bd7471ebce">

<br>

## 3. 애플리케이션의 실행 방법

<br>

### API 엔드포인트
**1. 회원가입**
- POST http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/members

**2. 로그인**
- POST http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/login

**3. 게시글 생성**
- POST http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/articles

**4. 게시글 목록 조회**
- GET http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/articles

**5. 특정 게시글 조회**
- GET http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/articles/{articleId}

**6. 특정 게시글 수정**
- PATCH http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/articles/{articleId}

**7. 특정 게시글 삭제**
- DELETE http://wanted-internship-app.ap-northeast-2.elasticbeanstalk.com/articles/{articleId}

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

