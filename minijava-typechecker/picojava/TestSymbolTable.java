package picojava;

public class TestSymbolTable {
    public TestSymbolTable(){
        testInsert();
    }

    public static void testInsert() {
        System.out.println("[TestSymbolTable] running: testInsert");

        SymbolTable testingSymbolTable = new SymbolTable();
        SymbolData symbolData = new SymbolData(new MyType("String"), 0, 0, 0, 0, 0);
        testingSymbolTable.insert("a", symbolData);
    }
}