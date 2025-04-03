package picojava;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import syntaxtree.*;
import visitor.*;

// Symbol Table Visitor: Traverses AST to create symbol table.
public class SymbolTable<R, A> extends GJDepthFirst<Void, Integer> {
    private HashMap<String, Symbol> data;
    private ArrayList<String> scopeKey = new ArrayList<String>(4);

    public SymbolTable() {
        scopeKey.add("global");
        data = new HashMap<String, Symbol>();
    }

    public String scopeKeyString(){
        String buffer = "|";
        String output = "";
        for(int i = 0; i < scopeKey.size(); ++i){
            output += scopeKey.get(i);
            if(i < scopeKey.size() - 1){
                output += buffer;
            }
        }
        return output;
    }

    public void enterScope(String scopeName){
        scopeKey.add(scopeName);
    }

    public void exitScope(){
        if (!scopeKey.isEmpty()) {
            scopeKey.remove(scopeKey.size() - 1);
        }
    }
    
    public void insert(String identifier, Symbol entry){
        if(
               identifier == null
            || identifier.trim().isEmpty()
            || entry == null
            || entry.type == null
        )
            throw new IllegalArgumentException("Insert failed due to null argument(s)");
        
        String currentKey = scopeKeyString() + identifier;
        if (data.containsKey(currentKey))
            throw new RuntimeException("Trying to insert an existing symbol to the same scope: " + currentKey);
        
        data.put(currentKey, entry);
    }

    public String prettyPrint(){
        StringBuilder output = new StringBuilder();
        output.append("---Symbol-Table---\n");
        for(String key : data.keySet()){
            output.append(data.get(key));
        }
        return output.toString();
    }

    // we can use our SymbolTable's .enterScope(); method when entering a method
    // Override every visitor method
    // Each method will be based around adding symbols to the symbol table
    // visit(this, Integer) Integer will be used to track scope

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    @Override
    public Void visit(Goal n, Integer depth) {

        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        return null;
    }

}
