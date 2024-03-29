![](https://img.shields.io/badge/내일배움캠프-Spring팀과제-white.svg)
:desktop_computer: 프로젝트 소개
---
+ 프로젝트 명 : kanban (스케줄링 어플리케이션)
+ 소개
    + 한 줄 정리 : 고객의 스케줄링을 편리하고 간편하게 정리하는 서비스입니다.
    + 내용 : 하나의 프로젝트를 관리하는 보드를 생성하여 섹션과 카드로 스케줄링이 가능합니다.
            섹션의 순서를 변경할 수 있고, 카드도 다른 섹션을 넘어 자유롭게 이동 가능합니다.
            카드에 대한 댓글을 작성하여 협업하는 사람들과 토론할 수 있습니다.
            또한 카드에 마감일을 설정해 관리할 수 있습니다.
---
# Development
## 디렉토리 구조
```bash
├── src                           # 소스 코드가 위치하는 디렉토리
│   ├── main                      # 메인 소스 코드가 위치하는 디렉토리
│   │   ├── java                  # 자바 소스 코드가 위치하는 디렉토리
│   │   │   ├── com.team8.kanban  # 기본 패키지 이름
│   │   │   │   ├── domain        # 도메인 디렉토리
│   │   │   │   │   ├── board     # 보드 관련 디렉토리
│   │   │   │   │   ├── card      # 카드 관련 디렉토리
│   │   │   │   │   ├── comment   # 댓글 관련 디렉토리
│   │   │   │   │   ├── section   # 섹션 관련 디렉토리
│   │   │   │   │   └── user      # 유저 관련 디렉토리
│   │   │   │   └── global        # 설정, 예외 처리 관련 디렉토리
│   │   │   │       ├── aop       # aop 관련 디렉토리
│   │   │   │       ├── common    # 공통 응답 객체 관련 디렉토리
│   │   │   │       ├── config    # 환경 설정 관련 디렉토리
│   │   │   │       ├── entity    # 공통 사용 객체 디렉토리
│   │   │   │       ├── exception # 예외 처리 관련 디렉토리
│   │   │   │       ├── jwt       # jwt 관련 디렉토리
│   │   │   │       └── security  # 보안 관련 디렉토리
│   │   └── resources             # 리소스 파일들이 위치하는 디렉토리
│   └── test                      # 테스트 소스 코드가 위치하는 디렉토리
├── build.gradle                  # Gradle 빌드 스크립트 파일
├── settings.gradle               # Gradle 설정 파일
└── README.md                     # 프로젝트에 대한 설명이 담긴 마크다운 파일
```
## :gear: 개발 환경
+ JDK amazoncorretto 17.0.9
+ Spring Boot 3.2.3
+ Gradle 8.5
+ MySQL 8.0.35
+ Apache Tomcat 10.1.19
+ bootstrap 5.2.3
+ QueryDSL 5.0.0

## Code Convension
+ Default

# API
https://www.notion.so/teamsparta/131b283096bd479991c4754142676a83?v=9ad0e2f3289a4dcfae4147a635bfe636&pvs=4

# ERD
https://www.erdcloud.com/d/rMKvHsWDrn25WxFn

# 진행 상황

## 필수 기능 구현
- [x]  **사용자 인증 기능**
    - [x] 회원가입 기능
    - [x] 로그인 기능
    - [ ] 프로필 조회 기능
    - [ ] 회원 탈퇴 기능
- [x]  **보드 관리 기능**
    - [x] 보드 작성 작성 기능
    - [x] 보드 단건 조회 기능
    - [x] 보드 전체 조회 기능
    - [x] 보드 수정 기능
    - [x] 보드 삭제 기능
    - [x] 보드 작업자 초대 기능
- [x]  **섹션 관리 기능**
    - [x] 섹션 작성 기능
    - [x] 섹션 수정 기능
    - [x] 섹션 삭제 기능
    - [x] 섹션 전체 조회 기능
    - [x] 섹션 위치 변경 기능
- [x]  **카드 관리 기능**
    - [x] 카드 생성 기능
    + 컬럼 내부에 카드 생성
    - [x] 카드 수정 기능
    + 카드 이름
    + 카드 설명
    + 작업자 할당
    + 작업자 변경

  
  - [x] 카드 삭제 기능
  - [x] 카드 섹션 변경 기능
  - [x] 카드 포지션 변경 기능
- [x]  **카드 상세 기능**
    - [x] 날짜 지정 기능
    + 카드에 마감일 설정 및 관리
- [x]  **댓글 관리 기능**
  + 작업자들과 카드에 대한 토론 기능
    - [x] 댓글 작성 기능
    - [x] 댓글 수정 기능
    - [x] 댓글 삭제 기능
    - [x] 댓글 조회 기능
    - [x] 댓글 단건 조회

