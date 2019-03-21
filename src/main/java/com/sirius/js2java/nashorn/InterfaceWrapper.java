package com.sirius.js2java.nashorn;

import jdk.nashorn.api.scripting.AbstractJSObject;

import java.util.HashMap;
import java.util.Map;

public class InterfaceWrapper extends AbstractJSObject {

    public InterfaceWrapper(MethodInvoker invoker) {
        this.invoker = invoker;
    }

    final MethodInvoker invoker;

    @Override
    public boolean isInstance(Object instance) {
        return true;
    }

    @Override
    public Object getMember(String name) {
        MethodWrapper method_wrapper = methods.get(name);
        if (method_wrapper == null) {
            method_wrapper = getMethod(name);
        }

        return method_wrapper;
    }

    private MethodWrapper getMethod(String name) {

        synchronized (methods) {
            MethodWrapper method_wrapper = methods.get(name);
            if (method_wrapper == null) {
                method_wrapper = new MethodWrapper(name, invoker);
                methods.put(name, method_wrapper);
            }

            return method_wrapper;
        }

    }

    private final Map<String, MethodWrapper> methods = new HashMap<>();

}
