var order_service = Services['com.pdd.services.order'];

function execute(parameters) {
    var orderId = parameters[0];

    var order = {id: orderId, info: {name: 'pippo', account: 22.01, fib: fib(100, 0, 1)}, desc: 'stw'};
    var result = order_service.create(order);

    return {success: result && result.success, order: order, id: orderId};
}

function fib(n) {
    return fib_(n, 0, 1);
}

function fib_(n, a, b) {
    return n == 0 ? a : fib_(n - 1, b, a + b);
}