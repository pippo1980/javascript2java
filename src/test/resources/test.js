function fib(n) {
    function fib_(n, a, b) {
        if (n == 0) return a
        else return fib_(n - 1, b, a + b)
    }

    return fib_(n, 0, 1)
};

console.log(fib(100))
var count = 10000;

var start = Date.now();
for (var i=0;i<count;i++){
    fib(100);
}

var cost = (Date.now()-start)*1000000/count;
console.log(cost)