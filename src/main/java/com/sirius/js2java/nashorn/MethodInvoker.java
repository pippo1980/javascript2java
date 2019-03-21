package com.sirius.js2java.nashorn;

public interface MethodInvoker {

    InvokeResult invoke(String method, Object[] objects);

}
