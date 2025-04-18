package typechecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import syntaxtree.*;
import typechecker.Symbol.Classification;
import visitor.*;

// Symbol Table Visitor: Traverses AST to create symbol table.
public class SymbolTable<R, A> extends GJDepthFirst<Void, String> {
    public HashMap<String, Symbol> declarations;
    public HashMap<String, Symbol> classes;
    public HashMap<String, Symbol> methods;
    public HashMap<String, Symbol> classInstances;

    public static final String BUFFER_CHAR = ":";
    public static final String METHOD_ARG_CHAR = "#";

    public SymbolTable() {
        declarations = new HashMap<String, Symbol>();
        classes = new HashMap<String, Symbol>();
        methods = new HashMap<String, Symbol>();
        classInstances = new HashMap<String, Symbol>();
    }

    private void validateParameters(String key, Symbol entry) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Key must not be null or empty");
        }
        if (entry == null) {
            throw new IllegalArgumentException("Entry must not be null");
        }
    }

    private void checkForDuplicate(String key, Map<String, Symbol> map) {
        if (map.containsKey(key)) {
            handleTypeError();
        }
    }

    public boolean insertDeclaration(String key, Symbol entry) {
        validateParameters(key, entry);
        checkForDuplicate(key, declarations);
        declarations.put(key, entry);
        return true;
    }

    public boolean insertClass(String key, Symbol entry) {
        validateParameters(key, entry);
        checkForDuplicate(key, classes);
        classes.put(key, entry);
        return true;
    }

    public boolean insertMethod(String key, Symbol methodSymbol) {
        validateParameters(key, methodSymbol);
        checkForDuplicate(key, methods);
        methods.put(key, methodSymbol);
        return true;
    }

    public boolean insertClassInstance(String classInstanceKey, Symbol entry) {
        validateParameters(classInstanceKey, entry);
        checkForDuplicate(classInstanceKey, classInstances);
        classInstances.put(classInstanceKey, entry);
        return true;
    }

    private void handleTypeError() {
        System.err.println("Type Error: Duplicate symbol declaration");
        System.exit(9);
    }

    public void postTraversalOperations() {
        updateClassInstances();
        updateClassKeysWithInheritance();
        updateMethodsReturnType();
        updateArgumentTypesWithInheritance();
    }

    public void updateMethodsReturnType() {
        for (String key : methods.keySet()) {
            if (declarations.containsKey(key)) {
                Symbol methodSymbol = methods.get(key);
                methodSymbol.type = declarations.get(key).type;
            }
        }
    }

    public void updateArgumentTypesWithInheritance() {
        for (String key : methods.keySet()) {
            if (declarations.containsKey(key)) {
                Symbol methodSymbol = methods.get(key);
                for (Symbol argument : methodSymbol.getArguments()) {
                    if (argument.classification == Symbol.Classification.CLASSINSTANCE) {
                        argument.type = classes.get(argument.type.getBaseType()).type;
                    }
                }
            }
        }
    }

    public static String removeAfter(String str, String c) {
        int index = str.indexOf(c);
        if (index == -1) {
            return str;
        }
        return str.substring(0, index);
    }

    public void updateClassInstances(String... className) {
        if (classes.isEmpty() || classInstances.isEmpty() || className == null) {
            return;
        }

        for (String instanceKey : classInstances.keySet()) {
            MyType instanceType = classInstances.get(instanceKey).type;

            MyType classType = findClass(instanceType.getBaseType()).type;

            if (declarations.containsKey(instanceKey)) {
                declarations.get(instanceKey).type = classType;
            } else {
                prettyPrint();
                System.out.println("Type Error: Tried to get a declaration that does not exist");
                System.exit(9);
            }
        }
    }

    public HashMap<String, Symbol> getClasses() {
        return classes;
    }

    public HashMap<String, Symbol> getMethods() {
        return methods;
    }

    public Symbol findMethodWithShadowing(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Attempt to call method with null arguments");
        }
        String[] keyFragments = key.split(BUFFER_CHAR);
        String idToFind = keyFragments[keyFragments.length - 1];

        // Search through first scope
        String currentKey = "";
        for (int i = keyFragments.length - 1; i >= 1; i--) {
            currentKey = String.join(BUFFER_CHAR, Arrays.copyOf(keyFragments, i)) + BUFFER_CHAR + idToFind;
            if (methods.containsKey(currentKey)) {
                return methods.get(currentKey);
            }
        }
        System.out.println("Type Error: Could not find method");
        System.exit(9);
        return null;
    }

    public Symbol findVariableWithShadowing(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Type Error: Attempt to call method with null arguments");
        }
        String[] keyFragments = key.split(BUFFER_CHAR);
        String idToFind = keyFragments[keyFragments.length - 1];

        // Search through first scope
        String currentKey = "";
        for (int i = keyFragments.length - 1; i >= 1; i--) {
            currentKey = String.join(BUFFER_CHAR, Arrays.copyOf(keyFragments, i)) + BUFFER_CHAR + idToFind;
            if (declarations.containsKey(currentKey)) {
                return declarations.get(currentKey);
            }
        }
        System.out.println("Type Error: Could not find variable with shadowing");
        System.exit(9);
        return null;
    }

    public Symbol findClass(String classId) {
        if (classId == null || classId.isEmpty()) {
            throw new IllegalArgumentException("Attempt to find class with illegal arguments");
        }
        if (classes.containsKey(classId)) {
            return classes.get(classId);
        }
        throw new RuntimeException("Attempted to find a Class that does not exist");
    }

    public Symbol findClassInstance(String classInstanceId) {
        if (classInstanceId == null || classInstanceId.isEmpty()) {
            throw new IllegalArgumentException("Attempt to call method with null arguments");
        }
        if (!classInstances.containsKey(classInstanceId)) {
            throw new RuntimeException("Attempted to find a ClassInstance that does not exist");
        }
        return classInstances.get(classInstanceId);
    }

    public void updateClasses(String parentClassId, String childClassId) {
        for (Symbol each : classes.values()) {
            if (each.type.typeArray.contains(childClassId)) {
                each.type.typeArray.insertElementAt(parentClassId, 0);
            }
        }
    }

    public void updateClassKeysWithInheritance() {
        // Create a copy of declaration keys to avoid concurrent modification
        List<String> originalDeclarationKeys = new ArrayList<>(declarations.keySet());

        for (String classKey : classes.keySet()) {
            for (String declarationKey : originalDeclarationKeys) {
                List<String> typeArray = classes.get(classKey).type.typeArray;
                String classKeyWithInheritance = String.join(BUFFER_CHAR, typeArray);

                String[] splitString = declarationKey.split(BUFFER_CHAR);
                List<String> newDeclarationKey = new ArrayList<String>();
                for (String each : splitString) {
                    if (each.equals(classKey)) {
                        newDeclarationKey.add(classKeyWithInheritance);
                    } else {
                        newDeclarationKey.add(each);
                    }
                }
                String newDeclarationKeyJoined = String.join(BUFFER_CHAR, newDeclarationKey);

                if (!newDeclarationKeyJoined.equals(declarationKey)) {
                    Symbol symbolToStore = declarations.get(declarationKey);
                    declarations.remove(declarationKey);
                    declarations.put(newDeclarationKeyJoined, new Symbol(symbolToStore));

                    // update method list
                    if (methods.containsKey(declarationKey)) {
                        Symbol methodSymbolToStore = methods.get(declarationKey);
                        methods.remove(declarationKey);
                        Symbol updatedMethodSymbol = new Symbol(methodSymbolToStore);
                        Symbol methodSymbol = declarations.get(newDeclarationKeyJoined);
                        updatedMethodSymbol.type = methodSymbol.type;
                        methods.put(newDeclarationKeyJoined, updatedMethodSymbol);
                    }
                }
            }
        }
    }

    public Symbol getNearestClass(String key) {
        String[] keyFragments = key.split(BUFFER_CHAR);

        for (int i = keyFragments.length; i >= 1; i--) {
            String[] currentFragments = Arrays.copyOf(keyFragments, i);
            String currentKey = String.join(BUFFER_CHAR, currentFragments);
            if (declarations.containsKey(currentKey)) {
                Symbol symbol = declarations.get(currentKey);
                String lastPart = currentFragments[currentFragments.length - 1];
                if (symbol.type.getBaseType().equals(lastPart)) {
                    return symbol;
                }
            }
        }
        System.err.println("Type Error: Could not find the nearest class");
        System.exit(9);
        return null;
    }

    public void prettyPrint() {
        System.out.println("| | | | | | | Declaration Table | | | | | | |");
        ArrayList<String> keys = new ArrayList<String>(declarations.keySet());
        Collections.sort(keys, Comparator.comparingInt(String::length));

        for (String key : keys) {
            System.out.print(key);
            System.out.print(" -> " + declarations.get(key) + "\n");
        }
        System.out.println("| | | | | | | | | | | | | | | | | | | | | | |");

        System.out.println("= = = = = = = = = Class Table = = = = = = = = =");
        for (String key : classes.keySet()) {
            System.out
                    .print(key + " -> " + classes.get(key) + " :: " + classes.get(key).getKeyWithInheritance() + "\n");
        }
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = =");

        System.out.println("- - - - - - - - - Method Table - - - - - - - - -");
        for (String key : methods.keySet()) {
            System.out.print(key + "()=" + methods.get(key).getArguments() + " -> " + methods.get(key).type + "\n");
        }
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - -");

        System.out.println(". . . . . . . . . . . . ClassInstances Table . . . . . . . . . . . .");
        for (String key : classInstances.keySet()) {
            System.out.print(key + "()=" + classInstances.get(key).type + "\n");
        }
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
    }

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    @Override
    public Void visit(Goal n, String key) {
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        System.out.println("# " + n.getClass().getSimpleName());
        postTraversalOperations();
        return null;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> "public"
     * f4 -> "static"
     * f5 -> "void"
     * f6 -> "main"
     * f7 -> "("
     * f8 -> "String"
     * f9 -> "["
     * f10 -> "]"
     * f11 -> Identifier()
     * f12 -> ")"
     * f13 -> "{"
     * f14 -> ( VarDeclaration() )*
     * f15 -> ( Statement() )*
     * f16 -> "}"
     * f17 -> "}"
     */
    @Override
    public Void visit(MainClass n, String key) {
        // MainClass addition
        String classKey = key + BUFFER_CHAR + n.f1.f0.toString();
        String className = n.f1.f0.toString();
        Symbol classSymbol = new Symbol(Symbol.Classification.CLASS, new MyType(n.f1.f0.toString()), n.f0.beginLine);
        insertClass(className, classSymbol);
        insertDeclaration(classKey, classSymbol);

        // Argument in mainClass addition
        String currentScope = key + BUFFER_CHAR + n.f1.f0.toString() + BUFFER_CHAR + "main";

        Symbol mainMethodSymbol = new Symbol(Symbol.Classification.METHOD, new MyType(), n.f5.beginLine);
        insertDeclaration(currentScope, mainMethodSymbol);

        Symbol argumentSymbol = new Symbol(Symbol.Classification.VARIABLE, new MyType("String", "[]"), n.f8.beginLine);
        insertDeclaration(currentScope + BUFFER_CHAR + n.f11.f0.toString(), argumentSymbol);

        n.f14.accept(this, currentScope);
        n.f15.accept(this, currentScope);
        System.out.println("# " + n.getClass().getSimpleName());
        return null;
    }

    /**
     * f0 -> ClassDeclaration()
     * | ClassExtendsDeclaration()
     */
    @Override
    public Void visit(TypeDeclaration n, String key) {
        n.f0.accept(this, key);
        return null;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> ( VarDeclaration() )*
     * f4 -> ( MethodDeclaration() )*
     * f5 -> "}"
     */
    @Override
    public Void visit(ClassDeclaration n, String key) {
        String currentScope = key + BUFFER_CHAR + n.f1.f0.toString();
        String className = n.f1.f0.toString();
        Symbol classSymbol = new Symbol(Classification.CLASS, new MyType(n.f1.f0.toString()), n.f0.beginLine);
        insertClass(className, classSymbol);
        insertDeclaration(currentScope,
                new Symbol(Classification.CLASS, new MyType(n.f1.f0.toString()), n.f0.beginLine));

        n.f3.accept(this, currentScope);
        n.f4.accept(this, currentScope);

        System.out.println("# " + n.getClass().getSimpleName());
        return null;
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "extends"
     * f3 -> Identifier()
     * f4 -> "{"
     * f5 -> ( VarDeclaration() )*
     * f6 -> ( MethodDeclaration() )*
     * f7 -> "}"
     */
    @Override
    public Void visit(ClassExtendsDeclaration n, String key) {
        String parentClassId = n.f3.f0.toString();
        String childClassId = n.f1.f0.toString();

        updateClasses(parentClassId, childClassId);

        Symbol classSymbol = new Symbol(Classification.CLASS, new MyType(parentClassId, childClassId),
                n.f0.beginLine);
        classSymbol.parentClassId = parentClassId;
        insertClass(childClassId, classSymbol);

        String currentScope = key + BUFFER_CHAR + childClassId;
        insertDeclaration(currentScope, new Symbol(new MyType(parentClassId, childClassId), n.f0.beginLine));

        n.f5.accept(this, currentScope);
        n.f6.accept(this, currentScope);

        System.out.println("# " + n.getClass().getSimpleName());
        return null;
    }

    /**
     * f0 -> "public"
     * f1 -> Type()
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( FormalParameterList() )?
     * f5 -> ")"
     * f6 -> "{"
     * f7 -> ( VarDeclaration() )*
     * f8 -> ( Statement() )*
     * f9 -> "return"
     * f10 -> Expression()
     * f11 -> ";"
     * f12 -> "}"
     */
    @Override
    public Void visit(MethodDeclaration n, String key) {
        String currentScope = key + BUFFER_CHAR + n.f2.f0.toString();
        // Symbol: place it using the current scope
        Symbol methodSymbol = new Symbol(Classification.METHOD, new MyType(), 0);
        insertMethod(currentScope, methodSymbol);
        n.f1.accept(this, currentScope);

        n.f4.accept(this, currentScope);

        n.f7.accept(this, currentScope);
        n.f8.accept(this, currentScope);
        n.f10.accept(this, currentScope);

        System.out.println("# " + n.getClass().getSimpleName());
        return null;
    }

    /**
     * f0 -> FormalParameter()
     * f1 -> ( FormalParameterRest() )*
     */
    @Override
    public Void visit(FormalParameterList n, String key) {
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        return null;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     */
    @Override
    public Void visit(FormalParameter n, String key) {
        n.f0.accept(this, key + METHOD_ARG_CHAR + n.f1.f0.toString());
        return null;
    }

    /**
     * f0 -> ","
     * f1 -> FormalParameter()
     */
    @Override
    public Void visit(FormalParameterRest n, String key) {
        n.f1.accept(this, key);
        return null;
    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    @Override
    public Void visit(VarDeclaration n, String key) {
        n.f0.accept(this, key + BUFFER_CHAR + n.f1.f0.toString());
        return null;
    }

    /**
     * f0 -> ArrayType()
     * | BooleanType()
     * | IntegerType()
     * | Identifier()
     */
    @Override
    public Void visit(Type n, String key) {
        n.f0.accept(this, key);
        return null;
    }

    /**
     * f0 -> "int"
     * f1 -> "["
     * f2 -> "]"
     */
    @Override
    public Void visit(ArrayType n, String key) {
        Symbol arraySymbol = new Symbol(Classification.VARIABLE, new MyType("int", "[]"), n.f0.beginLine);
        if (key.contains(METHOD_ARG_CHAR)) {
            String methodKey = new String(key);
            methodKey = removeAfter(methodKey, METHOD_ARG_CHAR);
            getMethods().get(methodKey)
                    .addArgument(arraySymbol);
            key = key.replace(METHOD_ARG_CHAR, BUFFER_CHAR);
        }

        insertDeclaration(key, arraySymbol);
        return null;
    }

    /**
     * f0 -> "boolean"
     */
    @Override
    public Void visit(BooleanType n, String key) {
        Symbol booleanSymbol = new Symbol(Classification.VARIABLE, new MyType("boolean"), n.f0.beginLine);
        if (key.contains(METHOD_ARG_CHAR)) {
            String methodKey = new String(key);
            methodKey = removeAfter(methodKey, METHOD_ARG_CHAR);
            getMethods().get(methodKey).addArgument(booleanSymbol);
            key = key.replace(METHOD_ARG_CHAR, BUFFER_CHAR);
        }
        insertDeclaration(key, booleanSymbol);
        return null;
    }

    /**
     * f0 -> "int"
     */
    @Override
    public Void visit(IntegerType n, String key) {
        Symbol intSymbol = new Symbol(Classification.VARIABLE, new MyType("int"), n.f0.beginLine);
        if (key.contains(METHOD_ARG_CHAR)) {
            String methodKey = new String(key);
            methodKey = removeAfter(methodKey, METHOD_ARG_CHAR);
            getMethods().get(methodKey).addArgument(intSymbol);
            key = key.replace(METHOD_ARG_CHAR, BUFFER_CHAR);
        }
        insertDeclaration(key, intSymbol);
        return null;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public Void visit(Identifier n, String key) {
        Symbol classInstanceSymbol = new Symbol(Classification.CLASSINSTANCE, new MyType(n.f0.toString()),
                n.f0.beginLine);
        if (key.contains(METHOD_ARG_CHAR)) {
            String methodKey = new String(key);
            methodKey = removeAfter(methodKey, METHOD_ARG_CHAR);
            String idName = n.f0.toString();
            getMethods().get(methodKey).addArgument(classInstanceSymbol);

            key = key.replace(METHOD_ARG_CHAR, BUFFER_CHAR);
        }
        insertClassInstance(key, classInstanceSymbol);
        insertDeclaration(key, classInstanceSymbol);
        return null;
    }

    @Override
    public Void visit(Block n, String key) {
        return null;
    }

    @Override
    public Void visit(AssignmentStatement n, String key) {
        return null;
    }

    @Override
    public Void visit(ArrayAssignmentStatement n, String key) {
        return null;
    }

    @Override
    public Void visit(IfStatement n, String key) {
        return null;
    }

    @Override
    public Void visit(WhileStatement n, String key) {
        return null;
    }

    @Override
    public Void visit(PrintStatement n, String key) {
        return null;
    }

    @Override
    public Void visit(Expression n, String key) {
        return null;
    }

    @Override
    public Void visit(AndExpression n, String key) {
        return null;
    }

    @Override
    public Void visit(CompareExpression n, String key) {
        return null;
    }

    @Override
    public Void visit(PlusExpression n, String key) {
        return null;
    }

    @Override
    public Void visit(MinusExpression n, String key) {
        return null;
    }

    @Override
    public Void visit(TimesExpression n, String key) {
        return null;
    }

    @Override
    public Void visit(ArrayLookup n, String key) {
        return null;
    }

    @Override
    public Void visit(ArrayLength n, String key) {
        return null;
    }

    @Override
    public Void visit(MessageSend n, String key) {
        return null;
    }

    @Override
    public Void visit(ExpressionList n, String key) {
        return null;
    }

    @Override
    public Void visit(ExpressionRest n, String key) {
        return null;
    }

    @Override
    public Void visit(PrimaryExpression n, String key) {
        return null;
    }

    @Override
    public Void visit(IntegerLiteral n, String key) {
        return null;
    }

    @Override
    public Void visit(TrueLiteral n, String key) {
        return null;
    }

    @Override
    public Void visit(FalseLiteral n, String key) {
        return null;
    }

    @Override
    public Void visit(ThisExpression n, String key) {
        return null;
    }

    @Override
    public Void visit(ArrayAllocationExpression n, String key) {
        return null;
    }

    @Override
    public Void visit(AllocationExpression n, String key) {
        return null;
    }

    @Override
    public Void visit(NotExpression n, String key) {
        return null;
    }

    @Override
    public Void visit(BracketExpression n, String key) {
        return null;
    }

    @Override
    public Void visit(Statement n, String key) {
        return null;
    }
}