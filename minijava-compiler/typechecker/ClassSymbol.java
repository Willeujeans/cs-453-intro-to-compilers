package typechecker;

import java.util.*;
import syntaxtree.*;
import typechecker.MyType;
import visitor.*;

public class ClassSymbol extends Symbol{
    public String declarationKey;

    public ClassSymbol(String newKey, MyType type, int lineDeclared) {
        super(type, lineDeclared);
        declarationKey = newKey;
    }

    public MyType updateChildrenClasses(SymbolTable symbolTable, MyType argumentType){
        type = updateChildrenClasses(symbolTable, argumentType);
        return type;
    }

    public String getKeyWithInheritance(){
        ArrayList<String> output = new ArrayList<String>();
        output.add("global");
        for(String item : type.type_array){
            output.add(item);
        }
        String outputString = String.join(":", output);
        return outputString;
    }
}
