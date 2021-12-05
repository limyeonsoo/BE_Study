# Reactive Programming

[Introduction to Reactive Programming - Reactive Programming with Reactor 3](https://tech.io/playgrounds/929/reactive-programming-with-reactor-3/Intro)

# Keyword

- 선언적 프로그래밍
- Reactor API (Reactor Streams)
- **asynchronous**
- **non-blocking**
- data가 available하게 되면 event를 consumer에게

~~**low-level concurrent or parallelized code  를 더이상 고려할 필요가 없음.**~~

고려 X : runnable, lock, thread , callback hell... ...

thread-safety

# Testing (StepVerifier)

[Testing](Reactive%20Programming%20a9789f8d9db949be8f8519accbbf1e4e/Testing%20da7b0f8ef1dd4d0a9746002ca3d95caf.md)

## 프로그램의 성능을 끌어올리는 방법

1. 더 많은 Thread, 더 좋은 HW, resource
+ 병렬 처리.
2. 현재 사용 중인 resource를 **효율적으로 사용**.

**⇒ 효율적으로 사용하는 방법.**

- 블로킹에 의한 리소스 낭비를 줄이자.
- 데이터를 기다리는 동안 무언가를 할 수 있어야 한다.

**⇒ 논블로킹과 비동기가 필요하다.**

## Java의 비동기 프로그래밍 모델 2가지

- Callbacks
    
    결과를 받으면 호출할 callback 파라미터(람다or익명함수)를 추가로 받는 비동기 메소드.
    
    단점, "콜백지옥"
    
- Futures
    
    곧바로 Future<T>를 반환하는 비동기 메소드.
    비동기 프로세스가 T값을 계산. → Future 객체 접근.
    즉시 사용 X, 사용 가능해질 때 까지 객체를 폴링.
    
    단점, Future 객체 여러 개를 조율하기가 힘들다.
    Future객체에 get()메소드 호출 시 블로킹된다.
    lazy computation을 지원하지 않는다.
    에러 처리가 어렵다.
    multi value 지원이 없다.
    

### Callback Example

1. User의 즐겨찾기 5개 조회
2. 없으면 추천 정보를 제공.

→ 즐겨찾기에 대한 ID를 조회한다.

이 때, callback 선언.

→ 있으면 즐겨찾기의 내용을 가져온다.

이 때, callback 선언.

→ 없으면 추천 저보를 가져온다.

이 때, callback 선언.

⇒ Reactor로 작성

```java
userService.getFavorites(userId)
	// .timeOut(Duration.ofMillis(800))
	// .onErrorResume(cacheService.cachedFavoritesFor(userId)
	.flatMap(favoriteService::getDetails)
	.switchIfEmpty(suggestionService.getSuggestions())
	.take(5)
	.publishOn(UiUtils.uiThreadScheduler())
	.subscribe(uiList::show, UiUtils::errorPopup);
```

1. getFavorites(userID)
    
    즐겨 찾기 정보를 가져온다.
    
2. flatMap(favoriteService::getDetails)
    
    받아온 즐겨 찾기 정보에 대해 Detail 정보를 가져온다.
    
3. switchIfEmpty(suggestionService.getSuggestions())
    
    받아온게 비었다면 추천 정보를 가져온다.
    
4. take(5)
    
    갯수를 5개 가져온다.
    

**< Reactor 만의 장점 > : 간단한 타임 체크**

i. timeOut(Duration.ofMillis(800)

조회할 시간을 800 미만으로 제한한다.

ii. onErrorResume(cacheService.cachedFavoritesFor(userId)

800 만에 못찾으면 cacheService에서 찾아본다.

## Reactive Streams

[GitHub - reactive-streams/reactive-streams-jvm: Reactive Streams Specification for the JVM](https://github.com/reactive-streams/reactive-streams-jvm)

: JVM기반의 라입러리

- Reactor 3
- Rxjava 2
- Akka Streams
- Vert.x
- Ratpack

등이 호환된다.

**Publicsher를 기반으로 low-level이 아니라 high-level에서 large한 범위를 커버하기를 목표로 함.**

![Untitled](Reactive%20Programming%20a9789f8d9db949be8f8519accbbf1e4e/Untitled.png)

publisher : 데이터를 보내주는 사람. → producs하면서 push events 함.

단, subscriber가 등록 되기 전까지 데이터를 보내지 않음.

subscriber는 consume하면서 feedback을 준다. ⇒ Backpressure

### specification

1. Publisher
    
    subscriber를 전달해서 subscribe함.
    
2. Subscriber
    
    subscription을 받음
    
    subscriber가 해야할 역할.
    
3. Subscription
    
    request : pull backpressure
    
4. Processor
    
    subscriber & publisher
    

## 백 프레셔

publisher가 막 data를 보내게 된다면?

subscriber에서 데이터가 막히거나 터질 수 있음.

데이터가 subscriber에 너무 많은 경우에 consumer가 몇 개만 달라고 feedback(pull)하기도 함.

### map, flatmap

- Stream 클래스
- Optional 클래스

```jsx
//flat Map
// 단일 차원 요소로 리턴 해줌.
Arrays.stream(array)
	.flatMap(inner -> Arrays.stream(inner))
	.filter(elem -> elem.equals("a"))
	.forEach(System.out::println);

// map
Arrays.stream(array)
	.map(inner -> Arrays.stream(inner))
	.forEach(elems -> elems.filter(elem -> elem.equals("a"))
		.forEach(System.out::println));
```

flatmap, map

소켓 - 서블릿 - mvc

쓰레드로컬

## test

스텝베리파이어 / 어웨이틸리티

# Reactor

⇒ Reactive Programming paradigm의 구현체!

```java
.Net -> Rx라이브러리를 만듬.
-> JVM위에서 실행하기 위해 -> RxJava가 탄생.
-> Java 표준이되어 인터페이스가 Java9 Flow class로 통합. 
```

## Iterator와의 비교

**Iterable**-**Iterator** 쌍과 성격이 유사한데, 이는 pull 기반.
`next()`를 개발자가 명령형으로 접근해야함.

**Publisher**-**Subscriber** 쌍(Reactive stream)은 push 기반.
**`onNext**()`로 publisher가 선언형으로 push를 해줌.
**`onError**()`로 에러 호출.
**`onComplete**()`로 완료 신호 호출.

## flatMap, concatMap, flatMapSequential

```java
	private List<String> toUpperCase(String s) {
		try{
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return List.of(s.toUpperCase(), Thread.currentThread().getName());
	}
```

1. flatMap
    
    ```java
    @Test
    	void contextLoads() {
    		Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i")
    				.window(3)
    				.flatMap(l -> l.map(this::toUpperCase).subscribeOn(parelle()))
    				.doOnNext(System.out.println)
    				.blockLast();
    	}
    ```
    
    3개씩 출력되고 순서를 보장받지 못함.
    
    ![Untitled](Reactive%20Programming%20a9789f8d9db949be8f8519accbbf1e4e/Untitled%201.png)
    
2. concatMap
    
    ```java
    @Test
    	void contextLoads() {
    		Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i")
    				.window(3)
    				.concatMap(l -> l.map(this::toUpperCase).subscribeOn(parelle()))
    				.doOnNext(System.out.println)
    				.blockLast();
    	}
    ```
    
    1개씩 출력되고 순서는 보장됨. 약 10초
    → parelle()의 효과를 전혀 볼 수 없음.
    
    ![Untitled](Reactive%20Programming%20a9789f8d9db949be8f8519accbbf1e4e/Untitled%202.png)
    
3. flatMapSequential
    
    ```java
    @Test
    	void contextLoads() {
    		Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i")
    				.window(3)
    				.flatMapSequential(l -> l.map(this::toUpperCase).subscribeOn(parelle()))
    				.doOnNext(System.out.println)
    				.blockLast();
    	}
    ```
    
    A, B가 출력됨가 동시에 CDEFGHI가 동시에 뜸. 약 3초
    
    ![Untitled](Reactive%20Programming%20a9789f8d9db949be8f8519accbbf1e4e/Untitled%203.png)
    

## mergeWith, concatWith, Flux.concatWith

1. mergeWith
    
    ```java
    mono1.concatWith(mono2);
    // -> mono1에 delay가 있다면 mono2가 먼저 끝난다.
    ```
    
2. concatWith
    
    → 합칠 때 순서를 보장할 수 있다.
    
    ```java
    mono1.concatWith(mono2);
    // -> Mono1 뒤에 Mono2
    ```
    
3. FLux.concatWith
    
    → Mono 여러개를 합쳐서 Flux로 만들 수 있다.
    

## Backpressure

추정?: Spring에서 Backpressure (request) 관리를 해주는 것 같음.

## expectComplete, thenCancel

```java
// Flux.just(0, 1, 2) 가 있을 때.
// StepVerifier를 이용했을 때. 3개를 다 꺼내고
expectComplete()
.verify()
// 를 해주어야 하는데, 
// 0, 1 2개만 꺼내고 싶다면.
thenCancel()
.verify()
// 로 취소해주면서 확인할 수 있음.
```

### ⇒ take & cancel

take(2)를 했을 때
take는 Flux를 2개 만들고,
upstream에 2개 지난 후 cancel을 날려서 종료함.

## Reactor의 특징.

### 쉽게 **구성** & **가독성**.

→ 여러 비동기 Task를 쉽게 조작할 수 있다는 점.

⇒ **가독성** + **유지보수성** + **재사용성**에 연결됨.

( 콜백지옥을 생각하면 쉽게 느낄 수 있음 )

### 연산자로 조작하는 **플로우.**

→ 마치 컨베이어 벨트와 같다.
공급원(Publisher)가 제공하는 원료를 소비자(Subscriber)에게 전달하도록...

이때, 중간에 결함이 생기더라도 흐름을 제어하기 쉽다.

Publisher에서 시작 → 동작 → 동작 → 동작 → ... → Subscriber에서 처리 종료.

![Untitled](Reactive%20Programming%20a9789f8d9db949be8f8519accbbf1e4e/Untitled%204.png)

### Subscribe

→ subscribe()를 해야만 데이터가 공급된다.
내부적으로는 Subscriber의 request가 upstream으로 전파되어 Publisher로 전달됨.

![Untitled](Reactive%20Programming%20a9789f8d9db949be8f8519accbbf1e4e/Untitled%205.png)

### Backpressure (컨슈머의 입장 고려)

→ 하위 워크스테이션의 처리가 느릴 때 : backpressure를 피드백으로 upstream에게 전파.

request(n) : 최대 n개를 처리할 준비가 되었다.

⭐ **⇒ 즉, push-pull 하이브리드 모델이라고 할 수 있다.
upstream에 데이터가 없다면 생산되었을 때 upstream이 push
upstream에 데이터가 잇다면 downstream이 n개를 pull**

### 높은 수준 추상화.

Hot & Cold
Reactive Stream이 Subscriber에게 반응하는 방식에 대한 구분.

- Cold
    
    시퀀스가 Subscriber마다 새로 시작.
    데이터에 HTTP 호출 래핑하고 있다면, 구독할 때 마다 HTTP 요청을 새로 만든다.
    
- Hot
    
    매번 만들지 않는다. → 나중에 구독한 구독자는 구독 이후 생산 신호를 받음.
    * 일부 Hot은 전체 or 일부 캐시 / 이전 데이터 재사용.
    * 구독자가 없을 때도 발생할 수 있음.
    
    ex) Mono.just()
    

[Advanced Features and Concepts](https://godekdls.github.io/Reactor%20Core/advancedfeaturesandconcepts/#92-hot-versus-cold)

## Flux: Asynchronous Sequene of 0-N Items

![Untitled](Reactive%20Programming%20a9789f8d9db949be8f8519accbbf1e4e/Untitled%206.png)

0-N개 비동기 시퀀스 생산하는 `표준 Publisher<T>`

→ **완료 or 에러** 신호에 의해 종료.

`onNext`   `onComplete`   `onError`  를 구독자가 이용.

onNext없이 onComplete만 잇다면 → **비어있는 유한한 시퀀스**
onComplete제거 시 → **비어있는 무한한 시퀀스**
Flux.interval(Duration)이용 시 → **일정 시간 값 방출하는 무한한 Flux<Long>** 

## Mono: Asynchronous 0-1 Result

![Untitled](Reactive%20Programming%20a9789f8d9db949be8f8519accbbf1e4e/Untitled%207.png)

`onComplete`   `onError`  신호에 종료되는 1개 아이템 생산 `Mono<T>`

Flux에서 지원하는 모든 연산자가 있진 않다.

단, 특정 연산자를 이용하여 다른 Publisher와 합쳐서 Flux로 전환 가능.

`Mono#concatWith(Publisher)`는 Flux를 반환함.

Mono<Void>를 사용하면 → **값은 없고 완료 개념만 있는 (Runnable) 비동기 처리가 가능.**

## Create Flux or Mono and subscribe to It

### <String> 시퀀스

1. just를 이용한 Flux
    
    ```java
    Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
    ```
    
2. collections를 이용한 Flux
    
    ```java
    List<String> iterale = Arrays.asList("foo", "bar", "foobar");
    Flux<String> seq2 = Flux.fromIterable(iterable);
    ```
    
3. 팩토리 메소드를 사용하는 방법
    
    ```java
    Mono<String> noData = Mono.empty();
    // 값이 없어도 제네릭 타입이 필요함!
    
    Mono<String> data = Mono.just("foo");
    
    Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
    ```
    

### <subscribe>: Java8의 람다 이용

```java
subscribe(); // (1)

subscribe(Consumer<? super T> consumer); // (2)

subscribe(Consumer<? super T> consumer,
          Consumer<? super Throwable> errorConsumer); // (3)

subscribe(Consumer<? super T> consumer,
          Consumer<? super Throwable> errorConsumer,
          Runnable completeConsumer); // (4)

subscribe(Consumer<? super T> consumer,
          Consumer<? super Throwable> errorConsumer,
          Runnable completeConsumer,
          Consumer<? super Subscription> subscriptionConsumer); // (5)
```

1. 구독하고 시퀀스를 트리거 한다.
2. 생산된 데이터에 무언가를 한다.
3. 데이터 처리 + 에러 처리도 한다.
4. 데이터 + 에러 처리를 하면서, 성공적으로 완료됐을 때 특정 코드도 실행한다.
5. 데이터 + 에러 처리 + 성공 처리를 하면서, subscribe호출로 생성되는 subscription으로 무언가를 한다.

### subscribe의 5가지 example

1. 인자 없는 기본 메소드
    
    ```java
    Flux<Integer> ints = Flux.range(1,3);
    ints.subscribe();
    ```
    
    Flux는 값 3개를 생산해낸다. (구독시점에)
    
2. 값에 대한 핸들러
    
    ```java
    ints.subscribe(i → System.out.println(i)));
    ```
    
    - 구독 시점에 값을 볼 수 있다.
3. 에러 발생 핸들러
    
    ```java
    Flux<Integer> ints = Flux.range(1, 4)
    	.map(i -> {
    			if (i<=3) return i;
    			throw new RuntimeException("Got to 4");
    	});
    ints.subscribe(i -> System.out.println(i),          // (1)
    	error -> System.err.pritnln("Error: " + error));  // (2)
    ```
    
    - Flux는 4개를 생산한다.
    - map을 이용해 각각 다른 행위를 시켜주는데,
    값이 4일 때 error를 발생시킨다.
    - 정상 값일 때는 (1)로 subscribe()
    에러 값일 때는 (2)로 subscribe() 처리를 할 수 있다.
4. 에러 핸들러 + 완료 핸들러
    
    ```java
    Flux<Integer> ints = Flux.range(1,4);
    ints.subscribe(i -> System.out.println(i),         // (1)
    	error -> System.err.println("Error " + error),   // (2)
    	() -> System.out.println("Done"));               // (3)
    ```
    
    - 구독, 에러, 완료 각각 (1), (2), (3) 핸들러로 컨트롤 할 수 있다.
    - ⭐**단, Error와 Complete는 베타적인 관계이므로, 둘 다 받을 수는 없다.**⭐
    - 완료 콜백 (3)은 입력이 없기 때문에 빈 괄호로 표현. (Runnable 인터페이스의 run과 동일)
5. Consumer<Subscription>을 받는 메소드
    
    Subscription으로 무언가를 해야만한다. 아니면 Flux는 멈춰 있다.
    
    request(long) : pull
    
    cancel() : 구독 취소
    
    ```java
    Flux<Integer> ints = Flux.range(1, 4);
    ints.subscribe(i -> System.out.println(i),
        error -> System.err.println("Error " + error),
        () -> System.out.println("Done"),
        sub -> sub.request(10));
    ```
    
    - 구독을 시작하면 `subscription`을 받는다.
    - request(10)을 했기 때문에 10개를 받는다.
    단, Flux에 4개가 있으므로 4개를 방출한다.
    

### Cancel subscribe() with Disposable

람다 기반 `subscribe()`메소드는 모두 `Disposable 타입`을 리턴한다.

`Disposable`은 구독취소할 수 있음을 의미. (`dispose()` 메소드 이용)

사용 예시)

사용자가 UI에서 새 요청을 한다면? 

→ Disposable 래퍼를 이용하여 기존 것을 취소 후 다시 생산하는 방식을 이용할 수 있다는 말.

### BaseSubscriber

subscribe 메소드에 람다 대신 (직접 조합, 로직)
→ Subscriber를 넘길 수 있다. (이미 무언가를 갖추고 있는)

확장가능한 BaseSubscriber

- 일회용 인스턴스
두 번 사용(다른 Publisher를 구독)하면 안된다는 규칙. (Reactive Stream: onNext()는 병렬로 절대 호출해선 안됨.)

< 커스텀 해보기 >

- 요청할 양을 커스텀하기 위해 BaseSubscriber를 상속.
- subscribe() 시점.
⇒ hookOnSubscribe(Subscription subscription){ }
- onNext() 시점.
⇒ hookOnNext(T value) { }

```java
package io.projectreactor.samples;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;

public class SampleSubscriber<T> extends BaseSubscriber<T> {

	public void hookOnSubscribe(Subscription subscription) {
		System.out.println("Subscribed");
		request(1);
	}

	public void hookOnNext(T value) {
		System.out.println(value);
		request(1);
	}
}
```

이 외에 

- cancel()
- requestUnbounded(): (언바운드 모드 전환)
- hookOnComplete()
- hookOnError()
- hookOnCancel()
- hookFinally()

```java
SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
Flux<Integer> ints = Flux.range(1, 4);
ints.subscribe(i -> System.out.println(i),
    error -> System.err.println("Error " + error),
    () -> {System.out.println("Done");},
    s -> s.request(10));
ints.subscribe(ss);
```

- 커스텀된 Subscriber ss를 subscribe() 했다.

< 출력 결과 >

```java
Subscribed
1
2
3
4
```

### Backpressure ways to reshape requests

request를 보냄으로써 구현.

```java
Flux.range(1, 10)
    .doOnRequest(r -> System.out.println("request of " + r))
    .subscribe(new BaseSubscriber<Integer>() {

      @Override
      public void hookOnSubscribe(Subscription subscription) {
        request(1);
      }

      @Override
      public void hookOnNext(Integer integer) {
        System.out.println("Cancelling after having received " + integer);
        cancel();
      }
    });

request of 1
Cancelling after having received 1
```

BaseSubsriber의 2가지 메소드를 재정의.

## 시퀀스를 생성하는 방법 (어려운거)

`generate`, `create`, `push`, `handle`

- generate(): Synchronous
- create() : Asynchronous & multi-thread
- push() : Asynchronous & single-thread

### generate( Supplier<S>, BiFunction<S, SynchrounousSink<T>, S>, Consumer<S> )

< 단순 예제 >

```java
Flux<String> flux = Flux.generate(
      AtomicLong::new,
			// () -> 0,
      (state, sink) -> {
          long i = state.getAndIncrement();
          sink.next("3 x " + i + " = " + 3*i);
          if (i == 10) sink.complete();
          return state;
      });
```

< 상태 객체가 반환해야할 리소스가 있을 때 >

```java
Flux<String> flux = Flux.generate(
    AtomicLong::new,
    (state, sink) -> { // (1)
        long i = state.getAndIncrement(); // (2)
        sink.next("3 x " + i + " = " + 3*i);
        if (i == 10) sink.complete();
        return state; // (3)
    }, (state) -> System.out.println("state: " + state)); // (4)
```

![Untitled](Reactive%20Programming%20a9789f8d9db949be8f8519accbbf1e4e/Untitled%208.png)

⁉️ AtomicLong은 Long wrapper 클래스
thread-safe 해서 multi-thread에서 synchronized없이 사용 가능.
동시성 보장.

### create( Consumer<? super Fluxsink<T>> emitter, backpressure )