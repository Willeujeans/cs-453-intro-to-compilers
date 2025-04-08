package picojava;

import java.beans.Expression;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import syntaxtree.*;
import visitor.*;

// Symbol Table Visitor: Traverses AST to create symbol table.
public class SymbolTable<R, A> extends GJDepthFirst<Void, String> {
    private HashMap<String, Symbol> data;
    private String bufferChar = ":";

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
        ){
            return false;
        }

        
        if (data.containsKey(key)) {
            System.out.println("❌ [SymbolTable] Type Error: duplicate (" + key + ")");
            System.exit(1);
        }

        data.put(key, entry);
        return true;
    }

    public Symbol find(String key){
        return data.get(key);
    }

    public void prettyPrint(){
        System.out.println("---Symbol-Table---");
        StringBuilder output = new StringBuilder();
        ArrayList<String> keys = new ArrayList<String>(data.keySet());
        Collections.sort(keys, Comparator.comparingInt(String::length));

        for(String key : keys){
            System.out.print(key);
            System.out.print(" -> " + data.get(key) + "\n");
        }
        System.out.println("------------------");
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
        // MainClass addition
        insert(key + bufferChar + n.f1.f0.toString(), new Symbol(new MyType("mainClass"), n.f0.beginLine));
        
        // Argument in mainClass addition
        String currentScope = key + bufferChar + n.f1.f0.toString() + bufferChar + "main";
        insert(currentScope + bufferChar + n.f11.f0.toString(), new Symbol(new MyType("String", "[", "]"), n.f8.beginLine));

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
        String currentScope = key + bufferChar + n.f1.f0.toString();
        insert(currentScope, new Symbol(new MyType("class"), n.f0.beginLine));
        n.f3.accept(this, currentScope);
        n.f4.accept(this, currentScope);
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
     * f0 -> "public"
     * f1 -> Type()
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( FormalParameterList() )?
     * f5 -> ")"
     * f6 -> "{"
     * f7 -> ( VarDeclaration() )*
     * f8 -> ( Statement() )*
     * f9 -> "return"
     * f10 -> Expression()
     * f11 -> ";"
     * f12 -> "}"
     */
    @Override
    public Void visit(MethodDeclaration n, String key) {
        String currentScope = key + bufferChar + n.f2.f0.toString();

        n.f1.accept(this, currentScope);
        n.f4.accept(this, currentScope);
        n.f7.accept(this, currentScope);
        n.f8.accept(this, currentScope);
        n.f10.accept(this, currentScope);

        return null;
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> ( FormalParameterRest() )*
     */
    @Override
    public Void visit(FormalParameterList n, String key) {
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        return null;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    @Override
    public Void visit(FormalParameter n, String key) {
        n.f0.accept(this, key + bufferChar + n.f1.f0.toString());
        return null;
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    @Override
    public Void visit(FormalParameterRest n, String key) {
        n.f1.accept(this, key);
        return null;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    @Override
    public Void visit(VarDeclaration n, String key) {
        n.f0.accept(this, key + bufferChar + n.f1.f0.toString());
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
        insert(key, new Symbol(new MyType("int", "[", "]"), n.f0.beginLine));
        return null;
    }

    /**
     * f0 -> "boolean"
     */
    @Override
    public Void visit(BooleanType n, String key) {
        insert(key, new Symbol(new MyType("boolean"), n.f0.beginLine));
        return null;
    }

    /**
     * f0 -> "int"
     */
    @Override
    public Void visit(IntegerType n, String key) {
        insert(key, new Symbol(new MyType("int"), n.f0.beginLine));
        return null;
    }
}
