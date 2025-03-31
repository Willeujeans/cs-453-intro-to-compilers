package picojava;

import syntaxtree.*;
import visitor.*;

// Symbol Table Visitor: Traverses AST to create symbol table.
public class SymTableVis<R, A> extends GJDepthFirst<Void, Integer> {
    private SymbolTable symbolTable = new SymbolTable();
    private int lineNumber;

    public SymbolTable getSymbolTable() {
        return symbolTable;
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
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        n.f4.accept(this, depth);
        n.f5.accept(this, depth);
        n.f6.accept(this, depth);
        n.f7.accept(this, depth);
        n.f8.accept(this, depth);
        n.f9.accept(this, depth);
        n.f10.accept(this, depth);
        n.f11.accept(this, depth);
        n.f12.accept(this, depth);
        n.f13.accept(this, depth);
        n.f14.accept(this, depth);
        n.f15.accept(this, depth);
        n.f16.accept(this, depth);
        n.f17.accept(this, depth);
        return null;
    }

    /**
     * f0 -> ClassDeclaration()
     * | ClassExtendsDeclaration()
     */
    @Override
    public Void visit(TypeDeclaration n, Integer depth) {
        
        n.f0.accept(this, depth);
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
    public Void visit(ClassDeclaration n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        n.f4.accept(this, depth);
        n.f5.accept(this, depth);
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
    public Void visit(ClassExtendsDeclaration n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        n.f4.accept(this, depth);
        n.f5.accept(this, depth);
        n.f6.accept(this, depth);
        n.f7.accept(this, depth);
        return null;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    @Override
    public Void visit(VarDeclaration n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
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
    public Void visit(MethodDeclaration n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        n.f4.accept(this, depth);
        n.f5.accept(this, depth);
        n.f6.accept(this, depth);
        n.f7.accept(this, depth);
        n.f8.accept(this, depth);
        n.f9.accept(this, depth);
        n.f10.accept(this, depth);
        n.f11.accept(this, depth);
        n.f12.accept(this, depth);
        return null;
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> ( FormalParameterRest() )*
     */
    @Override
    public Void visit(FormalParameterList n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        return null;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    @Override
    public Void visit(FormalParameter n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        return null;
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    @Override
    public Void visit(FormalParameterRest n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        return null;
    }

    /**
     * f0 -> ArrayType()
     * | BooleanType()
     * | IntegerType()
     * | Identifier()
     */
    @Override
    public Void visit(Type n, Integer depth) {
        
        n.f0.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "int"
     * f1 -> "["
     * f2 -> "]"
     */
    @Override
    public Void visit(ArrayType n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "boolean"
     */
    @Override
    public Void visit(BooleanType n, Integer depth) {
        
        n.f0.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "int"
     */
    @Override
    public Void visit(IntegerType n, Integer depth) {
        
        n.f0.accept(this, depth);
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
    public Void visit(Statement n, Integer depth) {
        
        n.f0.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "{"
     * f1 -> ( Statement() )*
     * f2 -> "}"
     */
    @Override
    public Void visit(Block n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        return null;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    @Override
    public Void visit(AssignmentStatement n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        return null;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "["
     * f2 -> Expression()
     * f3 -> "]"
     * f4 -> "="
     * f5 -> Expression()
     * f6 -> ";"
     */
    @Override
    public Void visit(ArrayAssignmentStatement n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        n.f4.accept(this, depth);
        n.f5.accept(this, depth);
        n.f6.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "if"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     * f5 -> "else"
     * f6 -> Statement()
     */
    @Override
    public Void visit(IfStatement n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        n.f4.accept(this, depth);
        n.f5.accept(this, depth);
        n.f6.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "while"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> Statement()
     */
    @Override
    public Void visit(WhileStatement n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        n.f4.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "System.out.println"
     * f1 -> "("
     * f2 -> Expression()
     * f3 -> ")"
     * f4 -> ";"
     */
    @Override
    public Void visit(PrintStatement n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        n.f4.accept(this, depth);
        return null;
    }

    /**
     * f0 -> AndExpression()
     * | CompareExpression()
     * | PlusExpression()
     * | MinusExpression()
     * | TimesExpression()
     * | ArrayLookup()
     * | ArrayLength()
     * | MessageSend()
     * | PrimaryExpression()
     */
    @Override
    public Void visit(Expression n, Integer depth) {
        
        n.f0.accept(this, depth);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    @Override
    public Void visit(AndExpression n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    @Override
    public Void visit(CompareExpression n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    @Override
    public Void visit(PlusExpression n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    @Override
    public Void visit(MinusExpression n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    @Override
    public Void visit(TimesExpression n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    @Override
    public Void visit(ArrayLookup n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    @Override
    public Void visit(ArrayLength n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
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
    public Void visit(MessageSend n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        n.f4.accept(this, depth);
        n.f5.accept(this, depth);
        return null;
    }

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    @Override
    public Void visit(ExpressionList n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        return null;
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    @Override
    public Void visit(ExpressionRest n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
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
    public Void visit(PrimaryExpression n, Integer depth) {
        
        n.f0.accept(this, depth);
        return null;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    @Override
    public Void visit(IntegerLiteral n, Integer depth) {
        
        n.f0.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "true"
     */
    @Override
    public Void visit(TrueLiteral n, Integer depth) {
        
        n.f0.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "false"
     */
    @Override
    public Void visit(FalseLiteral n, Integer depth) {
        
        n.f0.accept(this, depth);
        return null;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public Void visit(Identifier n, Integer depth) {
        
        n.f0.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "this"
     */
    @Override
    public Void visit(ThisExpression n, Integer depth) {
        
        n.f0.accept(this, depth);
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
    public Void visit(ArrayAllocationExpression n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        n.f4.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    @Override
    public Void visit(AllocationExpression n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        n.f3.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    @Override
    public Void visit(NotExpression n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        return null;
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    @Override
    public Void visit(BracketExpression n, Integer depth) {
        
        n.f0.accept(this, depth);
        n.f1.accept(this, depth);
        n.f2.accept(this, depth);
        return null;
    }

}
