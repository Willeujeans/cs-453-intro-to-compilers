package typechecker;

import java.util.*;
import syntaxtree.*;
import typechecker.MyType;
import visitor.*;

public class MethodSymbol extends Symbol{
    public MyType argumentTypes;

    public MethodSymbol(MyType type, int lineDeclared) {
        super(type, lineDeclared);
        this.argumentTypes = new MyType();
    }

    public MethodSymbol(MethodSymbol methodSymbol){
        super(methodSymbol);
        argumentTypes = new MyType();
        argumentTypes.addToType(methodSymbol.getArgumentTypes());
    }

    public void addArgumentType(MyType argumentType){
        if(argumentType == null){
            throw new IllegalArgumentException("Can't add an argument type that does not exist");
        }
        argumentTypes.addToType(argumentType);
    }

    public MyType getArgumentTypes(){
        if(argumentTypes.type_array.isEmpty()){
            return null;
        }else{
            return argumentTypes;
        }
    }

}
