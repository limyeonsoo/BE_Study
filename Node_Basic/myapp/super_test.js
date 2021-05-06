var request = require('supertest');
var express = require('express');
var app = express();

app.get('/users', function(req, res){
	res.send(200, { name : 'tobi'});
});
// get 함수를 통해 users에 request한 값이 잘 들어오는지 확인합니다. 
// expect는 들어온 값들이 예상한(expect)한 값과 일치하는지 확인합니다. 
request(app)
  .get('/users')  
  .expect('Content-Type', /json/)  // Content-Type이 json인지 확인
  .expect('Content-Length', '15')  // Content-Length가 15인지 확인 
  .expect(200) // response code가 200인지 확인합니다. 
  .end(function(err, res){
    if(err) throw err;    
});