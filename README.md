# 📷 Photogram - Instagram Clone (Spring Boot)

Photogram은 Spring Boot 기반의 **인스타그램 클론 웹 애플리케이션**으로, 실전 소셜 플랫폼 개발 경험과 백엔드 기술 스택을 포트폴리오 형태로 정리한 프로젝트입니다.

> 💼 **제작 목적**: 백엔드 개발자로서의 기술 스택 내재화 및 실전 클론 프로젝트 경험 축적  
> 📅 **개발 기간**: 2024년 7월 ~ 11월  
> 🧑‍💻 **개발 범위**: 백엔드 100% 직접 개발

---

## 🛠️ 주요 기능

| 기능            | 설명 |
|-----------------|------|
| 회원가입 / 로그인 | 일반 로그인 및 소셜 로그인 (Google OAuth2) |
| 피드 보기       | 로그인 후 사용자 피드 및 인기 이미지 조회 |
| 게시물 업로드   | 이미지 파일 업로드, 설명 추가 |
| 좋아요 / 댓글   | 게시물 좋아요, 댓글 작성/삭제 |
| 팔로우 기능     | 사용자 간 팔로우/언팔로우 |
| 마이페이지      | 본인 프로필, 게시물 목록, 팔로워/팔로잉 수 |

---

## 🔧 기술 스택

- **Language**: Java 11
- **Framework**: Spring Boot 2.x
- **ORM**: JPA (Hibernate)
- **Build Tool**: Maven
- **Security**: Spring Security, OAuth2
- **Database**: H2 (개발용), MySQL (운영용 변경 가능)
- **Template**: JSP / JSTL
- **Tools**: Lombok, Servlet Filter, AOP, Validation

---

## 📁 프로젝트 구조

```bash
photogram/
├── config/                  # 보안 및 웹 설정
├── config/auth/             # 사용자 인증 처리 (UserDetails 등)
├── config/oauth/            # OAuth2 로그인 처리
├── domain/                  # JPA Entity 및 Repository
│   ├── comment/             # 댓글 엔티티 및 CRUD
│   ├── image/               # 이미지 업로드/조회
│   ├── likes/               # 좋아요 기능
│   ├── subscribe/           # 팔로우/언팔로우
│   └── user/                # 사용자 정보
├── dto/                     # 데이터 전달용 객체
├── handler/                 # 예외 처리 및 응답 래퍼
├── service/                 # 비즈니스 로직
├── util/                    # 커스텀 유틸리티 (ex: 파일 저장)
└── PhotogramStartApplication.java
