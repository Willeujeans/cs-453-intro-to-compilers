package picojava;

import picojava.SymbolData;
import picojava.SymbolTable;

public class TestSymbolTable {
    public TestSymbolTable(){
        testInsert();
    }

    public static void testInsert() {
        SymbolTable testingSymbolTable = new SymbolTable();
        SymbolData symbolData = new SymbolData(new MyType("String"), 0, 0, 0, 0, 0);
        
        testingSymbolTable.insert("test", symbolData);
        System.out.println("Running[testInsert]");
    }
}