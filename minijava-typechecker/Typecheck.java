// Helper for HW2/CS453.
import java.io.*;
import visitor.*;
import syntaxtree.*;
import java.util.*;
// Files are stored in the picojava directory/package.
import picojava.*;

public class Typecheck {
    public static void main(String[] args) {
	Node root = null;
	try {
	    root = new MiniJavaParser(System.in).Goal();

	    // Pretty-print the tree. PPrinter inherits from
	    // GJDepthFirst<R,A>. R=Void, A=String.
		
	    PPrinter<Void,String> pp = new PPrinter<Void,String>();
	    root.accept(pp, "");
		pp.printResult();
	    // Build the symbol table. Top-down visitor, inherits from
	    // GJDepthFirst<R,A>. R=Void, A=Integer.

	    // SymTableVis<Void, Integer> pv = new SymTableVis<Void,Integer>();
	    // root.accept(pv, 0);
	    // HashMap<String, String> symt = pv.symt;

	    // Do type checking. Bottom-up visitor, also inherits from
	    // GJDepthFirst. Visit functions return MyTpe (=R), and
	    // take a symbol table (HashMap<String,String>) as
	    // argument (=A). You may implement things differently of
	    // course!

	    // TypeCheckSimp ts = new TypeCheckSimp();
	    // MyType res = root.accept(ts, symt);

	    // Ugly code not to be inspired from: "my" way of storing
	    // type info / typecheck property: if some of my internal
	    // structure is empty, then things don't typecheck for
	    // me. This is specific to my own implementation.

	    // if (res != null && res.type_array.size() > 0)
	    // 	System.out.println("Code typechecks");
	    // else
	    // 	System.out.println("Type error");
	}
	catch (ParseException e) {
	    System.out.println(e.toString());
	    System.exit(1);
	}
    }
}
