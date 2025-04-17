package typechecker;

import java.util.ArrayList;

public class Symbol {
    public String key = new String();
    public MyType type = new MyType();
    public int lineDeclared = 0;
    public ArrayList<Integer> lineUsed = new ArrayList<Integer>();
    private ArrayList<Symbol> arguments = new ArrayList<Symbol>();

    public Symbol(String newKey, MyType type, int lineDeclared) {
        this.key = newKey;
        this.type = type;
        this.lineDeclared = lineDeclared;
    }

    public Symbol(MyType type, int lineDeclared) {
        this.type = type;
        this.lineDeclared = lineDeclared;
    }

    public Symbol(MyType type) {
        this.type = type;
    }

    public MyType updateChildrenClasses(SymbolTable symbolTable, MyType argumentType) {
        type = updateChildrenClasses(symbolTable, argumentType);
        return type;
    }

    public Symbol(Symbol other) {
        this.type = new MyType(other.type);
        this.arguments = new ArrayList<Symbol>(other.arguments);
        this.lineDeclared = other.lineDeclared;
        this.lineUsed = new ArrayList<>(other.lineUsed);
    }

    public ArrayList<Symbol> getArguments() {
        if (arguments == null)
            throw new IllegalArgumentException("Trying to get something that is null");
        return arguments;
    }

    public String getClassName() {
        return new String(type.getBaseType());
    }

    public void addLineUsed(int lineNumber) {
        if (!lineUsed.contains(lineNumber)) {
            lineUsed.add(lineNumber);
        }
    }

    public boolean isSameBaseType(Symbol other) {
        if (other == null)
            return false;
        return type.getBaseType() == other.type.getBaseType();
    }

    public boolean isRelated(Symbol other) {
        if (other == null) {
            return false;
        }
        return type.isSimilarType(other.type);
    }

    public void addArgument(Symbol other) {
        if (other == null)
            throw new IllegalArgumentException("Cannot add argument using null");
        arguments.add(other);
    }

    public boolean isSameArgumentTypes(Symbol other) {
        if (arguments.size() != other.arguments.size()) {
            return false;
        }
        for (int i = 0; i < 0; ++i) {
            if (!arguments.get(i).type.isSameType(other.arguments.get(i).type)) {
                return false;
            }
        }
        return true;
    }

    public String getKeyWithInheritance() {
        ArrayList<String> output = new ArrayList<String>();
        output.add("global");
        for (String item : type.typeArray) {
            output.add(item);
        }
        String outputString = String.join(":", output);
        return outputString;
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("{");
        output.append(type.toString()).append(", ");
        output.append(arguments.toString());
        output.append("}");

        return output.toString();
    }
}