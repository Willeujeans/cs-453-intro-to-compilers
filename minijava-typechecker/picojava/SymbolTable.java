package picojava;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import syntaxtree.*;

public class SymbolTable {

    // A scope is defined as: HashMap<String, Symbol>
    //   Where String is the identifier
    //   symbol is the information reguarding the symbol
    private final Deque<HashMap<String, Symbol>> stackOfScopes = new ArrayDeque<>();
    private final Map<Node, HashMap<String, Symbol>> ASTNodeScopeDictionary = new HashMap<>();

    public SymbolTable() {
        stackOfScopes.push(new HashMap<>());
    }

    public void enterScope() {
        stackOfScopes.push(new HashMap<>());
    }

    public void exitScope() {
        if (stackOfScopes.size() > 1) {
            stackOfScopes.pop();
        }
    }
    
    public void insert(String identifier, Symbol entry){
        if(identifier == null || entry == null)
            throw new IllegalArgumentException("Insert failed due to null argument(s)");
        
        if (stackOfScopes.peek().containsKey(identifier))
            throw new RuntimeException("Trying to insert an existing symbol to the same scope: " + identifier);
        
        stackOfScopes.peek().put(identifier, entry);
    }

    // Grab the most inner scope indentifier that matches
    public Symbol resolveSymbol(String identifier) {
        for (HashMap<String, Symbol> scope : stackOfScopes) {
            if (scope.containsKey(identifier))
                return scope.get(identifier);
        }
        return null;
    }

    public String prettyPrint(){
        StringBuilder output = new StringBuilder();
        output.append("---Symbol-Table---\n");
        for(Node item : ASTNodeScopeDictionary.keySet()){
            System.out.println("\n");
            HashMap<String, Symbol> current_scope = ASTNodeScopeDictionary.get(item);
            output.append(" ________________________________________\n");
            output.append("( id, type, dimension, LoD, LoU, Address )\n");
            output.append(" ----------------------------------------\n");

            for(String key : current_scope.keySet()){
                output.append("( ");
                output.append(key);
                output.append(", ");
                Symbol symbol = current_scope.get(key);
                output.append(symbol.type.getType());
                output.append(", ");
                output.append(symbol.size);
                output.append(", ");
                output.append(symbol.dimension);
                output.append(", ");
                output.append(symbol.lineDeclared);
                output.append(", ");
                output.append(symbol.lineUsed);
                output.append(", ");
                output.append(symbol.address);
                output.append(" )");
            }
        }
        return output.toString();
    }
}
