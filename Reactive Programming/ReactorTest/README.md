# Testing

### Reactor Test를 위한 전용 아티팩트

[reactor-core/reactor-test at main · reactor/reactor-core](https://github.com/reactor/reactor-core/tree/main/reactor-test)

[StepVerifier (reactor-test 3.4.12)](https://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html)

구독 시 발생할 이벤트에 대한 기대치를 표현하여 비동기 시퀀스에 대한 검증 가능한 스크립트를 생성하는 선언적 방법.

1. create(Publisher) / withVirtualTime(Supplier<Publisher>) 를 사용하여 StepVerifier 생성.
2. expectNext, expectNextCount, expectNextSequence 등으로 기대치 설정.
3. thenRequest, thenCancel로 구독 작업 트리거.
4. expectComplete, expectError 등으로 마지막 기대치 설정.
5. verify()를 이용해 StepVerifier에 대한 검증.

## 6.0 Gradle을 이용할 때 dependencies

```java
dependencies {
	~~testCompile 'io.projectreactor:reactor-test'~~
	testImplementation 'io.projectreactor:reactor-test'
}
```

## 6.1 Reactor 테스트를 위한 시나리오

1. StepVerifier로 시퀀스가 주어진 시나리오를 따르는지 단계별 테스트.
    1. Flux / Mono를 정의해놓고, 구독하면 어떻게 되는지 테스트.
2. TestPublisher로 다운스트림 연산자를 테스트하기 위해 데이터 생산.
3. 선택 가능한 Publisher가 여럿 있는 시퀀스 검증.

## Example

### 시나리오:

Flux에서

1. thing1을 방출한다.
2. thing2를 방출한다.
3. boom이라는 에러를 생산한다.
4. 구독한 다음 검증한다.

![Untitled](Testing%20165642b37e544ec0aefdf4f2951e1040/Untitled.png)

### 테스트:

1. thing1, thing2가 있는 Flux를 정의한다.
2. 정의한 Flux를 감싸서 검증할 StepVerifier 빌더를 생성한다.
    1. 테스트할 Flux를 넘긴다.
3. 구독 후 첫 번째 신호는 thing1을 가진 onNext일거라 expect한다.
4. 구독 후 두 번째 신호는 thing2를 가진 onNext일거라 expect한다.
5. 마지막 신호는 ErrorMessage로 boom을 가진 onError일거라 expect한다.
6. verify()를 호출해서 테스트를 트리거 해준다.

## StepVerifier 빌더

Flux, Mono를 감싸 빌더를 생성한다.

시퀀스를 진행하면서 다음 신호에 대한 테스트를 한다.

- 기대값 ≠ 결과값 (expectNext(T...), expectNextCount(long))
⇒ 무조건 AssertionError와 함께 실패.
- 다음 신호 컨슘 (consumeNextWith(Consumer<T>)
⇒ 일부 시퀀스 스킵 / custom assertion을 적용할 때.
- 임의의 코드 중단 or 실행 (thenAwait(Duration), then(Runnable))
⇒ 테스트 환경에 필요한 상태나 컨텍스트 조작.

## Test에 대한 검증 trigger

- **verify()**
- verifyComplete()
- verifyError()
- verifyErrorMessage(String)
- **verifyThenAssertThat**
    
    StepVerifier.Assertions 객체를 반환.
    이 객체를 이용해서 성공적으로 끝난 시나리오의 상태를 검증할 수 있음(내부에서 verify()도 호출함)
    

### 위 메소드는 기본적으로 timeout이 없는 무한정 블로킹 메소드.

StepVerifier.setDefaultTimeout(Duration)
⇒ 전역에 타임아웃 지정.

verify(Duration)
⇒ 호출할 때 마다 지정.

## ❓ verify() & verifyComplete() 차이

![Untitled](Testing%20165642b37e544ec0aefdf4f2951e1040/Untitled%201.png)

## Test를 실패하게 만든 expectation 스텝 찾기.

1. as(String)
    
    expect* 메소드 뒤에 사용 → 이전 expectation에 대한 설명 추가.
    실패 시 에러 메세지
    단, 마지막 expectation과 verify에는 사용 X
    
    ![Untitled](Testing%20165642b37e544ec0aefdf4f2951e1040/Untitled%202.png)
    
2. StepVerifierOptions.create().scenarioName(String)
    
    이 옵션으로 StepVerifier를 만들면 scenarioName 메소드로 전체 시나리오 이름 지정.
    이 이름이 assertion 에러 메세지에 사용.
    
    ![Untitled](Testing%20165642b37e544ec0aefdf4f2951e1040/Untitled%203.png)
    

## 6.2 Manipulating Time

오랜 시간이 걸리는 코드를 기다리지 않고 테스트.

- StepVerifier.withVirtualTime

## 6.3 Performing Post-execution Assertions with StepVerifier

`verifyThenAssertThat()` 이용.

→ StepVerifier.Assertions 객체를 반환.
이 객체를 이용해서 성공적으로 끝난 시나리오의 상태를 검증할 수 있음(내부에서 verify()도 호출함)

![Untitled](Testing%20165642b37e544ec0aefdf4f2951e1040/Untitled%204.png)

## 6.4 Testing the Context

### Context를 전파하는 2가지 expectation

1. **expectAccessibleContext**
    
    전파한 Context관련 expectation을 세팅할 수 있는 ContextExpectations객체 반환.
    세팅 후 then() 필요. then()으로 시퀀스 expectation으로 돌아감.
    
2. **expectNoAccessibleContext**
    
    테스트 하는 동안 연산자 체인에서 Context가 전파되지 않는다는 expectation 세팅.
    Publisher가 Reactor의 publisher가 아닐 때 or Context를 전파할 연산자가 없을 때.
    

### Context?

명령형 프로그래밍 → 리액티브 프로그래밍으로 전환 시에 ThreadLocal에 의존하게 되어 어려움을 겪는다.
이를 해결하기 위해 Reactor 3.1.0부터 ThreadLocal과 비슷하지만, Thread 대신 Flux나 Mono에 적용할 수 있는 고급 기능을 제공.

```java
String key = "message";
Mono<String> r = Mono.just("Hello")
                .flatMap( s -> Mono.subscriberContext()
                                   .map( ctx -> s + " " + ctx.get(key)))
                .subscriberContext(ctx -> ctx.put(key, "World"));

StepVerifier.create(r)
            .expectNext("Hello World")
            .verifyComplete();
```

```java
StepVerifier.create(Mono.just(1).map(i -> i + 10),
    StepVerifierOptions.create().withInitialContext(Context.of("foo", "bar"))) // (1)
    **.expectAccessibleContext() // (2)
    .contains("foo", "bar") // (3)**
    .then() // (4)
    .expectNext(11)
    .verifyComplete(); // (5)
```

### ThreadLocal?

Thread 지역변수. set, get, remove “안정적임"But, ThreadLocal에 의존하면 리액티브 프로그래밍에 실패 할 수 있음.

![Untitled](Testing%20165642b37e544ec0aefdf4f2951e1040/Untitled%205.png)

![Untitled](Testing%20165642b37e544ec0aefdf4f2951e1040/Untitled%206.png)

## 6.5 Manually Emitting with TestPublisher

- 데이터 소스를 완전 통제하여, 테스트 하고 싶은 상황과 근접한 신호를 트리거.
- 연산자를 직접 구현 → 리액티브 스트림 스펙을 잘 따르는지 검증.

flux(), mono() 메소드를 이용하여 Flux나 Mono로 전환 할 수 있다.

TestPublisher 클래스 사용.

- `next(T)` & `next(T, T...)`: 1~n `onNext` 신호 트리거.
- `emit(T)`: `onNext` 신호 트리거 + `complete()`
- complete(): onComplete 신호와 함께 종료.
- error(Throwable): onError 신호와 함께 종료.

![Untitled](Testing%20165642b37e544ec0aefdf4f2951e1040/Untitled%207.png)

< 팩토리 메소드 이용하여 TestPublisher 생성 >

- create() → 잘 동작하는 TestPublisher
- createNoncompliant() → 실패하는 TestPublisher
    
    TestPublisher.Violation 열거형 값 한 개 이상을 받는다.
    
    - REQUEST_OVERFLOW
        
        요청이 충분하지 않을 때도 IllegalStateException 대신 next 호출 허용.
        
    - ALLOW_NULL
        
        null 값이 들어오면 NullPointerException 대신 next 호출 허용.
        
    - CLEANUP_ON_TERMINATE
        
        row 하나에서 종료 신호 complete(), error(), emit() 여러 번 허용.
        
    - DEFER_CANCELLATION
        
        취소 신호 무시, 계속 신호 방출 허용.
        

```java
TestPublisher<T> testPublisher = TestPublisher.createNoncompliant(Violation.CLEANUP_ON_TERMINATE);
```

TestPublisher는 구독 이후 내부 상태 값을 가지고 있어서 assert* 메소드로 검증할 수 있다.

## 6.6 PublisherProbe

- .then() : 결과값은 잊어버리고, 오직 성공했는지만 확인.
- Mono<void>: 아무 데이터도 방출 하지 않음.

### 프로브를 생성하여 Mono<Void> 자리를 대체할 수 있다.

그 후 probe를 이용하여 검증

- 구독되었는지 확인.
- 실제 데이터 요청했는지 확인.
- 취소 여부 확인.

![Untitled](Testing%20165642b37e544ec0aefdf4f2951e1040/Untitled%208.png)