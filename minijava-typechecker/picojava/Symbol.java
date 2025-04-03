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
}