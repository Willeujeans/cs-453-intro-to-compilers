import syntaxtree.*;
import visitor.*;

public class PPrinter<R, A> extends GJDepthFirst<Void, String> {
    public static void main(String[] args) {
        System.out.println("PPrinter");
    }
}
