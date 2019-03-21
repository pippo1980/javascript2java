package com.sirius.js2java.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JSContainer {

    public JSContainer(OuterServiceInvoker outerServiceInvoker) {
        this.outerServiceInvoker = outerServiceInvoker;
    }

    private OuterServiceInvoker outerServiceInvoker;
    private ScriptExecutor scriptExecutor;
    private ScriptObjectMirrorTransformer transformer = new ScriptObjectMirrorTransformer();

    public void init(String source) throws ScriptException {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        NashornScriptEngine engine = (NashornScriptEngine) engineManager.getEngineByName("nashorn");
        engine.put("Services", new ServiceFactory(outerServiceInvoker));

        engine.eval(source);
        scriptExecutor = engine.getInterface(ScriptExecutor.class);

        assert scriptExecutor != null;
    }

    public Object execute(Object... parameters) {
        try {
            Object result = scriptExecutor.execute(parameters);
            return result instanceof ScriptObjectMirror ? transformer.transform((ScriptObjectMirror) result) : result;
        } catch (Throwable e) {
            throw new JSContainerException(e);
        }
    }

    public interface ScriptExecutor {

        Object execute(Object... parameters);

    }
}
