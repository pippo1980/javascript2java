package com.sirius.js2java.nashorn;

import jdk.nashorn.api.scripting.AbstractJSObject;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory extends AbstractJSObject {

    private static final String EMPTY = "";

    public ServiceFactory(OuterServiceInvoker invoker) {
        this.invoker = invoker;
    }

    private final OuterServiceInvoker invoker;

    @Override
    public boolean isInstance(Object instance) {
        return true;
    }

    @Override
    public Object getMember(String name) {
        InterfaceWrapper interface_wrapper = services.get(name);
        if (interface_wrapper == null) {
            interface_wrapper = getInterface(name);
        }

        return interface_wrapper;
    }

    private InterfaceWrapper getInterface(String name) {

        synchronized (services) {
            InterfaceWrapper interface_wrapper = services.get(name);
            if (interface_wrapper == null) {
                interface_wrapper = new InterfaceWrapper(new InnerMethodInvoker(name));
                services.put(name, interface_wrapper);
            }
            return interface_wrapper;
        }

    }

    private final Map<String, InterfaceWrapper> services = new HashMap<>();

    private class InnerMethodInvoker implements MethodInvoker {

        InnerMethodInvoker(String _interface) {
            this._interface = _interface;
        }

        String _interface;

        @Override
        public InvokeResult invoke(String method, Object[] objects) {
            InvokeResult result = new InvokeResult();

            try {
                result.payload = invoker.invoke(_interface, method, objects);
                result.success = true;
                result.error_code = -1;
                result.error_msg = EMPTY;
            } catch (Throwable throwable) {
                // TODO logger

                result.success = false;
                // TODO error_code
                result.error_msg = throwable.getMessage();
            }

            return result;
        }
    }
}
