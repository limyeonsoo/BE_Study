const mongoose = require('mongoose');

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

const Users = mongoose.model('Users', userSchema);

module.exports = {Users}