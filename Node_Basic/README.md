# Node.js

# 장점

1. JavaScript : 전 세계에서 가장 많이 쓰는 언어.
2. Json 형태를 주로 사용 : mongoDB, express, AngularJS, Node.js 등

    ps) 마샬링 : 서버에서 데이터를 다른 포멧으로 바꾸는 작업을  인코딩/디코딩 보다 마샬링/언마샬링이라고 함.

    RDBMS 는 Read에 최적화 (Select) 

    NoSQL은 Write에 최적화 

3. 싱글 스레드 (Single Thread)

    공통된 자원에 관한 락 관리에 대한 부담 X.

    *** 실제로 CPU 마다 여러 쓰레드를 띄울 수 있지만, 위 특징을 부각하기 위해 싱글 스레드라 부름.**

4. NPM (Node Package Manager)

    대부분의 library가 다 있다.

    ps) module count라는 사이트에 의하면 Java보다 2배 이상 많은 모듈을 보유하고 있다고 알려져있다.

# 단점

1. Callback hell
2. JavaScript는 객체지향을 흉내낸 언어로서 새로운 공부가 필요할 수 있다.

# 활용

0. Socket 통신에 대해서는 최강이다.

1. Network Program
2. Tools
3. Robot
4. Desktop Application

- Rest + JSON APIs
- Single-page web app
- Real-time web app
- 빠른 prototyping
- 많은 I/O 처리.

## 프레임워크 별 벤치마킹 : [https://www.techempower.com/benchmarks/](https://www.techempower.com/benchmarks/)

- **JSON :**

    Django < Spring < Node < Netty (Java를 성능을 위해 비동기로 긴 코드로 짠 프레임워크)

- **Single Query :**

    DB에 대한 쿼리 작업.

- **Multiple Query :**

    하나의 Rest API에 대해 여러 쿼리를 날린 작업.

    Node.js의 성능이 확 떨어짐.

## Example)

- Paypal

    5 Core Java 보다 1 Core Node가 빠르더라.
    개발자 수 12 Java 보다 2 Node로 효율적이더라.

# 프레임워크에 대한 정확한 정의.

GoF 디자인 패턴 랄프의 정의.

라이브러리

함수 들에 대한 control flow를 사용자가 모두 제어 한다면.

프레임워크

Control flow를 프레임워크에서 제어 한다.
필요 시 오버라이딩 해서 사용해야 한다.

라이브러리를 공부한다면 코드 적으로 공부하면되지만,
프레임워크를 공부한다면 프레임워크의 흐름, 로직 등을 이해하는 과정이 필요하다.

# 나쁜 활용

- CPU 부하가 걸리는 작업

    ### ⇒ GO Lang

    2kb의 경량화된 쓰레드로 이루어진 언어.
    실제로 cpu에 부하를 걸기 위해서 만들어진 언어.
    cpu 핸들링 or 쓰레드 핸들링을 해야한다면 가장 강력하다.
    정렬을 이길 수 없다. 
    Java 정렬 12초 vs Go 정렬 2초

- Multi-thread app
- 큰 데이터 연산
- 복잡한 비지니스 로직

    ### ⇒ Java

    복잡한 비즈니스 로직, 개발자 간의 이해관계 향상 을 위해서 가장 강력하다.
    배달의 민족, 등 복잡한 프로세스에는 Java가 필수.

# Backend 구축 시 베이스

![Untitled](https://user-images.githubusercontent.com/42796949/115419938-b515c000-a235-11eb-81e0-99b5da3713a5.png)

**성능 우수 :  비동기 + 넌블로킹 + 프로액터**

### Window IOCP vs Linux EPOLL

10000개의 세션에 대해 

IOCP는 100% 처리 할 때,
EPOLL는 95% 정도.

CPU 사용량은 IOCP가 50% 정도 → 성능이 더 나올 가능성이 충분하다.

윈도우는 여기서 그치지 않았다.

### IOCP 향상 ⇒ RIO (Registered IO)

Java 저수준 - Netty라면
C 저수준 → Window RIO

바로 사용할 수 있는 다이렉트 메모리 페이지를 할당 받아놓고 유저랑 연결.

→ Context Switching이 6배 줄어듬.

→ CPU 사용량도 2배 줄어듬.

Node.js는 윈도우에서 더 Proactor 받아서 성능이 70퍼 가량 향상.

Linux에서는 100개 처리 할 때 Window에서 170개 처리.

세 가지 축에 대한 완벽한 이해가 필요함.

## 동기 비동기

클라이언트 - 서버 간 응답 프로세스
응답을 바로 기다릴 것이냐, 콜백으로 받을 것이냐

Spring은 대부분 동기적인 코드. (간결)

비동기는 대부분 복잡한 코드

## 블로킹 넌블로킹

서버 운영체제 안에서  유저 레벨 / 커널 레벨

유저 → 커널 레벨로 맡길 때, 유저 레벨에서 기다리면 블로킹
유저 → 커널 레벨로 맡길 때, 다른 작업을 할 수 있다면 넌블로킹

![Untitled 1](https://user-images.githubusercontent.com/42796949/115419958-b8a94700-a235-11eb-8fb3-7175d1c9623e.png)

파일, 디비, 등 대부분 I/O

## Reactor

I/O 작업에 대한 요청을 User 레벨에서 받고 Parsing 후 Kernel 레벨로 내려주는 것.

## Proactor

I/O 작업에 대한 요청이 들어오면 Kernel에서 받고, 요청을 다 처리 한 후, User 레벨로 올려주는 것.

### proactor를 지원하는 운영체제 : Window

게임 회사에서 게임을 윈도우를 이용하여 개발하는 이유가 이것.
⇒ IOCP ( Input Output Completion Port )

## ‼️Java에서 비동기 + 넌블로킹 으로 단순 Read하는 코드

[https://www.javacodegeeks.com/2012/08/io-demystified.html](https://www.javacodegeeks.com/2012/08/io-demystified.html)

Netty를 안쓰는 이유 : 코드가 너무 복잡함.

Django 는 넌블로킹 제공을 할까? 안할듯...

### Node Example Asynchronous

예제 

db.connect()를 한다 했을 때 Node에서 동기로 해버리면, connect 되기 전에 find를 해서 error가 날 수 있음.
callback으로 connect된 후, find 할 수 있도록 해야함.

Java는 1 Client 당 1 Thread

PHP는 1 Client 당 1 Process

Node는 1 Thread (CPU에 따라) 로 모두 해결.

## I/O 관계

동기 : 1개 Thread로 처리.

Fork : 1개 처리 당 1개의 프로세스

Thread : 1개 처리 당 1개의 쓰레드

# Node.js

[https://github.com/libuv/libuv](https://github.com/libuv/libuv)

libuv : 각각의 운영체제에 최적화된 I/O를 할 수 있는 C/C++로 되어 있음.

JavaScript는 껍데기.

**즉, C 저수준 까지 핸들링 해서 성능을 향상 한 후 wrapping 한 것이라 아키텍쳐를 무시할 수 없음.**

## 주의점

1. Block 되면 안된다.

    ```jsx
    for(i=0; i<5; i++){
    	sleep(1000);
    }
    // => Timer 사용해야함.
    ```

2. 소스 코드 순서대로 순차적 실행되지 않는다.
3. Event Loop 순서에 주의해야한다.

## process.**

```jsx
console.log('env = ', process.env);
console.log('version = ', process.version);
console.log('versions = ', process.versions);
console.log('platform = ', process.platform);
console.log('memory usage = ', process.memoryUsage());
console.log('up time = ', process.uptime());
```

## EventEmitter

### Node 의 최상위 객체

```jsx
var EventEmitter = require('events').EventEmitter;
var timer = new EventEmitter();

var count = 0;
var interval = 1000;

timer.on('timed', function(count, uptime) {
    console.log('Event!! count : %d, uptime : %d ms', count, uptime);
});

setInterval(function() {
    timer.emit('timed', ++count, count * interval);
}, interval);
```

윈도우, 리눅스 등이 반복되는 interval과 같은 것은 service로 등록 해야하는데,
node에서는 EventEmitter를 이용 할 수 있다.

즉, node를 이용하면 주기적인 것에 대해 장점을 볼 수 있다.

# Stream

거대한 데이터를 모두 메모리에 올릴 수 없으니,
앞에서 부터 읽어가면서 물 흐르듯이 보내는 것.

Readable/Writable 가능.

안좋은 예)

```jsx
fs.readFile('data.txt', () => {}) ... 
```

Stream 예)

```jsx
var stream = fs.createReadStream('data.txt', () => {}) ...
stream.pipe()
```

## Socket

socket.io

socket.io-client

# Rest API

## 구) 방식

```jsx
var http = require('http');
var url = require('url');

function onRequest(req, res){
    var pathname = url.parse(req.url).pathname;

    switch(pathname){
        case '/creatememo' :
            res.writeHead(200, {"Content-Type": "text/plain"});
            res.write('creatememo');
            res.end();
            break;
        case '/readmemo' :
            res.writeHead(200, {"Content-Type": "text/plain"});
            res.write('readmemo');
            res.end();
            break;
        case '/updatememo' :
            res.writeHead(200, {"Content-Type": "text/plain"});
            res.write('updatememo');
            res.end();
            break;
        case '/removememo' :
            res.writeHead(200, {"Content-Type": "text/plain"});
            res.write('removememo');
            res.end();
            break;
        default :
            res.writeHead(404, {"Content-Type": "text/paling"});
            res.write('pathname error');
            res.end();
            break;
    }
}
var server = http.createServer(onRequest);
server.listen(8080);
```

![Untitled 2](https://user-images.githubusercontent.com/42796949/115420106-d4ace880-a235-11eb-8f60-71405999562b.png)

**단점 :** 

**행위에 따라 각각의 url이 필요함.**

## 신) Restful API

하나의 같은 자원에서

- POST
- GET
- UPDATE
- DELETE

를 통해서 자원을 핸들링 한다.

즉, url은 [localhost:8080/memo](http://localhost:8080/memo) 라는 것으로 고정하고, HTTP method에 따라서 행위를 정하자.

⇒ 리소스 지향적인 아키텍쳐
