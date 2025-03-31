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

    public SymbolTable() {
        scopes = new LinkedList<>();
        enterScope();
    }

    public LinkedList<HashMap<String, SymbolEntry>> getScopes(){
        if(this.scopes != null && !this.scopes.isEmpty()){
            return this.scopes;
        }else{
            System.err.println("SymbolTable: Trying to get an empty LinkedList");
            return null;
        }
    }
    
    public void insert(String identifier, SymbolEntry entry){
        // Adds symbols to the symbol table
    }

    public SymbolEntry lookup(String identifier){
        // Lookup will find the symbolEntry inside the inner most scope using the identifier and symbol table
        return null;
    }

    public boolean set(String identifier, int lineUsed){
        // Updates a symbol table entry, sucess will return true, failure will return false
        return false;
    }

    public void enterScope() {
        // Enter a new scope
    }

    public void exitScope() {
        // Exit current scope
    }

    public void reset(){
        // Reset all non-global scopes
    }

}
