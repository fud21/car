# 🖥️ WEB - 관리자 웹 시스템

이 디렉토리는 GuardPlatform 프로젝트의 **웹 관리자 시스템**을 담당하는 Spring Boot 기반 서버입니다.  
관리자는 이 웹 시스템을 통해 **미등록 차량 경고 내역을 조회하고**, **차량 등록 여부를 관리**할 수 있습니다.

---

## 📌 주요 기능

- ✅ 관리자 로그인 (JWT 기반 인증)
- 🚗 차량 등록 여부 확인
- 🚨 미등록 차량 경고 기록 저장
- 🔎 경고 내역 필터링 (날짜, 차량 번호, 위치)
- 📄 RESTful API 제공

---

## 🛠️ 기술 스택

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- JPA (Hibernate)
- MySQL
- Gradle

---

## 📁 디렉토리 구조

```
WEB/
├── build.gradle
├── settings.gradle
├── gradle/
├── gradlew
├── gradlew.bat
├── HELP.md
├── README.md
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
```

---

## 🚀 실행 방법

### 1. 환경 구성

- `src/main/resources/application.yml` 파일에 DB 정보와 JWT 시크릿 키 설정
- 로컬 DB 기반 MySQL 서버 구성

### 2. 실행

```bash
cd WEB
./gradlew bootRun
```

서버는 기본적으로 `http://localhost:8080`에서 실행됩니다.

---

## 📮 API 예시

| 메서드 | 엔드포인트 | 설명 |
|--------|-------------|------|
| POST   | `/login`    | 관리자 로그인(JWT 반환) |
| POST   | `/vehicle`  | 차량 등록 |
| GET    | `/warnings` | 전체 경고 목록 조회 |
| GET    | `/warnings?date=2024-01-01` | 날짜별 필터링 |
| GET    | `/warnings?plate=12가3456` | 특정 차량의 경고 조회 |
| GET    | `/warnings?location=Gate1` | 위치별 경고 조회 |

---

## 🔐 인증 흐름
- `/login` 요청으로 JWT 토큰 발급
- 이후 모든 API 요청 시 `Authorization: Bearer <token>` 헤더 필요

---

## ⚠️ 에러 응답 예시
- 토큰 만료 시: `401 Unauthorized`
- 등록되지 않은 차량: `400 Bad Request` 등
