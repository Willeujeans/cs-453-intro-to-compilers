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
}
