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
        output.append(indent).append("[ Goal ] (Root)\n");
    
        // f0 -> MainClass()
        output.append(indent).append("[ MainClass ]:\n");
        n.f0.accept(this, indent + ">");
    
        // f1 -> ( TypeDeclaration() )*
        output.append(indent).append("[ TypeDeclarations ]:\n");
        n.f1.accept(this, indent + ">");

        return null;
    }
}
