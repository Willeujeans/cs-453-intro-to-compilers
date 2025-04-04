package picojava;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import syntaxtree.*;
import visitor.*;

// Symbol Table Visitor: Traverses AST to create symbol table.
public class SymbolTable<R, A> extends GJDepthFirst<Void, String> {
    private HashMap<String, Symbol> data;
    private ArrayList<String> scope = new ArrayList<String>();
    private ArrayList<ScopeTypes> scopeTracker = new ArrayList<ScopeTypes>();
    private String bufferCharacter = ":";

    private enum ScopeTypes {
        GLOBAL,
        CLASS,
        METHOD,
        VARIABLE
    }

    public SymbolTable() {
        enterGlobalScope();
        data = new HashMap<String, Symbol>();
    }

    public HashMap<String, Symbol> getData(){
        return data;
    }

    public ArrayList getScope(){
        return scope;
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
        if(scope.isEmpty()){
            scopeTracker.add(ScopeTypes.GLOBAL);
            scope.add("global");
        }else{
            throw new RuntimeException("Tried to enter a global scope while in incorrect scope");
        }
    }

    public void enterClassScope(String classIdentifier){
        ScopeTypes lastItem = scopeTracker.get(scopeTracker.size() - 1);
        if(lastItem == ScopeTypes.GLOBAL){
            scopeTracker.add(ScopeTypes.CLASS);
            scope.add(classIdentifier);
        }else{
            System.err.println((scopeTracker.get(scope.size() - 1)).toString());
            throw new RuntimeException("Tried to enter a class scope while in scope: ");
            
        }
    }

    public void enterMethodScope(String methodIdentifier){
        ScopeTypes lastItem = scopeTracker.get(scopeTracker.size() - 1);
        if(lastItem == ScopeTypes.CLASS){
            scopeTracker.add(ScopeTypes.METHOD);
            scope.add(methodIdentifier);
        }else{
            System.err.println((scopeTracker.get(scope.size() - 1)).toString());
            throw new RuntimeException("Tried to enter a class scope while in scope: ");
        }
    }

    public void enterVariableScope(String varIdentifier){
        ScopeTypes lastItem = scopeTracker.get(scopeTracker.size() - 1);

        if(lastItem == ScopeTypes.CLASS || lastItem == ScopeTypes.METHOD){
            scopeTracker.add(ScopeTypes.VARIABLE);
            scope.add(varIdentifier);
        }else{
            System.err.println((scopeTracker.get(scope.size() - 1)).toString());
            prettyPrint();
            throw new RuntimeException("Tried to enter a variable scope while in scope: ");
        }
    }

    public void exitScope() {
        System.out.println(scopeTracker);
        if (scope.size() > 1) {
            int lastIndex = scope.size() - 1;
            scope.remove(lastIndex);
            scopeTracker.remove(lastIndex);
        }
        System.out.println(scopeTracker);
    }
    
    public boolean insert(Symbol entry){
        if(
            entry == null
            || entry.type == null
        )
            return false;
        
        if (data.containsKey(scopeString()))
            return false;

        data.put(scopeString(), entry);
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
            
                if(!finderKey.isEmpty())
                finderKey.remove(finderKey.size() - 1);
        }
        throw new RuntimeException("[SymbolTable] This symbol was never defined!");
    }

    public void prettyPrint(){
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
        output.append("------------------\n");
        System.out.println(output.toString());
    }

    // All symbol types
    // variable declaration
    // variable assignment
    // class
    // method
    // statments

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    @Override
    public Void visit(Goal n, String key) {
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        return null;
    }
}
