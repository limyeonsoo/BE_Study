var EventEmitter = require('events').EventEmitter;
var timer = new EventEmitter();

var count = 0;
var interval = 1000;

timer.on('timed', function(count, uptime) {
    console.log('Event!! count : %d, uptime : %d ms', count, uptime);
});

setInterval(function() {
    timer.emit('timed', ++count, count * interval);
}, interval);