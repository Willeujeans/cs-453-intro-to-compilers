package picojava;
import syntaxtree.*;
import visitor.*;

public class PPrinter<R, A> extends GJDepthFirst<Void, String> {
    private final StringBuilder output = new StringBuilder();
    private final String indentChar = ">";

    public void printResult() {
        System.out.println("\n{ PPrinter } -> printResult()\n");
        System.out.println(output.toString());
        System.out.println("\n{ PPrinter } ---\n");
    }

    /**
    * Goal variables:
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
    @Override
    public Void visit(Goal n, String indent) {

        // f0 -> MainClass()
        n.f0.accept(this, indent + indentChar);

        output.append("\n");
        output.append("\n");

        // f1 -> ( TypeDeclaration() )*
        n.f1.accept(this, indent + indentChar);
        return null;
    }


    /**
    * This is the case of a Class that has a main method.
    * MainClass Variables:
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
    public Void visit(MainClass n, String indent) {
        n.f0.accept(this, indent + indentChar);
        output.append(" ");
        n.f1.accept(this, indent + indentChar);
        output.append(" ");
        n.f2.accept(this, indent + indentChar);
        
        output.append("\n").append(indent);
        n.f3.accept(this, indent + indentChar);
        output.append(" ");
        n.f4.accept(this, indent + indentChar);
        output.append(" ");
        n.f5.accept(this, indent + indentChar);
        output.append(" ");
        n.f6.accept(this, indent + indentChar);
        output.append(" ");
        n.f7.accept(this, indent + indentChar);
        output.append(" ");
        n.f8.accept(this, indent + indentChar);
        output.append(" ");
        n.f9.accept(this, indent + indentChar);
        n.f10.accept(this, indent + indentChar);
        output.append(" ");
        n.f11.accept(this, indent + indentChar);
        output.append(" ");
        n.f12.accept(this, indent + indentChar);
        output.append(" ");
        n.f13.accept(this, indent + indentChar);
        
        output.append("\n").append(indent).append(indent);
        n.f14.accept(this, indent + indentChar);
        n.f15.accept(this, indent + indentChar);
        
        output.append("\n").append(indent);
        n.f16.accept(this, indent + indentChar);
        
        output.append("\n");
        n.f17.accept(this, indent + indentChar);
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
    public Void visit(ClassDeclaration n, String indent){
        n.f0.accept(this, indent + indentChar);
        output.append(" ");
        n.f1.accept(this, indent + indentChar);
        output.append(" ");
        n.f2.accept(this, indent + indentChar);
        
        output.append("\n").append(indent);
        n.f3.accept(this, indent + indentChar);
        n.f4.accept(this, indent + indentChar);
        
        output.append("\n").append(indent);
        n.f5.accept(this, indent + indentChar);
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
    public Void visit(MethodDeclaration n, String indent){
        // f0 -> "public"
        n.f0.accept(this, indent + indentChar);
        output.append(" ");

        // f1 -> Type()
        n.f1.accept(this, indent + indentChar);
        output.append(" ");

        // f2 -> Identifier()
        n.f2.accept(this, indent + indentChar);
        output.append(" ");

        // f3 -> "("
        n.f3.accept(this, indent + indentChar);
        output.append(" ");

        // f4 -> ( FormalParameterList() )?
        n.f4.accept(this, indent + indentChar);
        output.append(" ");

        // f5 -> ")"
        n.f5.accept(this, indent + indentChar);
        output.append(" ");

        // f6 -> "{"
        n.f6.accept(this, indent + indentChar);
        output.append("\n");

        // f7 -> ( VarDeclaration() )*
        n.f7.accept(this, indent + indentChar);
        output.append("\n");

        // f8 -> ( Statement() )*
        n.f8.accept(this, indent + indentChar);
        output.append("\n");

        // f9 -> "return"
        n.f9.accept(this, indent + indentChar);
        output.append(" ");

        // f10 -> Expression()
        n.f10.accept(this, indent + indentChar);

        // f11 -> ";"
        n.f11.accept(this, indent + indentChar);
        output.append("\n");

        // f12 -> "}"
        n.f12.accept(this, indent + indentChar);
        output.append("\n");

        return null;
    }


    /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    public Void visit(Type n, String indent){
        n.f0.accept(this, indent + indentChar);
        return null;
    }


    /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
    public Void visit(FormalParameterList n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        return null;
    }


    /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    public Void visit(FormalParameter n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    public Void visit(FormalParameterRest n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
    public Void visit(VarDeclaration n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
    public Void visit(Statement n, String indent){
        n.f0.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
    public Void visit(AssignmentStatement n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        n.f3.accept(this, indent + indentChar);
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
    public Void visit(ArrayAssignmentStatement n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        n.f4.accept(this, indent + indentChar);
        n.f5.accept(this, indent + indentChar);
        n.f6.accept(this, indent + indentChar);
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
    public Void visit(IfStatement n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        n.f3.accept(this, indent + indentChar);
        n.f4.accept(this, indent + indentChar);
        n.f5.accept(this, indent + indentChar);
        n.f6.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
    public Void visit(WhileStatement n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        n.f3.accept(this, indent + indentChar);
        n.f4.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
    public Void visit(PrintStatement n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        n.f3.accept(this, indent + indentChar);
        n.f4.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    public Void visit(TypeDeclaration n, String indent){
        n.f0.accept(this, indent + indentChar);
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
    public Void visit(ClassExtendsDeclaration n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        n.f3.accept(this, indent + indentChar);
        n.f4.accept(this, indent + indentChar);
        n.f5.accept(this, indent + indentChar);
        n.f6.accept(this, indent + indentChar);
        n.f7.accept(this, indent + indentChar);
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
    public Void visit(Expression n, String indent) {
        n.f0.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
    @Override
    public Void visit(AndExpression n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "<"
    * f2 -> PrimaryExpression()
    */
    @Override
    public Void visit(CompareExpression n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
    @Override
    public Void visit(PlusExpression n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
    @Override
    public Void visit(MinusExpression n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
    @Override
    public Void visit(TimesExpression n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
    @Override
    public Void visit(ArrayLookup n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        n.f3.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
    @Override
    public Void visit(ArrayLength n, String indent){
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
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
    public Void visit(MessageSend n, String indent){
        output.append("   MESSAGE   \n");
        n.f0.accept(this, indent + indentChar);
        n.f1.accept(this, indent + indentChar);
        n.f2.accept(this, indent + indentChar);
        n.f3.accept(this, indent + indentChar);
        n.f4.accept(this, indent + indentChar);
        n.f5.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
    @Override
    public Void visit(PrimaryExpression n, String indent){
        n.f0.accept(this, indent + indentChar);
        return null;
    }

    /**
    * f0 -> <IDENTIFIER>
    */
    public Void visit(Identifier n, String indent){
        n.f0.accept(this, indent + indentChar);
        return null;
    }

    /**
    * NodeToken Variables:
    * Image -> name as String
    */
    @Override
    public Void visit(NodeToken n, String indent) {
        output.append(n.toString());
        return null;
    }
}
