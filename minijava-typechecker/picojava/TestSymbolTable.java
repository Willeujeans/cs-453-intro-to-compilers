package picojava;

import picojava.MyType;
import picojava.SymbolTable;

public class TestSymbolTable {
    public TestSymbolTable(){
        testInsertInvalidIdentifier();
        testInsert();
        testPrint();
    }

    // Testing insert methods
    public static void testInsertInvalidIdentifier() {
        String output = "[TestSymbolTable] TEST: Insert Invalid Identifier";
        SymbolTable testingSymbolTable = new SymbolTable();
        Symbol symbol = new Symbol(new MyType("String"), 0, 0, 0, 0, 0);
        try {
            testingSymbolTable.insert(null, symbol);
        }catch(IllegalArgumentException exception){
            output += " ✅";
            System.out.println(output);
        }
    }

    public static void testInsert() {
        System.out.println("[TestSymbolTable] running: testInsert");

        SymbolTable testingSymbolTable = new SymbolTable();
        Symbol symbol = new Symbol(new MyType("String"), 0, 0, 0, 0, 0);
        testingSymbolTable.insert("a", symbol);
    }

    public static void testPrint(){
        System.out.println("[TestSymbolTable] running: testPrint");
        SymbolTable testingSymbolTable = new SymbolTable();
        Symbol symbol = new Symbol(new MyType("String"), 0, 0, 0, 0, 0);
        testingSymbolTable.insert("a", symbol);

        System.out.println(testingSymbolTable.prettyPrint());
    }
}