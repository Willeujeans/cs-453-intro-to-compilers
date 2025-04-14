package typechecker;

import java.util.*;
import syntaxtree.*;
import typechecker.MyType;
import visitor.*;

public class MethodSymbol extends Symbol{
    public List<MyType> argumentList;

    public MethodSymbol(MyType type, int lineDeclared) {
        super(type, lineDeclared);
        this.argumentList = new ArrayList();
    }

    public MethodSymbol(MethodSymbol methodSymbol){
        super(methodSymbol);
        this.argumentList = new ArrayList<MyType>(methodSymbol.argumentList);
    }

    public void addArgumentType(MyType argumentType){
        if(argumentType == null){
            throw new IllegalArgumentException("Can't add an argument type that does not exist");
        }
        argumentList.add(argumentType);
    }

    public List<MyType> getArgumentList(){
        if(argumentList.isEmpty()){
            return null;
        }else{
            return argumentList;
        }
    }

}
