const express = require('express');
const path = require('path');
const dotenv = require('dotenv');
const bodyParser = require('body-parser');
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

mongoose.connect(`mongodb+srv://${process.env.DBID}:${process.env.DBPW}@madgo.nvzw2.mongodb.net/myFirstDatabase?retryWrites=true&w=majority`, {
    // connect option  
    useNewUrlParser : true,
    useUnifiedTopology : true,
    useCreateIndex : true,
    useFindAndModify: false
}).then(() => console.log("MongoDB Connected!!!"))
.catch(err => console.log("MongoDB Error!!!"))

// For application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({extended: true}));
// For application/json
app.use(bodyParser.json());

app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.post('/register', (req, res) => {
    // client request with information for Sign Up.

    const user = new Users(req.body);
    user.save((err, userInfo) => { // mongoDB method.
        if(err) return res.json({ success : false, err });
        return res.status(200).json({ success : true });
    });
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})