import syntaxtree.*;
import visitor.*;

public class PPrinter<R, A> extends GJDepthFirst<Void, String> {
    private final StringBuilder output = new StringBuilder();

    public void printResult() {
        System.out.println("\n{ PPrinter } -> printResult()");
        System.out.println(output.toString());
        System.out.println("{ PPrinter } ---\n");
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


    /**
    * Goal variables:
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
    @Override
    public Void visit(Goal n, String indent) {
        output.append(indent);
        output.append(indent).append("[ Goal ] (Root)");
        output.append("\n");
    
        // f0 -> MainClass()
        output.append(indent).append("[ MainClass ]:");
        output.append("\n");
        n.f0.accept(this, indent + ">");
    
        // f1 -> ( TypeDeclaration() )*
        output.append(indent).append("[ TypeDeclarations ]:");
        output.append("\n");
        n.f1.accept(this, indent + ">");

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
    * 
    * public static void main(String[] a){
    */
    @Override
    public Void visit(MainClass n, String indent) {
        output.append(indent);
        output.append(" [ ");
        n.f0.accept(this, indent);
        output.append(" ] ");
        n.f1.accept(this, indent);
        output.append(" ");
        n.f2.accept(this, indent);
        output.append("\n");
        
        // public static void main(String[] a){
        String methodIndent = indent + ">";
        output.append(methodIndent);
        n.f3.accept(this, indent);
        output.append(" ");
        n.f4.accept(this, indent);
        output.append(" ");
        n.f5.accept(this, indent);
        output.append(" ");
        n.f6.accept(this, indent);
        n.f7.accept(this, indent);
        output.append(" ");
        n.f8.accept(this, indent);
        output.append(" ");
        n.f9.accept(this, indent);
        output.append(" ");
        n.f10.accept(this, indent);
        output.append(" ");
        n.f11.accept(this, indent);
        output.append(" ");
        n.f12.accept(this, indent);
        output.append(" ");

        n.f13.accept(this, indent);
        output.append(" \n");
        output.append(indent + ">>");
        
        String bodyIndent = methodIndent + ">>";
        n.f14.accept(this, bodyIndent);
        output.append(" ");

        n.f15.accept(this, bodyIndent);
        output.append(" \n");
        
        output.append(indent + ">");
        n.f16.accept(this, indent);
        output.append(" \n");

        output.append(indent);
        n.f17.accept(this, indent);
        output.append(" \n");

        return null;
    }

    
    /**
    * f0 -> <IDENTIFIER>
    */
    public Void visit(Identifier n, String indent){
        output.append("id( ");
        n.f0.accept(this, indent);
        output.append(" )");
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
        indent += ">";
        output.append("\n");
        output.append(indent);
        output.append("[ MethodDeclaration ]: ");
        output.append("\n");

        // f0 -> "public"
        output.append(indent);
        n.f0.accept(this, indent);
        output.append(" ");

        // f1 -> Type()
        n.f1.accept(this, indent);
        output.append(" ");

        // f2 -> Identifier()
        n.f2.accept(this, indent);
        output.append(" ");

        // f3 -> "("
        n.f3.accept(this, indent);
        output.append(" ");

        // f4 -> ( FormalParameterList() )?
        n.f4.accept(this, indent);
        output.append(" ");

        // f5 -> ")"
        n.f5.accept(this, indent);
        output.append(" ");

        // f6 -> "{"
        n.f6.accept(this, indent);
        output.append("\n");
        indent += ">";

        // f7 -> ( VarDeclaration() )*
        output.append(indent);
        n.f7.accept(this, indent);
        output.append("\n");

        // f8 -> ( Statement() )*
        output.append(indent);
        n.f8.accept(this, indent);
        output.append("\n");

        // f9 -> "return"
        output.append(indent);
        n.f9.accept(this, indent);
        output.append(" ");

        // f10 -> Expression()
        n.f10.accept(this, indent);

        // f11 -> ";"
        n.f11.accept(this, indent);
        output.append("\n");
        indent = ">";

        // f12 -> "}"
        output.append(indent);
        n.f12.accept(this, indent);
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
        output.append("Type( ");
        n.f0.accept(this, indent);
        output.append(" )");
        return null;
    }


    /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
    public Void visit(FormalParameterList n, String indent){
        output.append("[ FormalParameterList ]: ");
        n.f0.accept(this, indent);
        n.f1.accept(this, indent);
        return null;
    }


    /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
    public Void visit(FormalParameter n, String indent){
        n.f0.accept(this, indent);
        n.f1.accept(this, indent);
        return null;
    }

    /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
    public Void visit(FormalParameterRest n, String indent){
        n.f0.accept(this, indent);
        n.f1.accept(this, indent);
        return null;
    }

    /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
    public Void visit(VarDeclaration n, String indent){
        output.append("[VarDeclaration] ");
        n.f0.accept(this, indent);
        output.append(" : ");
        n.f1.accept(this, indent);
        output.append(" ");
        n.f2.accept(this, indent);
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
        output.append("[Statement : ");
        indent += ">";
        n.f0.accept(this, indent);
        return null;
    }

    /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
    public Void visit(AssignmentStatement n, String indent){
        output.append("AssignmentStatement]: \n");
        output.append(indent);
        n.f0.accept(this, indent);
        n.f1.accept(this, indent);
        n.f2.accept(this, indent);
        n.f3.accept(this, indent);
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
        output.append("ArrayAssignmentStatement]: \n");
        output.append(indent);
        n.f0.accept(this, indent);
        n.f1.accept(this, indent);
        n.f2.accept(this, indent);
        n.f4.accept(this, indent);
        n.f5.accept(this, indent);
        n.f6.accept(this, indent);
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
        output.append("IfStatement]: \n");
        output.append(indent);
        n.f0.accept(this, indent);
        n.f1.accept(this, indent);
        n.f2.accept(this, indent);
        n.f3.accept(this, indent);
        n.f4.accept(this, indent);
        n.f5.accept(this, indent);
        n.f6.accept(this, indent);
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
        output.append("WhileStatement]: \n");
        output.append(indent);
        n.f0.accept(this, indent);
        n.f1.accept(this, indent);
        n.f2.accept(this, indent);
        n.f3.accept(this, indent);
        n.f4.accept(this, indent);
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
        output.append("PrintStatement]: \n");
        output.append(indent);
        n.f0.accept(this, indent);
        n.f1.accept(this, indent);
        n.f2.accept(this, indent);
        n.f3.accept(this, indent);
        n.f4.accept(this, indent);
        return null;
    }
}
