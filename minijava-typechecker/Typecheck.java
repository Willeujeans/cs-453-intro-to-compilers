// Helper for HW2/CS453.
import java.io.*;
import java.text.ParseException;

import visitor.*;
import syntaxtree.*;
import java.util.*;
// Files are stored in the picojava directory/package.
import picojava.*;

public class Typecheck {
    public static void main(String[] args) {
		try {
			System.out.println("[Typecheck] main");
			Node root = null;
			new MiniJavaParser(System.in);
			root = MiniJavaParser.Goal();
			System.out.println("[Typecheck] MiniJavaParser Finished");

			SymbolTable<Void, String> symbolTable = new SymbolTable<Void,String>();
			root.accept(symbolTable, "global");
			System.out.println("[Typecheck] symbolTable construction Finished");
			symbolTable.prettyPrint();

			TypeValidator<MyType, String> typeValidator = new TypeValidator<MyType, String>(symbolTable.getData());
			System.out.println("[Typecheck] symbolTable TypeValidator Finished");
			
		} catch (Exception e) {
			System.err.println(e);
		}
    }
}
