package typechecker;

import java.util.ArrayList;

public class Symbol {
    public boolean isClass = false;
    public String key = new String();
    public MyType type = new MyType();
    public int lineDeclared = 0;
    public ArrayList<Integer> lineUsed = new ArrayList<Integer>();
    private ArrayList<Symbol> arguments = new ArrayList<Symbol>();

    public Symbol(String newKey, MyType type, int lineDeclared) {
        if (newKey == null || type == null) {
            throw new IllegalArgumentException("Key and type cannot be null");
        }
        this.key = newKey;
        this.type = new MyType(type); // Defensive copy
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

    public void addArgument(Symbol other) {
        if (other == null)
            throw new IllegalArgumentException("Cannot add argument using null");
        arguments.add(other);
    }

    public boolean isSameArgumentTypes(Symbol other) {
        if (arguments.size() != other.arguments.size()) {
            return false;
        }
        for (int i = 0; i < arguments.size(); ++i) {
            Symbol argumentA = arguments.get(i);
            Symbol argumentB = other.arguments.get(i);
            if (argumentA.isClass && argumentB.isClass) {
                if (!argumentA.type.isRelated(argumentB.type)) {
                    return false;
                }
            } else {
                if (!argumentA.type.isSameType(argumentB.type)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isExactSameArgumentTypes(Symbol other) {
        if (arguments.size() != other.arguments.size()) {
            return false;
        }
        for (int i = 0; i < arguments.size(); ++i) {
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
        return "Symbol{key='" + key + '\'' +
                ", type=" + type +
                ", lineDeclared=" + lineDeclared +
                ", lineUsed=" + lineUsed +
                ", arguments=" + arguments +
                '}';
    }
}