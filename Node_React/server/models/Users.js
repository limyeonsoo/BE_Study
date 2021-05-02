const mongoose = require('mongoose');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');

const saltRounds = 10;

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
    role:{
        type: Number,
        default: 0
    },
    image : String,
    token:{
        type: String
    },
    tokenExp:{
        type:Number
    }
})

// Store hash in your password DB.
userSchema.pre('save', function(next){
    var user = this;

    if(user.isModified('password')){ // 비밀번호에 대해 수정사항이 있을 때만.
        bcrypt.genSalt(saltRounds, function(err, salt) {
            console.log("isModified",err);
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

userSchema.methods.comparePassword = function(plainPassword, callback){
    // DB pw  <--->  input pw
    bcrypt.compare(plainPassword, this.password, function(err, isMatch){
        console.log("comparePassword",err);
        if(err) return callback(err);
        callback(null, isMatch);
    })
}
userSchema.methods.generateToken = function(cb){
    // JWT를 이용하여 Token 생성.
    var user = this;

    var token = jwt.sign(user._id.toHexString(), 'secretToken')

    user.token = token;
    user.save(function(err, user){
        console.log("generateToken",err);
        if(err) return cb(err);
        cb(null, user);
    })
}

userSchema.statics.findByToken = function(token, cb){
    var user = this;

    jwt.verify(token, 'secretToken', function(err, decoded){
        console.log("findByToken",err);
        user.findOne({"_id":decoded, "token":token}, function(err, user){
            console.log("findByToken err");
            if(err) return cb(err);
            cb(null, user);
        })
    });
}

const Users = mongoose.model('Users', userSchema);

module.exports = {Users}