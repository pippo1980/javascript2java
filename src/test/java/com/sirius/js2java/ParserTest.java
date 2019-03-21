package com.sirius.js2java;

import com.sirius.js2java.anltr.JavaScriptLexer;
import com.sirius.js2java.anltr.JavaScriptParser;
import com.sirius.js2java.anltr.JavaScriptParserListener;
import net.sf.cglib.proxy.Proxy;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.junit.Test;

import java.io.IOException;

public class ParserTest {

    @Test
    public void test1() throws IOException {
        String file = ParserTest.class.getResource("/examples/Classes.js").getFile();

        JavaScriptLexer lexer = new JavaScriptLexer(CharStreams.fromFileName(file));

        CommonTokenStream tokens = new CommonTokenStream(lexer);
        JavaScriptParser parser = new JavaScriptParser(tokens);

//        JavaScriptParserVisitor visitor = (JavaScriptParserVisitor) Proxy.newProxyInstance(ParserTest.class.getClassLoader(),
//                new Class[]{JavaScriptParserVisitor.class},
//                (o, method, objects) -> {
//                    System.out.println(method.getName() + "####" + Arrays.toString(objects));
//                    return null;
//                });
//
//        parser.program().accept(visitor);

        JavaScriptParserListener listener = (JavaScriptParserListener) Proxy.newProxyInstance(ParserTest.class.getClassLoader(),
                new Class[]{JavaScriptParserListener.class},
                (o, method, objects) -> {
                    try {
                        System.out.println("#######" + method.getName());
                        if (objects[0] instanceof ParserRuleContext) {
                            ParserRuleContext context = (ParserRuleContext) objects[0];
                            System.out.println(context.getText());
                        }

                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    return null;
                });
        parser.addParseListener(listener);

        parser.program();
    }

}
