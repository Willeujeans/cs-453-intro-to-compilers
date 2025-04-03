package picojava;

public class Symbol {
    public MyType type;
    public int size = 0;
    public int dimension = 0;
    public int lineDeclared = 0;
    public int lineUsed = 0;
    public int address = 0;
    
    public Symbol(MyType type, int size, int dimension, int lineDeclared, int lineUsed, int address) {
        this.type = type;
        this.size = size;
        this.dimension = dimension;
        this.lineDeclared = lineDeclared;
        this.lineUsed = lineUsed;
        this.address = address;
    }

    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append("ₘₜ{");
        output.append(type.toString()).append(", ");
        output.append(size).append(", ");
        output.append(dimension).append(", ");
        output.append(lineDeclared).append(", ");
        output.append(lineUsed).append(", ");
        output.append(address);
        output.append("}");
        
        return output.toString();
    }
}