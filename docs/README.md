# 비즈니스 요구사항과 설계

---

### 회원

- 회원을 가입하고 조회할 수 있다.
- 회원은 일반과 VIP 두 가지 등급이 있다.
- 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다. (미확정)

---

### 주문과 할인 정책

- 회원은 상품을 주문할 수 있다.
- 회원 등급에 따라 할인 정책을 적용할 수 있다.
- 할인 정책은 모든 VIP는 1,000원을 할인해주는 고정 금액 할인을 적용해달라. (추후 변경 가능)
- 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못 했고, 오픈 직전까지 고민을 미루고 싶다. 최악의 경우 할인을 적용하지 않을 수도 있다.(미확정)

---

### 회원 도메인 설계

#### 회원 도메인 요구사항

- 회원을 가입하고 조회할 수 있다.
- 회원은 일반과 VIP 두 가지 등급이 있다.
- 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다. (미확정)

---

#### 회원 도메인 협력 관계

```mermaid
classDiagram
    클라이언트 --> 회원서비스
    회원서비스 --> 회원저장소
    회원저장소 <.. DB저장소
    회원저장소 <.. 메모리저장소
    회원저장소 <.. 외부시스템연동회원저장소
    class 클라이언트
    class 회원서비스 {
        -회원가입기능()
        -회원조회기능()
    }
    class 회원저장소
    <<interface>> 회원저장소
    class 메모리저장소
    class DB저장소
    class 외부시스템연동회원저장소
```

---

#### 회원 클래스 다이어그램

```mermaid
classDiagram
    MemberService <|.. MemberServiceImpl
    MemberServiceImpl --> MemberRepository
    MemberRepository <|.. MemoryMemberRepository
    MemberRepository <|.. DbMemberRepository
    class MemberService
    <<interface>> MemberService
    class MemberServiceImpl
    class MemberRepository
    <<interface>> MemberRepository
    class MemoryMemberRepository
    class DbMemberRepository
```

---

#### 회원 객체 다이어그램

```mermaid
flowchart LR
    클라이언트 --> 회원서비스 --> 메모리회원저장소
```

---


#### 주문 도메인 협력, 역할, 책임

```mermaid
flowchart LR
    클라이언트 -- 1 주문생성 --> 주문서비스역할 -- 2 회원조회 --> 회원저장소역할
    주문서비스역할 -- 3 할인적용 --> 할인정책역할
    주문서비스역할 -- 4 주문결과반환 --> 클라이언트
```


---

#### 주문 도메인 전체

```mermaid
flowchart LR
    클라이언트 ==> 주문서비스역할 --> 회원저장소역할
    메모리회원저장소 --> 회원저장소역할
    DB회원저장소 --> 회원저장소역할
    주문서비스역할 --> 할인정책역할
    주문서비스구현체 --> 주문서비스역할
    정액할인정책 --> 할인정책역할
    정률할인정책 --> 할인정책역할
```

---

#### 주문 도메인 클래스 다이어그램

```mermaid
classDiagram
    OrderService <|.. OrderServiceImpl
    MemberRepository <|.. MemoryMemberRepository
    MemberRepository <|.. DbMemberRepository
    DiscountPolicy <|.. FixDiscountPolicy
    DiscountPolicy <|.. RateDiscountPolicy

    OrderServiceImpl --> MemberRepository
    OrderServiceImpl --> DiscountPolicy
    class OrderService
    <<interface>> OrderService
    class OrderServiceImpl

    class MemberRepository
    <<interface>> MemberRepository
    class MemoryMemberRepository
    class DbMemberRepository

    class DiscountPolicy
    <<interface>> DiscountPolicy
    class FixDiscountPolicy
    class RateDiscountPolicy
```


---

