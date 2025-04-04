package picojava;
import java.util.*;
import syntaxtree.*;
import visitor.*;

public class TestSymbolTable {
    public TestSymbolTable(){
        testInsert();
        // testInsertInvalid();
        // testInsertDuplicate();
        // testExitScopeTooManyTimes();
        // testEnterScopeIncorrectly();
        // testPrettyPrinting();
    }

    private void testInsert(){
        StringBuilder output = new StringBuilder("[TestSymbolTable] TEST 'testInsert'");
        SymbolTable<Void, Integer> symbolTable = new SymbolTable<Void,Integer>();
        String identifier = "x";
        MyType type = new MyType("int");
        Symbol symbol = new Symbol(type, 0);
    
        try {
            symbolTable.enterVariableScope(identifier);
            symbolTable.insert(symbol);
            output.insert(0, " ✅");
        } catch (Exception e) {
            output.insert(0, " ❌");
        }

        System.out.println(output);
    }

    private void testInsertInvalid(){
        StringBuilder output = new StringBuilder("[TestSymbolTable] TEST 'testInsertInvalid'");
        SymbolTable<Void, Integer> symbolTable = new SymbolTable<Void,Integer>();
        String identifier = "";
        MyType type = new MyType("int");
        Symbol symbol = new Symbol(type, 0);
        symbolTable.enterVariableScope(identifier);
        if(symbolTable.insert(symbol)){
            output.insert(0, " ❌");
        }else{
            output.insert(0, " ✅");
        }
        
        System.out.println(output);
    }

    private void testInsertDuplicate(){
        StringBuilder output = new StringBuilder("[TestSymbolTable] TEST 'testInsertDuplicate'");
        SymbolTable<Void, Integer> symbolTable = new SymbolTable<Void,Integer>();
        String identifier = "x";
        MyType type = new MyType("int");
        Symbol symbol = new Symbol(type, 0);
        symbolTable.enterVariableScope(identifier);
        symbolTable.insert(symbol);

        if(symbolTable.insert(symbol))
            output.insert(0, " ❌");
        else
            output.insert(0, " ✅");
            
        
        System.out.println(output);
    }

    private void testExitScopeTooManyTimes(){
        StringBuilder output = new StringBuilder("[TestSymbolTable] TEST 'testExitScopeTooManyTimes'");
        SymbolTable<Void, Integer> symbolTable = new SymbolTable<Void,Integer>();
        MyType type = new MyType("int");
        Symbol symbol = new Symbol(type, 0);
        symbolTable.enterVariableScope("x");
        symbolTable.insert(symbol);
        symbolTable.exitScope();
        symbolTable.enterClassScope("myClass");
        symbolTable.exitScope();
        symbolTable.exitScope();
        symbolTable.exitScope();
        if(symbolTable.getScope().contains("global")){
            output.insert(0, " ✅");
        }else{
            output.insert(0, " ❌");
        }
        System.out.println(output);
    }

    private void testEnterScopeIncorrectly(){
        StringBuilder output = new StringBuilder("[TestSymbolTable] TEST 'testEnterScopeIncorrectly'");
        SymbolTable<Void, Integer> symbolTable = new SymbolTable<Void,Integer>();
        MyType type = new MyType("int");
        Symbol symbol = new Symbol(type, 0);
        symbolTable.enterVariableScope("a");
        symbolTable.insert(symbol);
        try {
            symbolTable.enterMethodScope("myMethod");
            output.insert(0, " ❌");
        } catch (Exception e) {
            output.insert(0, " ✅");
        }
        System.out.println(output);
    }

    private void testPrettyPrinting(){
        SymbolTable<Void, Integer> symbolTable = new SymbolTable<Void,Integer>();
        MyType type = new MyType("int");
        Symbol symbol = new Symbol(type, 0);
        symbolTable.insert(symbol);
        symbolTable.enterClassScope("myClass");
        symbolTable.insert(symbol);
        symbolTable.enterMethodScope("myMethod");
        symbolTable.insert(symbol);
        symbolTable.exitScope();
        symbolTable.insert(symbol);
        symbolTable.prettyPrint();
    }
}
