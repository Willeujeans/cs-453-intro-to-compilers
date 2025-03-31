package picojava;

public class Symbol {
    public MyType type;
    public int size;
    public int dimension;
    public int lineDeclared;
    public int lineUsed;
    public int address;

    public Symbol(MyType type, int size, int dimension, int lineDeclared, int lineUsed, int address){
        this.type = type;
        this.size = size;
        this.dimension = dimension;
        this.lineDeclared = lineDeclared;
        this.lineUsed = lineUsed;
        this.address = address;
    }
}
