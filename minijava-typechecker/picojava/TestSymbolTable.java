package picojava;

import picojava.MyType;
import picojava.SymbolTable;

public class TestSymbolTable {
    public TestSymbolTable(){
        testInsertInvalidIdentifier();
        testInsert();
    }

    // Testing insert methods
    public static void testInsertInvalidIdentifier() {
        String output = "[TestSymbolTable] TEST: Insert Invalid Identifier";
        SymbolTable testingSymbolTable = new SymbolTable();
        SymbolData symbolData = new SymbolData(new MyType("String"), 0, 0, 0, 0, 0);
        try {
            testingSymbolTable.insert(null, symbolData);
        }catch(IllegalArgumentException exception){
            output += " ✅";
            System.out.println(output);
        }
    }

    public static void testInsert() {
        System.out.println("[TestSymbolTable] running: testInsert");

        SymbolTable testingSymbolTable = new SymbolTable();
        SymbolData symbolData = new SymbolData(new MyType("String"), 0, 0, 0, 0, 0);
        testingSymbolTable.insert("a", symbolData);
    }
}