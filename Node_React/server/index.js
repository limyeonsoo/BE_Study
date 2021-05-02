const express = require('express');
const path = require('path');
const dotenv = require('dotenv');
const bodyParser = require('body-parser');
const cookieParser = require('cookie-parser');

const {auth} = require('./middleware/auth');

const app = express()
const port = 4000

dotenv.config({
    path: path.resolve(
        process.cwd(),
        ".env"
    )
});

const mongoose = require('mongoose');
const { Users } = require('./models/Users');
const { reset } = require('nodemon');

mongoose.connect(`mongodb+srv://${process.env.DBID}:${process.env.DBPW}@madgo.nvzw2.mongodb.net/myFirstDatabase?retryWrites=true&w=majority`, {
    // connect option  
    useNewUrlParser : true,
    useUnifiedTopology : true,
    useCreateIndex : true,
    useFindAndModify: false
}).then(() => console.log("MongoDB Connected!!!"))
.catch(err => {console.log("MongoDB Error!!!"); console.log(err)})

// For application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({extended: true}));
// For application/json
app.use(bodyParser.json());

app.use(cookieParser());
app.get('/', (req, res) => {
  res.send('Hello World!')
})
app.get('/api/hello', (req, res) => {
    res.send('hello word');
})
app.post('/api/users/register', (req, res) => {
    // client request with information for Sign Up.

    const user = new Users(req.body);

    user.save((err, userInfo) => { // mongoDB method.
        if(err) return res.json({ success : false, err });
        return res.status(200).json({ success : true });
    });
})

app.post('/api/users/login', (req, res) => {
    Users.findOne({email: req.body.email}, (err, user) => {
        console.log('user : ', user);
        if(!user) return res.json({
            loginSuccess: false,
            message: "Invalid Email"
        })
        // save 전에 암호화 필요.
        user.comparePassword(req.body.password, (err, isMatch) => {
            if(!isMatch)
                return res.json({
                    loginSuccess:false,
                    messgage:"Incorrect Password"
                })
            
            // password가 일치 했으니, Token 생성하기.
            user.generateToken((err, user) => {
                if(err) return res.status(400).send(err);

                // Store Token   1. cookie  2. localStorage
                res.cookie("x_auth", user.token)
                .status(200)
                .json({loginSuccess : true, userId : user._Id});
            })
        })
    })
})

app.get('/api/users/auth', auth , (req, res) => {
    // auth : middleware 를 거친 후.
    console.log('/api/users/auth');
    console.log(req.body);
    res.status(200).json({
        _id: req.user._id,
        isAdmin: req.user.role === 0?false : true,
        isAuth:true,
        email:req.user.email,
        name:req.user.name,
        lastname:req.user.lastname,
        role:req.user.role,
        image:req.user.image
    })
})

app.get('/api/users/logout', auth, (req, res) => {

    Users.findOneAndUpdate({
        _id:req.user._id}, 
        {token:""}, 
        (err, user) => {
            if(err) return res.json({success:false, err});
            return res.status(200).send({
                success:true
            })
        }
    )
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})