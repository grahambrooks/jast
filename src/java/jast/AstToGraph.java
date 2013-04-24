package jast;

import jast.Java.JavaParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AstToGraph {
    public Object toGraph(JavaParser.CompilationUnitContext compilationUnitContext) {
        Map<String, Object> compilationUnit = new HashMap<String, Object>();

        compilationUnit.put("package", terminalList(compilationUnitContext.packageDeclaration().qualifiedName().Identifier()));

        compilationUnit.put("imports", importList(compilationUnitContext.importDeclaration()));

        compilationUnit.put("types", typeList(compilationUnitContext.typeDeclaration()));

        return compilationUnit;
    }

    private Object typeList(List<JavaParser.TypeDeclarationContext> types) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (JavaParser.TypeDeclarationContext t : types) {
            Map<String, Object> typeMap = new HashMap<String, Object>();
            typeMap.put("class-declaration", mapNode(t.classDeclaration().Identifier()));
            result.add(typeMap);
        }
        return result;
    }

    private List<Object> importList(List<JavaParser.ImportDeclarationContext> importDeclarationContexts) {
        List<Object> result = new ArrayList<Object>();

        for (JavaParser.ImportDeclarationContext c : importDeclarationContexts) {
            Object o = terminalList(c.qualifiedName().Identifier());
            result.add(o);
        }
        return result;  //To change body of created methods use File | Settings | File Templates.
    }

    private List<Map<String, Object>> terminalList(List<TerminalNode> identifier) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        for (TerminalNode n : identifier) {
            Map<String, Object> token = mapNode(n);

            result.add(token);
        }
        return result;
    }

    private Map<String, Object> mapNode(TerminalNode n) {
        Map<String, Object> token = new HashMap<String, Object>();
        token.put("text", n.getSymbol().getText());
        token.put("line", n.getSymbol().getLine());
        token.put("chrPositionInLine", n.getSymbol().getCharPositionInLine());
        return token;
    }


}
