package com.sirius.js2java.nashorn;

public interface OuterServiceInvoker {

    Object invoke(String _interface, String method, Object... parameters) throws Throwable;

}
