// function makeException(){
//     throw new Error('custom Exception');
// }

// setTimeout(function(){
//     console.log('Hello ');
//     setTimeout(function(){
//         console.log(' World');
//     }, 1000)
// }, 1000)

// makeException();
// console.log('!!!!!');

function makeException(){
    throw new Error('custom Exception');
}

setTimeout(function(){
    console.log('Hello ');
    setTimeout(function(){
        console.log(' World');
    }, 1000)
}, 1000)

try{
    makeException();
}catch(err){
    console.log('try-catch ' + err.stack);
}