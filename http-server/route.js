var indexHandler = require('./indexHandler');

exports.route = (function(){
    console.log("In index");
    var handlers = {};

    handlers['/'] = {
        POST: indexHandler.create,
        GET: indexHandler.read,
        PUT: indexHandler.update,
        DELETE: indexHandler.remove
    };

    function route(req, res, body){
        var pathname = new URL('/', 'http://localhost:8080').pathname;
        var method = req.method.toUpperCase();

        if(typeof handlers[pathname][method] === 'function'){
            handlers[pathname][method](req,res,body);
        }else{
            res.writeHead(404, {"Content-Type": "text/plain"});
            res.write('pathname error');
            res.end()
        }
    }
    return route;
})();