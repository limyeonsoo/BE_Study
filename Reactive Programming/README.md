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

![Untitled](Reactive%20Programming%2087d596702fca451ba9d5891edfada2ad/Untitled.png)

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
    
    ![Untitled](Reactive%20Programming%2087d596702fca451ba9d5891edfada2ad/Untitled%201.png)
    
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
    
    ![Untitled](Reactive%20Programming%2087d596702fca451ba9d5891edfada2ad/Untitled%202.png)
    
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
    
    ![Untitled](Reactive%20Programming%2087d596702fca451ba9d5891edfada2ad/Untitled%203.png)
    

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