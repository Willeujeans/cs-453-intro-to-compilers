package picojava;
import java.util.HashMap;
import java.util.LinkedList;

public class SymbolTable {
    private LinkedList<HashMap<String, SymbolEntry>> scopes;
    
    public static class SymbolEntry {
        public final MyType type = new MyType("");
        public final int size = 0;
        public final int dimension = 0;
        public final int lineDeclared = 0;
        public final int lineUsed = 0;
        public final int address = 0;
    }

}
