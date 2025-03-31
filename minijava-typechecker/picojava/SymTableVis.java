package picojava;
import syntaxtree.*;
import visitor.*;

// Symbol Table Visitor: Traverses AST to create symbol table.
public class SymTableVis<R, A> extends GJDepthFirst<Void, Integer> {
    // Override every visitor method
    // Each method will be based around adding symbols to the symbol table
    // visit(this, Integer) Integer will be used to track scope


    /**
    * Goal variables:
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
    @Override
    public Void visit(Goal n, Integer depth) {
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
    public Void visit(MainClass n, Integer depth) {
        return null;
    }

}
