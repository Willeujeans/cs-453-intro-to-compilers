package picojava;
import syntaxtree.*;
import visitor.*;

import java.util.HashMap;

public class TypeValidator extends GJDepthFirst<MyType, String> {
    HashMap<String, Symbol> symbolTableData;
    private String bufferChar = ":";

    public TypeValidator(HashMap<String, Symbol> symbolTableData){
        this.symbolTableData = new HashMap<String, Symbol>(symbolTableData);
    }

    // return the type to check it
    // example situation:
    // (Mismatching assignment operator)
    // int x;
    // x = false;

    // VISIT METHODS

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    @Override
    public MyType visit(Goal n, String key) {
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
    public MyType visit(MainClass n, String key) {
        System.out.println("Validating MainClass...");
        String currentScope = key + bufferChar + n.f1.f0.toString() + bufferChar + "main";
        
        // f15 -> ( Statement() )*
        n.f15.accept(this, currentScope);
        
        return null;
    }

    /**
     * f0 -> Block()
     * | AssignmentStatement()
     * | ArrayAssignmentStatement()
     * | IfStatement()
     * | WhileStatement()
     * | PrintStatement()
     */
    @Override
    public MyType visit(Statement n, String key) {
        n.f0.accept(this, key);
        return null;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    @Override
    public MyType visit(AssignmentStatement n, String key) {
        n.f0.accept(this, key); // look up in symboltable
        n.f2.accept(this, key);
        return null;
    }

   /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
    @Override
    public MyType visit(Expression n, String key) {
        n.f0.accept(this, key);
        return new MyType("int");
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(AndExpression n, String key) {
        
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(CompareExpression n, String key) {
        
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        return null;
    }


    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(PlusExpression n, String key) {
        
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(MinusExpression n, String key) {
        
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(TimesExpression n, String key) {
        
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    @Override
    public MyType visit(ArrayLookup n, String key) {
        
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        n.f3.accept(this, key);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    @Override
    public MyType visit(ArrayLength n, String key) {
        
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( ExpressionList() )?
     * f5 -> ")"
     */
    @Override
    public MyType visit(MessageSend n, String key) {
        
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        n.f3.accept(this, key);
        n.f4.accept(this, key);
        n.f5.accept(this, key);
        return null;
    }

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    @Override
    public MyType visit(ExpressionList n, String key) {
        
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        return null;
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    @Override
    public MyType visit(ExpressionRest n, String key) {
        
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        return null;
    }

    /**
     * f0 -> IntegerLiteral()
     * | TrueLiteral()
     * | FalseLiteral()
     * | Identifier()
     * | ThisExpression()
     * | ArrayAllocationExpression()
     * | AllocationExpression()
     * | NotExpression()
     * | BracketExpression()
     */
    @Override
    public MyType visit(PrimaryExpression n, String key) {
        n.f0.accept(this, key); // type that we return
        return null;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    @Override
    public MyType visit(IntegerLiteral n, String key) {
        // return an int type
        n.f0.accept(this, key);
        return null;
    }

    /**
     * f0 -> "true"
     */
    @Override
    public MyType visit(TrueLiteral n, String key) {
        // return a boolean type
        n.f0.accept(this, key);
        return null;
    }

    /**
     * f0 -> "false"
     */
    @Override
    public MyType visit(FalseLiteral n, String key) {
        // return a boolean type
        n.f0.accept(this, key);
        return null;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public MyType visit(Identifier n, String key) {
        // look up IDENTIFIER's type
        n.f0.accept(this, key);
        return null;
    }

    /**
     * f0 -> "this"
     */
    @Override
    public MyType visit(ThisExpression n, String key) {
        // look up vars the class has to see if we are accessing one that exists
        // look up type of var in class
        n.f0.accept(this, key);
        return null;
    }

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     */
    @Override
    public MyType visit(ArrayAllocationExpression n, String key) {
        // return int[] type
        n.f3.accept(this, key);
        return null;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    @Override
    public MyType visit(AllocationExpression n, String key) {
        // Unsure how to handle yet
        n.f1.accept(this, key);
        return null;
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    @Override
    public MyType visit(NotExpression n, String key) {
        // return expression type, I think only booleans can use this
        n.f1.accept(this, key);
        return null;
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    @Override
    public MyType visit(BracketExpression n, String key) {
        // return expression's type
        n.f1.accept(this, key);
        return null;
    }
}
