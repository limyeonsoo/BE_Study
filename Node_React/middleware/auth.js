// authentication.
// 1. Client로부터 Token을 가져온다.
// 2. Token을 복호화 후 user를 알아낸다.
// 3.1 user가 유효하면 인증 success.
// 3.2 user가 무효하면 인증 fail.
const {User} = require('../models/Users');

let auth = (req, res, next) => {
    
    let token = req.cookies.x_auth;

    User.findByToken(token, (err,user) => {
        if(err) throw err;
        if(!user) return res.json({
            isAuth:false,
            error: true
        });
        req.token = token;
        req.user = user;
        next(); //next가 없으면 middleware에서 끝.
    });
    
}

module.exports = {auth};