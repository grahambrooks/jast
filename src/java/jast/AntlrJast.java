package jast;

import jast.Java.JavaLexer;
import jast.Java.JavaParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Utils;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Tree;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AntlrJast {
    public static void run(String[] args) {
        try {
            JavaLexer lexer = new JavaLexer(new ANTLRFileStream(args[0]));
            JavaParser parser = new JavaParser(new CommonTokenStream(lexer));

            JavaParser.CompilationUnitContext compilationUnitContext = parser.compilationUnit();

            System.out.print(toStringTree(compilationUnitContext, parser));
        } catch (IOException e) {
            System.err.println("Error processing file args[0]");
            e.printStackTrace();
        }
    }

    public static String toStringTree(Tree t, Parser parser) {
        String[] ruleNames = parser != null ? parser.getRuleNames() : null;
        List<String> ruleNamesList = ruleNames != null ? Arrays.asList(ruleNames) : null;
        return toStringTree(t, ruleNamesList);
    }

    public static String toStringTree(Tree t, List<String> ruleNames) {
        String s = Utils.escapeWhitespace(getNodeText(t, ruleNames), false);
        if (t.getChildCount() == 0) return s;
        StringBuilder buf = new StringBuilder();
        buf.append("(");
        s = Utils.escapeWhitespace(getNodeText(t, ruleNames), false);
        buf.append(s);
        buf.append(' ');
        for (int i = 0; i < t.getChildCount(); i++) {
            if (i > 0) buf.append(' ');
            buf.append(toStringTree(t.getChild(i), ruleNames));
        }
        buf.append(")");
        return buf.toString();
    }

    public static String getNodeText(Tree t, List<String> ruleNames) {
        if (ruleNames != null) {
            if (t instanceof RuleNode) {
                int ruleIndex = ((RuleNode) t).getRuleContext().getRuleIndex();
                return ruleNames.get(ruleIndex);
            } else if (t instanceof ErrorNode) {
                return t.toString();
            } else if (t instanceof TerminalNode) {
                Token symbol = ((TerminalNode) t).getSymbol();
                if (symbol != null) {
                    return String.format("[%s %d %d]", symbol.getText(), symbol.getLine(), symbol.getCharPositionInLine());
                }
            }
        }
        // no recog for rule names
        Object payload = t.getPayload();
        if (payload instanceof Token) {
            return ((Token) payload).getText();
        }
        return t.getPayload().toString();
    }


}
