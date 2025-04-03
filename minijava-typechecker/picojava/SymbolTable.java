package picojava;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import syntaxtree.*;

public class SymbolTable {
    private final HashMap<String, Symbol> data;
    private List<String> scopeKey;

    public SymbolTable() {
        scopeKey = new List<String>();
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
        for(Node item : data.keySet()){
        }
        return output.toString();
    }
}
