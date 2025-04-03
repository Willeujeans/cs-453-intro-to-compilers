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
    private ArrayList<String> scope = new ArrayList<String>();
    private ArrayList<String> scopeTypes = new ArrayList<String>();
    private String bufferCharacter = ":";

    public SymbolTable() {
        enterGlobalScope();
        data = new HashMap<String, Symbol>();
    }

    public String scopeString(){
        String output = "";
        for(int i = 0; i < scope.size(); ++i){
            output += scope.get(i);
            output += bufferCharacter;
        }
        return output;
    }

    public void enterGlobalScope(){
        scopeTypes.add("global");
        scope.add("global");
    }

    public void enterClassScope(String classIdentifier){
        if(scopeTypes.get(scopeTypes.size() - 1) == "global"){
            scopeTypes.add("class");
            scope.add(classIdentifier);
        }else{
            System.err.println("Tried to enter a class scope while in correct scope");
        }
    }

    public void enterMethodScope(String methodIdentifier){
        if(scopeTypes.get(scopeTypes.size() - 1) == "class"){
            scopeTypes.add("method");
            scope.add(methodIdentifier);
        }else{
            System.err.println("Tried to enter a method scope while in correct scope");
        }
    }

    public void exitScope(){
        int removeElm = scope.size() - 1;
        if(scopeTypes.get(removeElm) != "global"){
            scope.remove(removeElm);
            scopeTypes.remove(removeElm);
        }
    }
    
    public boolean insert(String identifier, Symbol entry){
        if(
            identifier == null
            || identifier.trim().isEmpty()
            || entry == null
            || entry.type == null
        )
            return false;
        
        String currentKey = scopeString() + identifier;
        
        if (data.containsKey(currentKey))
            return false;

        data.put(currentKey, entry);
        return true;
    }

    public Symbol find(String key){
        String[] finderKeySplit = (key + scope).split(bufferCharacter);
        ArrayList<String> finderKey = new ArrayList<String>();
        for (int i = 0; i < finderKeySplit.length; i++) {
            finderKey.add(finderKeySplit[i]);
        }

        // If we cannot find it in this scope, we will move up
        while(finderKey.size() > 1){
            if (data.containsKey(finderKey.toString()))
                return data.get(key);
            finderKey.remove(finderKey.size() - 1);
        }
        throw new RuntimeException("[SymbolTable] This symbol was never defined!");
    }

    public String prettyPrint(){
        StringBuilder output = new StringBuilder();
        output.append("\n---Symbol-Table---\n");
        for(String key : data.keySet()){
            output.append("(");
            output.append(key);
            output.append(") : ");
            output.append("[");
            output.append(data.get(key));
            output.append("]\n");
        }
        output.append("---------\n");
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
