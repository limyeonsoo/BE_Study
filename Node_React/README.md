# 간단한 로그인 만들기

# express setting

express란?

Node.js를 위한 웹 프레임워크

웹 애플리케이션을 위해 간결하고 유연한 기능 제공.

## 라우팅

URI 및 특정한 HTTP 메소드인 특정 엔드포인트에 대한 Client 요청 & App의 응답을 결정.

[https://expressjs.com/ko/guide/routing.html](https://expressjs.com/ko/guide/routing.html)

`app.METHOD(PATH, HANDLER)`

여기서,

- `app`은 `express`의 인스턴스입니다.
- `METHOD`는 [HTTP 요청 메소드](http://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol)입니다.
- `PATH`는 서버에서의 경로입니다.
- `HANDLER`는 라우트가 일치할 때 실행되는 함수입니다.
    - `res.send` : App의 응답.

```jsx
var express = require('express');
var app = express();
var port = 4000;

// respond with "hello world" when a GET request is made to the homepage
app.get('/', function(req, res) {
  res.send('hello world'); 
});

// listen port
app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})
```

# MongoDB

## Connection

- mongoose  :  mongoDB를 위한 비동기 환경. Promise 와 Callback 반환.
- dotenv  :  .env 파일에서 환경변수를 사용하기 위한 zero-dependency 모듈. [https://www.npmjs.com/package/dotenv](https://www.npmjs.com/package/dotenv)

    zero-dependency : 모듈, 패키지 어떤 것에도 의존하지 않음. ⇒ .env 만 있으면 된다.

```jsx
mongoose.connect(`mongodb+srv://${process.env.DBID}:${process.env.DBPW}@[ 생략 ]`, {
    // connect option  https://mongoosejs.com/docs/connections.html
    useNewUrlParser : true,
    useUnifiedTopology : true,
    useCreateIndex : true,
    useFindAndModify: false
}).then(() => console.log("MongoDB Connected!!!"))
.catch(err => console.log("MongoDB Error!!!"))
```

mongoose의 connect 메소드를 실행한 후:

Promise에 대해 성공이면 then 실패면 catch 실행.

## Schema

### mongoose의 Schema 메소드를 이용하여 설계.

[https://mongoosejs.com/docs/api/schema.html#schema_Schema](https://mongoosejs.com/docs/api/schema.html#schema_Schema)

```jsx
const userSchema = mongoose.Schema({
    name:{
        type:String,
        maxlength : 50,
    },
    email:{
        type : String,
        trim : true, // space 제거
        unique: 1,
    },
    password:{
        type:String,
        minlength:8,
    },
    // ... ... [생략]
})
```

### mongoose의 model 메소드를 이용하여 정의된 스키마를 컴파일하여 생성자로 사용.

[https://mongoosejs.com/docs/models.html](https://mongoosejs.com/docs/models.html)

```jsx
const Users = mongoose.model('Users', userSchema);
```

# post 만들기

REST에서 post는 등록 할 때 사용. :  Schema를 등록하는데 사용해보자.

1. Client에서 정보를 보낸다.
2. 서버에서 정보를 읽어들인다.
3. Schema에 맞게 저장한다.

### bodyParser

[http://expressjs.com/en/resources/middleware/body-parser.html](http://expressjs.com/en/resources/middleware/body-parser.html)

express로 들어오는 요청(req)의 body를 parsing해주는 middleware

- urlencoded : 본문만 구문 분석 & 키-값 쌍 중 값에 모든 유형.
- json : json 형태로 parsing

/register 라는 엔드포인트에 post 요청이 오게 되면, req.body를 파싱해서 Users라는 스키마에 적용하고, 적용된 모델을 save()한다. 

성공하면 200 status와 success:true를 반환,
실패하면 success:false와 err를 반환.

```jsx
const bodyParser = require('body-parser');

// For application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({extended: true}));

// For application/json
app.use(bodyParser.json());

const { Users } = require('./models/Users');

app.post('/register', (req, res) => {
    // client request with information for Sign Up.

    const user = new Users(req.body);
    user.save((err, userInfo) => { // mongoDB method.
        if(err) return res.json({ success : false, err });
        return res.status(200).json({ success : true });
    });
})
```

# nodemon

node application의 파일 변화가 감지되면 자동으로 restart해주는 Tool

[https://www.npmjs.com/package/nodemon](https://www.npmjs.com/package/nodemon)

```jsx
"nodemon index.js"
```

app이 수정되면 index.js 를 자동으로 재실행.

# bcrypt 암호화

DB에 저장될 때 password에 암호화 해서 저장한다.

bcrypt : 

![%E1%84%80%E1%85%A1%E1%86%AB%E1%84%83%E1%85%A1%E1%86%AB%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%86%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%80%E1%85%B5%207740ebf150f6442a947d36050a17005c/Untitled.png](%E1%84%80%E1%85%A1%E1%86%AB%E1%84%83%E1%85%A1%E1%86%AB%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%86%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%80%E1%85%B5%207740ebf150f6442a947d36050a17005c/Untitled.png)

![%E1%84%80%E1%85%A1%E1%86%AB%E1%84%83%E1%85%A1%E1%86%AB%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%86%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%80%E1%85%B5%207740ebf150f6442a947d36050a17005c/Untitled%201.png](%E1%84%80%E1%85%A1%E1%86%AB%E1%84%83%E1%85%A1%E1%86%AB%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%86%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%80%E1%85%B5%207740ebf150f6442a947d36050a17005c/Untitled%201.png)

Blowfish 암호를 기반으로 설계된 암호화 함수.

현재 강력한 해시 메커니즘 암호 중 하나.

## SHA의 단점.

### SHA란

**SHA**(Secure Hash Algorithm, 안전한 해시 알고리즘) 함수들은 서로 관련된 암호학적 해시 함수들의 모음이다.
SHA 함수군에 속하는 최초의 함수는 공식적으로 **SHA**라고 불리지만, 나중에 설계된 함수들과 구별하기 위하여 **SHA-0**이라고도 불린다. 
2년 후 SHA-0의 변형인 **SHA-1**이 발표되었으며, 
그 후에 4종류의 변형, 즉 **SHA-224**, **SHA-256**, **SHA-384**, **SHA-512**가 더 발표되었다. 이들을 통칭해서 **SHA-2**라고 하기도 한다.

- SHA-1은 SHA 함수들 중 가장 많이 쓰이며, TLS, SSL, PGP, SSH, IPSec 등 많은 보안 프로토콜과 프로그램에서 사용되고 있다.

[Nalee와 함께 떠나는 IT이야기]

빠른 해싱 속도가 약점이 됐다.

GPU연산에 유리한 32비트 논리/산술 연산을 사용하는데, 이는 빠른연산으로 공격자가 공격했을 때 취약하다는 단점이 있다.

**왜지...? :  생성 자체가 빠르니까 공격자가 많이 생성해서 빨리 때려맞출 수 있다.**

- ISO-27001 보안 규정 준수 해야할 때는 PBKDF2 사용.

## Bcrypt의 개선.

좀 더 느리게 된 설계 방식으로 암호화를 진행.

## 암호화를 하는 시점.

Client에게 받은 패스워드는 Plain Password.

이 Plain Password가 DB에 들어가기 전 + Schema에 적용되기 전에 암호화를 해주자.

⇒ Schema.pre()  를 이용해서 암호화를 해준다. 

조건 password가 수정사항이 있다면 ⇒ 암호화
아니라면 ⇒ pre() 다음으로 진행.

```jsx
// Store hash in your password DB.
userSchema.pre('save', function(next){
    var user = this;

    if(user.isModified('password')){ // 비밀번호에 대해 수정사항이 있을 때만.
        bcrypt.genSalt(saltRounds, function(err, salt) {
            if(err) return next(err);

            bcrypt.hash(user.password, salt, function(err, hash) {
                if(err) return next(err);
                user.password = hash;
                next();
            });
        });
    }else{
        next();
    }
})
```

# Client

`npx create-react-app`

## react-router-dom

[React Router: Declarative Routing for React](https://reactrouter.com/web/api/Router)

- Router

    low-level Router Interface.

    - BrowserRouter  :  history API를 사용할 수 있는 라우터. ex)뒤로가기, 앞으로가기, GO등.
    [https://developer.mozilla.org/en-US/docs/Web/API/History_API](https://developer.mozilla.org/en-US/docs/Web/API/History_API)
    - Server의 router와 별개로 url Routing을 해줌.
    새로고침 시에도 #으로 이루어진 url을 그대로 유지시킬 수 있음.
- Route

    url과 매핑시켜 component 호출.

- Link

    Link를 통해 url이동.

## CORS

cross-origin-resource-sharing

[https://github.com/limyeonsoo/ComputerScience/tree/master/CORS](https://github.com/limyeonsoo/ComputerScience/tree/master/CORS)

1. CHROME-CORS-EXTENSION  개발자 도구 이용.
2. 헤더를 통해서 특정 한 것들을 다 받을 수 있게 하는 방법.
3. Proxy를 이용하는 방법.

    [https://create-react-app.dev/docs/proxying-api-requests-in-development/](https://create-react-app.dev/docs/proxying-api-requests-in-development/)

    `npm install http-proxy-middleware`

    < src/setupProxy.js >

    허용할 백엔드의 url을 적어주면 된다.

    ```jsx
    const proxy = require('http-proxy-middleware');
    module.exports = function(app){
        app.use(
            '/api',
            proxy({
                target: 'http://localshost:4000',
                changeOrigin: true,
            })
        )
    }
    ```

    ![%E1%84%80%E1%85%A1%E1%86%AB%E1%84%83%E1%85%A1%E1%86%AB%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%86%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%80%E1%85%B5%207740ebf150f6442a947d36050a17005c/Untitled%202.png](%E1%84%80%E1%85%A1%E1%86%AB%E1%84%83%E1%85%A1%E1%86%AB%E1%84%92%E1%85%A1%E1%86%AB%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%20%E1%84%86%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%86%AF%E1%84%80%E1%85%B5%207740ebf150f6442a947d36050a17005c/Untitled%202.png)

    ## Proxy

    Server와 Client 사이의 미들웨어

    1. server or client의 IP를 익명으로 할 수 있다.
    2. 보내는 데이터도 임의로 바꿀 수 있다.
    3. 방화벽 기능을 할 수 있다.
    4. 캐쉬, 공유 데이터를 저장할 수 있다.
    5. 특정 url을 제한 할 수 있다.

# concurrently

## front server & back server 를 동시에 실행하기.

`"dev": "concurrently \"npm run backend\" \"npm run start --prefix client\""`

npm run dev ⇒ 

concurrently를 이용해서 실행 될 것.

1. npm run backend
2. npm run start   +   위치가  client/ 인 곳의 "start" 스크립트가 실행.

# CSS Framework

1. Material UI
2. React Bootstrap
3. Semantic UI
4. Ant Design
5. Materialize
6. etc...

# Redux

1. redux
2. react-redux
3. redux-promise

    ⇒ store 에 promise 타입의 액션을 전달해주기 위한 미들웨어.

4. redux-chunk

    ⇒ store 에 function 타입의 액션을 전달해주기 위한 미들웨어.

```jsx
import {applyMiddleware, createStore} from 'redux';
import promiseMiddleware from 'redux-promise';
import ReduxThunk from 'redux-thunk';

const createStoreWithMiddleware = applyMiddleware(promiseMiddleware, ReduxThunk)(createStore);
```

# props.history.push

/login,  /, /register 등  url을 변경할 수 있다.

react-router-dom에 의해 사용할 수 있는 객체.

[https://reactrouter.com/web/api/history](https://reactrouter.com/web/api/history)

### Error

이렇게 하면 props.history.push 가 undefined라고 한다.

```jsx
<Route exact path='/'>
       <LandingPage/>
</Route>
```

### Solve

```jsx
<Route exact path='/' component={Auth(LandingPage, null)} />
```

## withRouter 도 필요.

`import {withRouter} from 'react-router-dom';`

## react-router-dom history vs window.location (궁금증)

Browser reload의 차이.

history 는 reload 하지 않고,
location은 reload 된다.

# Auth

HOC 기법을 이용한다.

Auth를 확인하는 컴포넌트는 인증 된 사용자만 사용할 수 있는 컴포넌트를 가지고 있다.

1. HOC 컴포넌트를 만들어준다.

    Landing, Login, Register 각 페이지를 Auth로 감쌌다.

    - option

        null ⇒ 모든 유저 접근.

        true ⇒ 인증된 유저 접근.

        false ⇒ 인증안된 유저 접근.

    ```jsx
    export default function (SpecificComponent, option, adminRoute = null){
        
    		function AuthenticationCheck(props){
            const dispatch = useDispatch();
            useEffect(() => {
                dispatch(auth())
                    .then(res => {
                        console.log(res);
                        // // Not Loggin
                        if(!res.payload.isAuth){
                            if(option){ // option : true  ->  X
                                props.history.push('/login');
                            }
                        }else{ // loggined
                            if(adminRoute && !res.payload.isAdmin){
                                props.history.push('/');
                            }else{
                                // logged in  -> login & register X
                                if(option === false)
                                    props.history.push('/');
                            }
                        }
                    })
            },[]);
            return(
                <SpecificComponent />
            )
        }
        return AuthenticationCheck;
    }
    ```

    ```jsx
    <Router>
        <Switch>
          <Route exact path='/' component={Auth(LandingPage, null)} />
          <Route path='/register' component={Auth(RegisterPage, false)} />
          <Route path='/login' component={Auth(LoginPage, false)} />
        </Switch>
    </Router>
    ```

2. 인증 상태를 가져온다.

    현재는 cookie의 token을 이용해서 확인 할 수 있다.

    auth API는 JWT를 이용한 token이 있는지 확인하는 로직.

    ```jsx
    //dispatch(auth())

    export function auth(){
        const request = axios.get('/api/users/auth')
            .then(res => res.data);
        
        return{
            type:AUTH_USER,
            payload: request
        }
    }
    ```