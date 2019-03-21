package com.sirius.js2java.nashorn;

import jdk.nashorn.api.scripting.AbstractJSObject;

public class MethodWrapper extends AbstractJSObject {

    public MethodWrapper(String method, MethodInvoker invoker) {
        this.method = method;
        this.invoker = invoker;
    }

    private final String method;
    private final MethodInvoker invoker;
    private final ScriptObjectMirrorTransformer transformer = new ScriptObjectMirrorTransformer();

    @Override
    public boolean isFunction() {
        return true;
    }

    @Override
    public boolean isStrictFunction() {
        return true;
    }

    @Override
    public Object call(Object _this, Object... args) {
        // TODO 性能优化, 可以根据pb或者具体类型真正生成对一个的class, 提升性能
        return invoker.invoke(method, transformer.transform(args));
    }

}
