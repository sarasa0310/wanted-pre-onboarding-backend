# wanted-pre-onboarding-backend
원티드 프리온보딩 백엔드 인턴십 선발 과제 제출용 레포지토리

## 1. 성명
**은지일**

<br>

## 2. 애플리케이션의 실행 방법

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

## 3. 데이터베이스 테이블 구조
![원티드 인턴십 과제 ERDPNG](https://github.com/sarasa0310/wanted-pre-onboarding-backend/assets/129481038/b28b55d1-45dc-47d5-a113-cb2ec49976f1)
