var request = require('supertest');
var express = require('express');
var app = express();

app.get('/users', function(req, res){
	res.send(200, { name : 'tobi'});
});

describe('GET /users', function(){
  it('respond with json', function(done){
    request(app)
    .get('/users')
    .expect('Content-Type', /json/)
    .expect('Content-Length', '15')
    .expect(200)
    .end(function(err, res){
      if(err) throw err;
      done(); // mocha를 쓸 때는 반드시 done이 있어야합니다.
    }); 
  })
})