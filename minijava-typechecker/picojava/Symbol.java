package picojava;

import java.util.ArrayList;

public class Symbol {
    public MyType type;
    public int lineDeclared = 0;
    public ArrayList<Integer> lineUsed = new ArrayList<Integer>();
    
    public Symbol(MyType type, int lineDeclared) {
        this.type = type;
        this.lineDeclared = lineDeclared;
    }

    public void addLineUsed(int lineNumber){
        if(!lineUsed.contains(lineNumber)){
            lineUsed.add(lineNumber);
        }
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