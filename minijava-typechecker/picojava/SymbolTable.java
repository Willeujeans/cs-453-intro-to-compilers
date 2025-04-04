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

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> "public"
     * f4 -> "static"
     * f5 -> "void"
     * f6 -> "main"
     * f7 -> "("
     * f8 -> "String"
     * f9 -> "["
     * f10 -> "]"
     * f11 -> Identifier()
     * f12 -> ")"
     * f13 -> "{"
     * f14 -> ( VarDeclaration() )*
     * f15 -> ( Statement() )*
     * f16 -> "}"
     * f17 -> "}"
     */
    @Override
    public Void visit(MainClass n, String key) {
        System.out.println("visit(MainClass)");
        insert(key + ":" + n.f1.f0.toString(), new Symbol(new MyType("mainClass"), 0));
        
        String currentScope = key + ":" + n.f1.f0.toString() + ":" + "main";
        insert(currentScope + ":" + n.f11.f0.toString(), new Symbol(new MyType("String", "[", "]"), 0));

        n.f14.accept(this, currentScope);
        n.f15.accept(this, currentScope);
        return null;
    }

    /**
     * f0 -> ClassDeclaration()
     * | ClassExtendsDeclaration()
     */
    @Override
    public Void visit(TypeDeclaration n, String key) {
        n.f0.accept(this, key);
        return null;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> ( VarDeclaration() )*
     * f4 -> ( MethodDeclaration() )*
     * f5 -> "}"
     */
    @Override
    public Void visit(ClassDeclaration n, String key) {
        n.f3.accept(this, key + n.f1.f0.toString());
        n.f4.accept(this, key + n.f1.f0.toString());
        return null;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "extends"
     * f3 -> Identifier()
     * f4 -> "{"
     * f5 -> ( VarDeclaration() )*
     * f6 -> ( MethodDeclaration() )*
     * f7 -> "}"
     */
    @Override
    public Void visit(ClassExtendsDeclaration n, String key) {
        // Not sure how to handle this yet
        return null;
    }


    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    @Override
    public Void visit(VarDeclaration n, String key) {
        n.f0.accept(this, key + ":" + n.f1.f0.toString());
        return null;
    }

    /**
     * f0 -> ArrayType()
     * | BooleanType()
     * | IntegerType()
     * | Identifier()
     */
    @Override
    public Void visit(Type n, String key) {
        n.f0.accept(this, key);
        return null;
    }

    /**
     * f0 -> "int"
     * f1 -> "["
     * f2 -> "]"
     */
    @Override
    public Void visit(ArrayType n, String key) {
        insert(key, new Symbol(new MyType("int", "[", "]"), 0));
        return null;
    }

    /**
     * f0 -> "boolean"
     */
    @Override
    public Void visit(BooleanType n, String key) {
        insert(key, new Symbol(new MyType("boolean"), 0));
        return null;
    }

    /**
     * f0 -> "int"
     */
    @Override
    public Void visit(IntegerType n, String key) {
        System.out.println("Just added an int type!");
        System.out.println(key);
        insert(key, new Symbol(new MyType("int"), 0));
        return null;
    }

}
