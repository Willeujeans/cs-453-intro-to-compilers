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
			Node root = null;
			new MiniJavaParser(System.in);
			root = MiniJavaParser.Goal();

			SymbolTable<Void, String> symbolTable = new SymbolTable<Void,String>();
			root.accept(symbolTable, "global");
			symbolTable.prettyPrint();
		} catch (Exception e) {
			System.err.println(e);
		}
    }
}
