package com.sirius.js2java;

import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.junit.Test;

import javax.script.CompiledScript;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EngineTest {

    static String function = "function fib(n){\n" +
                             "   function fib_(n,a,b){\n" +
                             "       if(n==0)  return a\n" +
                             "       else return fib_(n-1,b,a+b)\n" +
                             "   }\n" +
                             "   return fib_(n,0,1)\n" +
                             "};" +
                             "fib(100);";

    @Test
    public void test_1() throws ScriptException {
        ScriptEngineManager engineManager =
                new ScriptEngineManager();
        NashornScriptEngine engine = (NashornScriptEngine) engineManager.getEngineByName("nashorn");

        CompiledScript script = engine.compile(function);
        // warm up
        script.eval();

        int times = 1000000;

        long start = System.nanoTime();
        for (int i = 0; i < times; i++) {
            script.eval();
        }
        System.out.println((double) (System.nanoTime() - start) / times);

        start = System.nanoTime();
        for (int i = 0; i < times; i++) {
            fib(100);
        }
        System.out.println((double) (System.nanoTime() - start) / times);
    }

    private long fib(long n) {
        return fib(n, 0, 1);
    }

    private long fib(long n, long a, long b) {
        if (n == 0) {
            return a;
        } else {
            return fib(n - 1, b, a + b);
        }
    }

    @Test
    public void test_2() throws ScriptException, NoSuchMethodException {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        NashornScriptEngine engine = (NashornScriptEngine) engineManager.getEngineByName("nashorn");
        engine.eval("function invoke(service){ service.call() }");

        Invoker invoker = engine.getInterface(Invoker.class);
        invoker.invoke(service);

        int times = 1000000;

        long start = System.nanoTime();
        for (int i = 0; i < times; i++) {
            invoker.invoke(service);
        }
        System.out.println((double) (System.nanoTime() - start) / times);

        invoker = Service::call;
        start = System.nanoTime();
        for (int i = 0; i < times; i++) {
            invoker.invoke(service);
        }
        System.out.println((double) (System.nanoTime() - start) / times);
    }

    private Service service = new Service();

    public interface Invoker {

        void invoke(Service service);

    }

    @Test
    public void test_3() throws ScriptException {
        Function<Object[], Object> function = objects -> {
            for (Object object : objects) {
                Map<String, Object> so = (ScriptObjectMirror) object;
                System.out.println(so.keySet());
            }
            return new HashMap<String, Object>();
        };

        JSObject caller = new AbstractJSObject() {

            @Override
            public Object call(Object thiz, Object... args) {
                return function.apply(args);
            }

            @Override
            public boolean isFunction() {
                return true;
            }

            @Override
            public boolean isStrictFunction() {
                return true;
            }

        };

        ScriptEngineManager engineManager = new ScriptEngineManager();
        NashornScriptEngine engine = (NashornScriptEngine) engineManager.getEngineByName("nashorn");
        engine.put("caller", caller);
        CompiledScript script = engine.compile("function run(){ var result = caller({a:1}); print(result) }; run();");

        script.eval();
    }
}
