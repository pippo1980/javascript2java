package com.sirius.js2java.nashorn;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.HashMap;
import java.util.Map;

public class ScriptObjectMirrorTransformer {

    public Object[] transform(Object[] args) {
        if (args == null || args.length == 0) {
            return args;
        }

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof ScriptObjectMirror) {
                args[i] = transform((ScriptObjectMirror) arg);
            }
        }

        return args;
    }

    public Map<String, Object> transform(ScriptObjectMirror mirror) {
        if (mirror == null) {
            return null;
        }

        Map<String, Object> rtvl = new HashMap<>();

        for (Map.Entry<String, Object> entry : mirror.entrySet()) {

            Object value = entry.getValue();
            rtvl.put(entry.getKey(),
                    value instanceof ScriptObjectMirror ? transform((ScriptObjectMirror) value) : value);

        }

        return rtvl;
    }

}
