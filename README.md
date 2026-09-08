# 📷 Photogram — Spring Boot 인스타그램 클론

Spring Boot 기반 인스타그램 클론 웹 애플리케이션입니다.
회원가입·소셜 로그인부터 사진 업로드, 좋아요, 댓글, 팔로우까지 소셜 피드의 기본 동선을 백엔드부터 구현했습니다.

> **개발 기간**: 2024년 7월 ~ 11월
> **기반**: 최주호 강사의 Spring Boot 시큐리티 클론 코딩 강의를 따라가며 만들었고,
> 그 위에 직접 고치고 추가한 부분이 있습니다. 아래 [직접 구현한 부분](#직접-구현한-부분)에 정리했습니다.

---

## 🛠️ 주요 기능

| 기능 | 설명 |
|------|------|
| 회원가입 / 로그인 | 폼 로그인 + Facebook OAuth2 소셜 로그인 |
| 피드 | 팔로우한 사용자의 사진 피드, 무한 스크롤 페이징 |
| 인기 페이지 | 좋아요 수 기준 인기 사진 목록 |
| 사진 업로드 | 이미지 파일 업로드, 설명 추가 |
| 좋아요 | 좋아요 / 취소, 카운트 표시 |
| 댓글 | 작성, 삭제, 유효성 검사 |
| 팔로우 | 팔로우 / 언팔로우, 구독자 목록 모달 |
| 프로필 | 본인·타인 프로필, 게시물 목록, 팔로워·팔로잉 수, 프로필 사진 변경 |

---

## 직접 구현한 부분

강의를 따라간 뒤 직접 잡거나 붙인 것들입니다. 커밋으로 확인할 수 있습니다.

### 버그 수정

| 문제 | 수정 | 커밋 |
|------|------|------|
| 프로필의 **"회원정보 변경"** 버튼이 `/user/1/update` 로 하드코딩돼 있어, 어떤 계정으로 로그인하든 1번 유저의 수정 페이지로 이동 | `/user/${dto.user.id}/update` 로 변경 | `6256ade` |
| 정보수정 화면의 프로필 이미지가 `src="#"` 로 되어 있어 **항상 깨진 이미지**로 표시 | 실제 업로드 경로(`/upload/${principal.user.profileImageUrl}`)를 바라보도록 수정 | `6256ade` |

### 기능 추가

| 추가 | 내용 | 커밋 |
|------|------|------|
| 스토리 작성자 → 프로필 이동 | 피드에서 작성자 이름을 누르면 해당 계정 프로필로 이동 | `6256ade` |
| 댓글 작성자 → 프로필 이동 | 댓글에 달린 이름을 누르면 그 계정 프로필로 이동 | `ffb9bf8` |
| 댓글 표시명 변경 | 댓글에 `username` 대신 `name` 을 노출 | `6256ade` |

강의 범위 안에서 구현한 것 중에도 손이 많이 간 부분이 있습니다 — **AOP 기반 유효성 검사 자동화**(`ValidationAdvice`, `020400e`)로 각 컨트롤러에 흩어져 있던 `BindingResult` 처리를 걷어냈고, **좋아요 기능의 JPA 무한 참조**를 잡았습니다(`536cf49`).

---

## 🔧 기술 스택

| 항목 | 내용 |
|------|------|
| Language | **Java 21** |
| Framework | **Spring Boot 3.3.5** |
| Build | Maven |
| ORM | Spring Data JPA (Hibernate) |
| Security | Spring Security, OAuth2 Client, spring-security-taglibs |
| Database | **MariaDB** |
| View | JSP / JSTL (Tomcat Embed Jasper) |
| 기타 | Lombok, Spring AOP, Bean Validation, QLRM(네이티브 쿼리 매핑), Actuator, DevTools |

---

## 📁 프로젝트 구조

```
photogram/src/main/java/com/cos/photogramstart/
├── config/              # 보안 및 웹 설정
│   ├── auth/            # UserDetails, PrincipalDetails
│   └── oauth/           # OAuth2 로그인 처리
├── domain/              # JPA 엔티티 및 Repository
│   ├── comment/
│   ├── image/
│   ├── likes/
│   ├── subscribe/
│   └── user/
├── handler/             # 예외 처리
│   ├── aop/             # ValidationAdvice (유효성 검사 자동화)
│   └── ex/              # 커스텀 예외
├── service/             # 비즈니스 로직
├── util/                # Script 유틸
└── web/                 # 컨트롤러
    ├── api/             # REST API 컨트롤러
    └── dto/             # 요청·응답 DTO
```

---

## ⚙️ 실행 방법

### 1. 클론

```bash
git clone https://github.com/JeongSangMyeong/Spring-boot-Photogram.git
cd Spring-boot-Photogram/photogram
```

### 2. 데이터베이스

MariaDB에 `photogram` 스키마를 만들고 접속 계정을 준비합니다.

```sql
CREATE DATABASE photogram;
```

### 3. 설정

`src/main/resources/application.yml` 의 접속 정보와 업로드 경로를 환경에 맞게 수정합니다.

```yaml
spring:
  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mariadb://localhost:3306/photogram?serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false
    username: <사용자>
    password: <비밀번호>

file:
  path: <업로드 파일을 저장할 디렉터리 경로>
```

`ddl-auto: update` 로 설정돼 있어 첫 실행 시 테이블이 생성됩니다.

### 4. 실행

```bash
./mvnw spring-boot:run
```

`http://localhost:8080` 으로 접속합니다.

---

## 알려진 한계

- **소셜 로그인은 추가 설정이 필요합니다.** OAuth2 처리 코드(`Oauth2DetailsService`)는 있지만 `application.yml` 에 `spring.security.oauth2.client.registration` 블록이 없어, 현재 상태 그대로는 Facebook 로그인이 동작하지 않습니다. Facebook 앱을 등록하고 클라이언트 정보를 환경변수로 주입해야 합니다. (자격증명을 저장소에 커밋하지 않으려고 비워둔 상태입니다.)
- 업로드 경로가 `application.yml` 에 절대 경로로 하드코딩돼 있어 환경마다 수정이 필요합니다.
- 테스트는 Spring Initializr가 만든 컨텍스트 로딩 스텁만 있습니다.
- 강의 제공 샘플 이미지 2개(약 7MB)가 저장소에 포함돼 있습니다.
