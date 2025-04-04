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
    private String bufferCharacter = ":";

    public SymbolTable() {
        data = new HashMap<String, Symbol>();
    }

    public HashMap<String, Symbol> getData(){
        return data;
    }
    
    public boolean insert(String key, Symbol entry){
        if(key == null
            || key.trim().isEmpty()
            || entry == null
            || entry.type == null
        )
            return false;
        
        if (data.containsKey(key))
            return false;

        data.put(key, entry);
        return true;
    }

    public Symbol find(String key){
        return data.get(key);
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
