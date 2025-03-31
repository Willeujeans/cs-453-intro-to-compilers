package picojava;
import java.util.HashMap;
import java.util.LinkedList;

public class SymbolTable {
    private LinkedList<HashMap<String, Symbol>> scopes;

    public SymbolTable() {
        System.out.println("[SymbolTable]: Constructed");
        scopes = new LinkedList<>();
        enterScope();
    }

    public LinkedList<HashMap<String, Symbol>> getScopes(){
        if(this.scopes != null && !this.scopes.isEmpty()){
            return this.scopes;
        }else{
            System.err.println("SymbolTable: Trying to get an empty LinkedList");
            return null;
        }
    }
    
    public void insert(String identifier, Symbol entry){
        if(identifier == null){
            throw new IllegalArgumentException("Insert failed due to empty identifier");
        }
        if(entry == null){
            throw new IllegalArgumentException("Insert failed due to empty entry");
        }

        // Adds symbols to the symbol table
        if (scopes.peek().containsKey(identifier)) {
            throw new RuntimeException("Trying to insert an existing symbol to the same scope: " + identifier);
        }else{
            scopes.peek().put(identifier, entry);
        }
    }

    public Symbol lookup(String identifier){
        // Lookup will find the symbolEntry inside the inner most scope using the identifier and symbol table
        return null;
    }

    public boolean set(String identifier, int lineUsed){
        // Updates a symbol table entry, sucess will return true, failure will return false
        return false;
    }

    public void enterScope() {
        // Enter a new scope
        scopes.push(new HashMap<>());
    }

    public void exitScope() {
        // Exit current scope
    }

    public void reset(){
        // Reset all non-global scopes
    }

}
