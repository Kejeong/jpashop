# JPA Shop

Spring Boot와 JPA의 핵심 개념을 학습하기 위해 만든 간단한 쇼핑몰 예제입니다. 회원과 도서 상품을 웹 화면에서 관리하며, 주문·재고·배송 흐름은 도메인 모델과 서비스 계층으로 구현했습니다.

## 학습 목표

- JPA 엔티티 매핑과 연관관계 설정
- 엔티티 중심의 비즈니스 로직 설계
- 서비스 계층의 트랜잭션 처리와 변경 감지(Dirty Checking)
- Spring MVC, Thymeleaf를 이용한 서버 사이드 렌더링
- Bean Validation을 이용한 폼 검증

## 기술 스택

| 구분 | 사용 기술 |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 4.1.1, Spring MVC |
| ORM | Spring Data JPA, Hibernate |
| View | Thymeleaf, Bootstrap |
| Database | H2 Database |
| Build | Gradle 9.7.1 (Wrapper) |
| Test | JUnit 5 |

## 구현 범위

### 웹 화면

- 회원 가입 및 회원 목록 조회
- 도서 상품 등록, 목록 조회, 수정
- 회원 가입 폼의 필수 이름 값 검증

### 도메인·서비스 계층

- 회원 이름 중복 가입 방지
- 주문 생성 시 주문 상품, 배송 정보, 주문 상태 생성
- 주문 취소 시 재고 복구
- 재고 부족 시 `NotEnoughStockException` 발생
- 배송 완료 상품의 주문 취소 방지
- 상품 수정 시 JPA 변경 감지로 업데이트

> 주문 기능은 `OrderService`와 도메인 엔티티에 구현되어 있으며, 주문/취소를 호출하는 웹 컨트롤러와 화면은 아직 추가하지 않았습니다.

## 프로젝트 구조

```text
src
├── main
│   ├── java/jpabook/jpashop
│   │   ├── controller    # 웹 요청 및 폼 처리
│   │   ├── domain        # 엔티티, 값 타입, 도메인 로직
│   │   ├── exception     # 재고 부족 예외
│   │   ├── repository    # EntityManager 기반 저장소
│   │   └── service       # 트랜잭션 및 유스케이스
│   └── resources
│       ├── templates     # Thymeleaf 화면
│       └── application.yml
└── test                  # 서비스 및 애플리케이션 테스트
```

## 실행 방법

### 1. 사전 요구 사항

- JDK 21
- H2 Database Server

개발 프로필은 다음 TCP 주소의 H2 데이터베이스에 연결하도록 설정되어 있습니다.

```yaml
jdbc:h2:tcp://localhost/~/jpashop
```

따라서 애플리케이션을 실행하기 전에 H2 Server를 시작해야 합니다. H2 JAR가 있는 위치에서 다음과 같이 실행할 수 있습니다.

```bash
java -cp h2-*.jar org.h2.tools.Server -tcp
```

### 2. 애플리케이션 실행

```bash
./gradlew bootRun
```

실행 후 브라우저에서 `http://localhost:8080`에 접속합니다.

| 경로 | 기능 |
| --- | --- |
| `/` | 홈 |
| `/members/new` | 회원 가입 |
| `/members` | 회원 목록 |
| `/items/new` | 도서 상품 등록 |
| `/items` | 상품 목록 |
| `/items/{itemId}/edit` | 도서 상품 수정 |

## 테스트 실행

```bash
./gradlew test
```

테스트는 별도 설정 파일(`src/test/resources/application.yml`)의 인메모리 H2 데이터베이스를 사용하며, 실행 후 데이터가 남지 않습니다.