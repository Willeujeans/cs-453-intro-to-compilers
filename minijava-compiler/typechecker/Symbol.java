package typechecker;

import java.util.ArrayList;

public class Symbol {
    public MyType type;
    public int lineDeclared = 0;
    public ArrayList<Integer> lineUsed = new ArrayList<Integer>();
    public ArrayList<Symbol> arguments = new ArrayList<Symbol>();
    
    public Symbol(){
        this.type = new MyType();
    }

    public Symbol(MyType type, int lineDeclared) {
        this.type = type;
        this.lineDeclared = lineDeclared;
    }

    public Symbol(MyType type) {
        this.type = type;
    }

    public Symbol(Symbol other){
        this.type = new MyType(other.type);
        this.lineDeclared = other.lineDeclared;
        this.lineUsed = new ArrayList<>(other.lineUsed);
    }

    public String getClassName(){
        return new String(type.getType());
    }

    public void addLineUsed(int lineNumber){
        if(!lineUsed.contains(lineNumber)){
            lineUsed.add(lineNumber);
        }
    }

    public boolean isSameType(Symbol other){
        if(other == null){
            return false;
        }
        return type.checkIdentical(other.type);
    }

    public boolean isSameBaseType(Symbol other){
        if(other == null){
            return false;
        }
        return type.getType() == other.getType();
    }

    public boolean isRelated(Symbol other){
        if(other == null){
            return false;
        }
        return type.checkSimilar(other.type);
    }

    public String getType(){
        return type.getType();
    }

    public void addArgument(Symbol other){
        arguments.add(other);
    }

    public Boolean isSameArgumentTypes(Symbol other){
        for(Symbol each : arguments){
            if(!each.isSameType(other)){
                return false;
            }
        }
        return true;
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append("{");
        output.append(type.toString()).append(", ");
        output.append(lineDeclared).append(", ");
        output.append(lineUsed);
        output.append("}");
        
        return output.toString();
    }
}