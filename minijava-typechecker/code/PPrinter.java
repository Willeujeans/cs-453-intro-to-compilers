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
}
