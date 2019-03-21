package com.sirius.js2java.anltr;

import org.antlr.v4.Tool;

public class ToolCodeGenerator {

    public static void main(String[] args) {
//        Tool.main(new String[0]);
        String out = ToolCodeGenerator.class.getResource("/").getFile().replace("/target/classes/",
                "/src/main/java/com/sirius/js2java/");

        try {
            Tool.main(new String[]{"-o", out, "-package", "com.sirius.js2java", "-listener", "-visitor",
                    ToolCodeGenerator.class.getResource("/JavaScriptLexer.g4").getFile(),
                    ToolCodeGenerator.class.getResource("/JavaScriptParser.g4").getFile()});
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

}
