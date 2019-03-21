package com.sirius.js2java;

import com.sirius.js2java.nashorn.JSContainer;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class JSContainerTest {

    private static String source;

    @BeforeClass
    public static void init() throws IOException {
        source = IOUtils.toString(JSContainerTest.class.getResourceAsStream("/service_call.js"), "utf-8");
    }

    @Test
    public void test() throws ScriptException {
        JSContainer container = new JSContainer((_interface, method, parameters) -> Collections.emptyMap());
        container.init(source);

        Map<String, Object> result = (Map<String, Object>) container.execute(System.currentTimeMillis());
        assert result != null;
        assert result.get("id") instanceof Long;
    }

    @Test
    public void performance() throws ScriptException {
        JSContainer container = new JSContainer((_interface, method, parameters) -> Collections.emptyMap());
        container.init(source);
        Object result = container.execute(System.currentTimeMillis());
        assert result != null;

        int times = 100000;
        long start = System.nanoTime();
        for (int i = 0; i < times; i++) {
            container.execute(System.currentTimeMillis());
        }
        System.out.println((double) (System.nanoTime() - start) / times / 1000000);
    }

    @Test
    public void currency() throws ScriptException, InterruptedException {
        JSContainer container = new JSContainer((_interface, method, parameters) -> Collections.emptyMap());
        container.init(source);

        Set<Callable<Void>> tasks = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            tasks.add(() -> {
                try {
                    Long id = System.currentTimeMillis();
                    Map<String, Object> result = (Map<String, Object>) container.execute(id);
                    assert result != null;
                    assert id.equals(result.get("id"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            });
        }

        Executors.newFixedThreadPool(8).invokeAll(tasks).forEach(future -> {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
