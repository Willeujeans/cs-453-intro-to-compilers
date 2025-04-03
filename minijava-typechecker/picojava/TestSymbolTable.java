package picojava;
import java.util.*;
import syntaxtree.*;
import visitor.*;

public class TestSymbolTable {
    public TestSymbolTable(){
        testInsert();
        testInsertInvalid();
        testInsertDuplicate();
        testExitScopeTooManyTimes();
        testPrettyPrinting();
    }

    private void testInsert(){
        StringBuilder output = new StringBuilder("[TestSymbolTable] TEST 'testInsert'");
        SymbolTable<Void, Integer> symbolTable = new SymbolTable<Void,Integer>();
        String identifier = "x";
        MyType type = new MyType("int");
        Symbol symbol = new Symbol(type, 0, 0, 0, 0, 0);
    
        try {
            symbolTable.insert(identifier, symbol);
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
        Symbol symbol = new Symbol(type, 0, 0, 0, 0, 0);
        
        if(symbolTable.insert(identifier, symbol)){
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
        Symbol symbol = new Symbol(type, 0, 0, 0, 0, 0);
        symbolTable.insert(identifier, symbol);

        if(symbolTable.insert(identifier, symbol))
            output.insert(0, " ❌");
        else
            output.insert(0, " ✅");
            
        
        System.out.println(output);
    }

    private void testExitScopeTooManyTimes(){
        StringBuilder output = new StringBuilder("[TestSymbolTable] TEST 'testExitScopeTooManyTimes'");
        SymbolTable<Void, Integer> symbolTable = new SymbolTable<Void,Integer>();
        MyType type = new MyType("int");
        Symbol symbol = new Symbol(type, 0, 0, 0, 0, 0);
        symbolTable.insert("a", symbol);
        symbolTable.enterMethodScope("myClass");
        symbolTable.exitScope();
        symbolTable.exitScope();
        symbolTable.exitScope();

        System.out.println(output);
    }


    private void testPrettyPrinting(){
        SymbolTable<Void, Integer> symbolTable = new SymbolTable<Void,Integer>();
        MyType type = new MyType("int");
        Symbol symbol = new Symbol(type, 0, 0, 0, 0, 0);
        symbolTable.insert("a", symbol);
        symbolTable.enterClassScope("myClass");
        symbolTable.insert("a", symbol);
        symbolTable.enterMethodScope("myMethod");
        symbolTable.insert("c", symbol);
        symbolTable.exitScope();
        symbolTable.insert("d", symbol);
        System.out.println(symbolTable.prettyPrint());
    }


}
