const express = require('express');
const path = require('path');
const dotenv = require('dotenv');
const app = express()
const port = 4000

dotenv.config({
    path: path.resolve(
      process.cwd(),
      ".env"
    )
  });

const mongoose = require('mongoose');


mongoose.connect(`mongodb+srv://${process.env.DBID}:${process.env.DBPW}@madgo.nvzw2.mongodb.net/myFirstDatabase?retryWrites=true&w=majority`, {
    // connect option
    useNewUrlParser : true,
    useUnifiedTopology : true,
    useCreateIndex : true,
    useFindAndModify: false
}).then(() => console.log("MongoDB Connected!!!"))
.catch(err => console.log("MongoDB Error!!!"))


app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})