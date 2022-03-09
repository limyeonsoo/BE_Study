# curl, vi, jq로 API Test하기

2022.03.08의 작업

# curl

통신 프로토콜을 이용할 수 있는 라이브러리 및 Command Line 제공 Project

# vi

유닉스 환경에서 많이 쓰이는 문서 편집기 - 한 줄 단위가 아니라 한 화면을 편집하는 Visual Editor라는 뜻이 있음.

## 왜 이 2개를 이용했나

Pros.

- env를 통해 변수화가 가능하다.
- shell script를 통해 자동화가 가능하다.
- 명령어를 잘 다루면 편리할 수 있다.
- Tool, OS 등에 영향을 받지 않고 개발 할 수 있다.

Cons.

- 처음에 어렵다
    
    → 극복 가능.
    

### API Test를 위한 다른 도구

- JetBrains의 HTTP Client
    - Pros.
        - API Test Scenario를 파일로 가질 수 있다.
        - 팀원들 모두 사용할 줄 안다.
    - Cons.
        - 매 번 수동으로 실행을 해줘야 할 수도 있다.
        - 변경에 민감할 수도 있다.
        - 자동화가 어렵다.
- Postman
    - Pros.
        - API Test Scenario를 파일로 가질 수 있다.
        - curl, ... 다른 코드로도 변경 할 수 있다.
    - Cons.
        - 남의 거다.
- swagger
    - Pros.
        - Frame이 있어서 쉽다.
    - Cons.
        - 다음에 다시 사용할 수 없다. 매번 새로 만드는 귀찮음이 있을 수 있다.
        - 자동화가 어렵다.

# Step

1. 마일스톤에 해당하는 API 식별
2. 식별된 API 나열 및 의존 관계 확인
    
    → 의존 관계 때문에 Index를 메긴 후 테스트가 매끄럽게 동작할 수 있도록 정렬.
    
    → POST - GET/PUT/... - DELETE의 순서가 필요할 수 있음.
    
3. 공통 요구조건 식별
    
    → 공통 변수화 할 수 있으니 중요함.
    
4. 각 API 별 상세 테스트
    
    → curl로 공통화 하여 shell script로 자동화 할 수 있게 함.
    

# curl

```bash
curl
	-s: silent mode: curl이 찍는 *과 같은 log들을 안 볼 수 있음.
	-v: verbose: talkative하게 만든다 -> 더 상세한 내용을 볼 수 있음.
	-X: specify request command to use -> 없으면 GET. Method 지정할 수 있음.
	-H: header: Pass custom header to server -> Header 한 줄마다 한 개씩
	-d: data: HTTP POST data
```

-s, -v 는 그냥 준다.

-X에는 GET, POST, DELETE, PUT 등을 준다.

-H에는 X-AUTH-TOKEN, Content-Type, Accept 등을 준다.

### -H ‘Content-Type: application/json’

request에 body가 있을 때 JSON 형태로 주겠다.

### -H ‘Accept: application/json’

response를 JSON 형태로 받겠다.

### ⇒단, Spring Boot가 default로 처리할 수 있는 능력이 있다.

-d에는 body를 JSON 형태로 입력해준다.

### \ escape를 이용하여 예쁘게 줄 수 있다.

### @-를 이용하여 Standard Input을 body로 사용할 수 있다.

# jq

command-line JSON processor

[커맨드라인 JSON 프로세서 jq : 기초 문법과 작동원리](https://www.44bits.io/ko/post/cli_json_processor_jq_basic_syntax)

`$ jq .`

넘겨 받은 인자를 출력해라. → JSON 형태로 출력해줌.

`$ jq ‘.key’`

key에 해당하는 속성의 값만 출력해라.

`-c` (compact)

한 줄에 이어붙여서 출력해라.

`-r` (raw-output)

쌍따옴표 없이 문자열만 출력해라.

`[]` 

배열로 필터를 하겠다.

`[n]`

배열에서 n번째 값을 가져와라.

```bash
jq . -c
jq -r '.[] | .id'
	-> 배열로 돌면서 id에 해당하는 value만 가져올거고, raw로 출력해라.
jq -r '.[]
jq -r '.id'
```

# `grep` vs `fgrep`

## grep

패턴에 매칭 되는 것을 찾아라

## fgrep

문자열이 같은 것을 찾아라

# $?

가장 최근 명령어의 종료 상태를 나타낸다.

grep을 해서 실패한 경우 1

성공한 경우 0

# date +%s

현재시간을 epochmillis로 줌.