package picojava;

import syntaxtree.*;
import visitor.*;
import java.util.HashMap;

public class TypeCheckSimp<R, A> extends GJDepthFirst<MyType, HashMap<String, String>> {
    public static void main(String[] args) {
        System.out.println("TypeCheckSimp");
    }
}
