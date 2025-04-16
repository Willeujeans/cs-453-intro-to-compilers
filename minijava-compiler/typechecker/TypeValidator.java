package typechecker;

import syntaxtree.*;
import visitor.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;

public class TypeValidator extends GJDepthFirst<Symbol, String> {
    SymbolTable symbolTable;

    public TypeValidator(SymbolTable symbolTable){
        this.symbolTable = symbolTable;
    }

    public void checkForOverload(){
        HashMap<String, Symbol> methods = symbolTable.getMethods();
        HashMap<String, Symbol> declarations = symbolTable.declarations;
        if(!methods.isEmpty()){
            System.out.println("checking...");
            for(String methodKey : methods.keySet()){
                String[] keyFragments = methodKey.split(symbolTable.bufferChar);
                String[] keyFragmentsTrimmed = Arrays.copyOf(keyFragments, keyFragments.length - 2);
                String methodName = keyFragments[keyFragments.length - 1];
    
                int i = keyFragmentsTrimmed.length;
                String currentKey = keyFragmentsTrimmed + symbolTable.bufferChar + methodName;

                
                while(i > 1){
                    currentKey = String.join(symbolTable.bufferChar, Arrays.copyOf(keyFragmentsTrimmed, i)) + symbolTable.bufferChar + methodName;
                    if(methods.containsKey(currentKey)){
                        Symbol originalMethod = methods.get(methodKey);
                        Symbol methodToCheck = methods.get(currentKey);
                        
                        if(!originalMethod.isSameArgumentTypes(methodToCheck)){
                            System.out.println("Overload check return not the same: Type Error");
                            System.exit(1);
                        }

                        Symbol methodOneSymbol = declarations.get(methodKey);
                        Symbol methodTwoSymbol = declarations.get(currentKey);
                        
                        if(!methodOneSymbol.isSameType(methodTwoSymbol)){
                            System.out.println("Overload check: Type Error");
                            System.exit(1);
                        }
                    }
                    --i;
                }
            }
        }
        System.out.println("# checkForOverload");
    }

    // return the type to check it
    // example situation:
    // (Mismatching assignment operator)
    // int x;
    // x = false;

    // VISIT METHODS

    /**
     * f0 -> MainClass()
     * f1 -> ( TypeDeclaration() )*
     * f2 -> <EOF>
     */
    @Override
    public Symbol visit(Goal n, String key) {
        checkForOverload();
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        System.out.println("# " + n.getClass().getSimpleName());
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
    public Symbol visit(MainClass n, String key) {
        String currentScope = key + symbolTable.bufferChar + n.f1.f0.toString() + symbolTable.bufferChar + "main";
        n.f15.accept(this, currentScope);
        Symbol returnSymbol = new Symbol(new MyType(n.f1.f0.toString()));
        System.out.println("# " + n.getClass().getSimpleName());
        return returnSymbol;
    }

    /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    @Override
    public Symbol visit(TypeDeclaration n, String key){
        Symbol returnSymbol = n.f0.accept(this, key);
        System.out.println("# " + n.getClass().getSimpleName());
        return returnSymbol;
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
    public Symbol visit(ClassDeclaration n, String key){
        String classKey = n.f1.f0.toString();
        ClassSymbol classSymbol = symbolTable.findClass(classKey);
        String currentScope = classSymbol.getKeyWithInheritance();

        n.f4.accept(this, currentScope);

        System.out.println("# " + n.getClass().getSimpleName());
        return classSymbol;
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
    public Symbol visit(ClassExtendsDeclaration n, String key){
        String classKey = n.f1.f0.toString();
        ClassSymbol classSymbol = symbolTable.findClass(classKey);
        String currentScope = classSymbol.getKeyWithInheritance();

        n.f6.accept(this, currentScope);

        System.out.println("# " + n.getClass().getSimpleName());
        return classSymbol;
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
    public Symbol visit(MethodDeclaration n, String key) {
        String currentScope = key + symbolTable.bufferChar + n.f2.f0.toString();
        n.f8.accept(this, currentScope);
        Symbol expectedreturnSymbol = n.f1.accept(this, key);
        Symbol actualreturnSymbol = n.f10.accept(this, currentScope);
        String className = expectedreturnSymbol.getClassName();
        
        String otherClassName = actualreturnSymbol.getClassName();
        boolean firstClass = symbolTable.getClasses().containsKey(className);
        boolean secondClass = symbolTable.getClasses().containsKey(otherClassName);

        if(firstClass && secondClass){
            if(!expectedreturnSymbol.isRelated(actualreturnSymbol)){
                System.out.println("Method return type mismatch: Type Error");
                System.exit(1);
            }
        }else{
            if(!expectedreturnSymbol.isSameType(actualreturnSymbol)){
                System.out.println("Method return type mismatch: Type Error");
                System.exit(1);
            }
        }
        return expectedreturnSymbol;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    @Override
    public Symbol visit(AllocationExpression n, String key) {
        String classKey = n.f1.f0.toString();
        Symbol returnSymbol = symbolTable.findClass(classKey);
        return returnSymbol;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    @Override
    public Symbol visit(AssignmentStatement n, String key) {
        Symbol identifierSymbol = n.f0.accept(this, key);
        Symbol expressionSymbol = n.f2.accept(this, key);
        boolean isClass = symbolTable.classes.containsKey(identifierSymbol.getClassName());
        boolean isClassB = symbolTable.classes.containsKey(expressionSymbol.getClassName());
        boolean isBothClasses = (isClass && isClassB);
        if(isBothClasses){
            // Less strict check
            if(!identifierSymbol.isRelated(expressionSymbol)){
                System.out.println(n.getClass().getSimpleName() + ": Type Error");
                System.exit(1);
            }
        }else{
            // More strict check
            if(!identifierSymbol.isSameType(expressionSymbol)){
                System.out.println(n.getClass().getSimpleName() + ": Type Error");
                System.exit(1);
            }
        }

        return expressionSymbol;
    }

       /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
    @Override
    public Symbol visit(ArrayAssignmentStatement n, String key){
        Symbol arraySymbol = n.f0.accept(this, key);
        Symbol arrayIndexSymbol = n.f2.accept(this, key);
        Symbol assignmentSymbol = n.f5.accept(this, key);

        if(!arrayIndexSymbol.isSameType(new Symbol(new MyType("int")))){
            System.out.println("Type Error");
            System.out.println("!Array index must be an int");
            System.exit(1);
        }
        
        if(!arraySymbol.isSameBaseType(assignmentSymbol)){
            System.out.println("Type Error");
            System.out.println("!Incorrect type assignment to array");
            System.exit(1);
        }
        return arraySymbol;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    @Override
    public Symbol visit(ArrayLookup n, String key) {
        Symbol primaryExpressionZero = n.f0.accept(this, key);
        Symbol primaryExpressionTwo = n.f2.accept(this, key);

        if(!primaryExpressionZero.isSameType(new Symbol(new MyType("int", "[]")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error  primaryExpressionZero" + key);
            System.exit(1);
        }

        if(!primaryExpressionTwo.isSameType(new Symbol(new MyType("int")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error  primaryExpressionTwo");
            System.exit(1);
        }

        Symbol returnSymbol = new Symbol(primaryExpressionZero);
        return returnSymbol;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    @Override
    public Symbol visit(ArrayLength n, String key) {
        Symbol returnSymbol = n.f0.accept(this, key);
        
        if(!returnSymbol.isSameType(new Symbol(new MyType("int", "[]")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        System.out.println("# " + n.getClass().getSimpleName());
        return returnSymbol;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> Identifier()
     * f3 -> "("
     * f4 -> ( ExpressionList() )?
     * f5 -> ")"
     */
    @Override
    public Symbol visit(MessageSend n, String key) {
        Symbol classSymbol = n.f0.accept(this, key);
        String methodName = n.f2.f0.toString();

        String classkeyWithInheritance = symbolTable.findClass(classSymbol.getClassName()).declarationKey;

        String classMethodKey = classkeyWithInheritance + symbolTable.bufferChar + methodName;
        
        Symbol methodSymbol = symbolTable.findMethodWithShadowing(classMethodKey);
        Symbol methodReturnSymbol = symbolTable.findVariableWithShadowing(classMethodKey);

        Symbol passedArguments = n.f4.accept(this, key);
        // Because arguments are optional it might not return anything
        if(passedArguments == null){
            passedArguments = new Symbol(new MyType());
        }
        
        System.out.println(methodSymbol.getArguments());
        System.out.println("===}{===");
        System.out.println(passedArguments.getArguments());

        if(!methodSymbol.isSameArgumentTypes(passedArguments)){
            System.out.println(methodSymbol.getArguments() + " != " + passedArguments.getArguments());
            System.out.println("Type Error: Calling method with incorrect arguments");
            System.exit(1);
        }

        System.out.println("# " + n.getClass().getSimpleName());
        return methodReturnSymbol;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public Symbol visit(Identifier n, String key) {
        String searchKey = key + symbolTable.bufferChar + n.f0.toString();
        Symbol foundSymbol = symbolTable.findVariableWithShadowing(searchKey);
        if (foundSymbol == null) {
            System.out.println("Undeclared variable: " + n.f0.toString());
            System.exit(1);
        }
        return foundSymbol;
    }

    /**
     * f0 -> "this"
     */
    @Override
    public Symbol visit(ThisExpression n, String key) {
        // return the closest class we are inside of
        Symbol returnSymbol = symbolTable.getNearestClass(key);
        return returnSymbol;
    }

    @Override
    public Symbol visit(ExpressionList n, String key) {
        Symbol argumentSymbol = new Symbol(new MyType());
        argumentSymbol.addArgument(n.f0.accept(this, key));
        
        if (n.f1.present()) {
            for (Node node : n.f1.nodes) {
                Symbol otherArgumentSymbols = node.accept(this, key);
                argumentSymbol.addArgument(otherArgumentSymbols);
            }
        }
        return argumentSymbol;
    }

    /**
    * f0 -> ","
    * f1 -> Expression()
    */
    @Override
    public Symbol visit(ExpressionRest n, String key) {
        Symbol returnSymbol = n.f1.accept(this, key);
        return returnSymbol;
    }

    /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    @Override
    public Symbol visit(Type n, String key){
        Symbol returnSymbol = n.f0.accept(this, key);
        return returnSymbol;
    }

       /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    @Override
    public Symbol visit(ArrayType n, String key){
        Symbol returnSymbol = new Symbol(new MyType("int", "[]"));
        return returnSymbol;
    }

   /**
    * f0 -> "boolean"
    */
    @Override
    public Symbol visit(BooleanType n, String key){
        Symbol returnSymbol = new Symbol(new MyType("boolean"));
        return returnSymbol;
    }

   /**
    * f0 -> "int"
    */
    @Override
    public Symbol visit(IntegerType n, String key){
        Symbol returnSymbol = new Symbol(new Symbol(new MyType("int")));
        return returnSymbol;
    }
    /**
     * f0 -> Block()
     * | AssignmentStatement()
     * | ArrayAssignmentStatement()
     * | IfStatement()
     * | WhileStatement()
     * | PrintStatement()
     */
    @Override
    public Symbol visit(Statement n, String key) {
        Symbol returnSymbol = n.f0.accept(this, key);
        return returnSymbol;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    @Override
    public Symbol visit(AndExpression n, String key) {
        // Can only accept booleans
        Symbol typeA = n.f0.accept(this, key);
        Symbol typeB = n.f2.accept(this, key);
        if(!typeA.isSameType(new Symbol(new MyType("boolean")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        if(!typeB.isSameType(new Symbol(new MyType("boolean")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        Symbol returnSymbol = new Symbol(new MyType("boolean"));
        return returnSymbol;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    @Override
    public Symbol visit(CompareExpression n, String key) {
        Symbol typeA = n.f0.accept(this, key);
        Symbol typeB = n.f2.accept(this, key);

        if(!typeA.isSameType(new Symbol(new MyType("int")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        if(!typeB.isSameType(new Symbol(new MyType("int")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        Symbol returnSymbol = new Symbol(new MyType("boolean"));
        return returnSymbol;
    }


    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    @Override
    public Symbol visit(PlusExpression n, String key) {
        // Can only accept ints
        
        Symbol typeA = n.f0.accept(this, key);
        Symbol typeB = n.f2.accept(this, key);

        if(!typeA.isSameType(new Symbol(new MyType("int")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        if(!typeB.isSameType(new Symbol(new MyType("int")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        Symbol returnSymbol = new Symbol(new Symbol(new MyType("int")));
        return returnSymbol;
    }

    /** 
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    @Override
    public Symbol visit(MinusExpression n, String key) {
        // Can only accept ints
        Symbol typeA = n.f0.accept(this, key);
        Symbol typeB = n.f2.accept(this, key);

        if(!typeA.isSameType(new Symbol(new MyType("int")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        if(!typeB.isSameType(new Symbol(new MyType("int")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        Symbol returnSymbol = new Symbol(new MyType("int"));
        return returnSymbol;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    @Override
    public Symbol visit(TimesExpression n, String key) {
        // Can only accept ints
        Symbol typeA = n.f0.accept(this, key);
        Symbol typeB = n.f2.accept(this, key);

        if(!typeA.isSameType(new Symbol(new MyType("int")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        if(!typeB.isSameType(new Symbol(new MyType("int")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        Symbol returnSymbol = new Symbol(new MyType("int"));
        return returnSymbol;
    }

    /**
     * f0 -> IntegerLiteral()
     * | TrueLiteral()
     * | FalseLiteral()
     * | Identifier()
     * | ThisExpression()
     * | ArrayAllocationExpression()
     * | AllocationExpression()
     * | NotExpression()
     * | BracketExpression()
     */
    @Override
    public Symbol visit(PrimaryExpression n, String key) {
        Symbol returnSymbol = n.f0.accept(this, key);
        return returnSymbol;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    @Override
    public Symbol visit(IntegerLiteral n, String key) {
        Symbol returnSymbol = new Symbol(new MyType("int"));
        return returnSymbol;
    }

    /**
     * f0 -> "true"
     */
    @Override
    public Symbol visit(TrueLiteral n, String key) {
        Symbol returnSymbol = new Symbol(new MyType("boolean"));
        return returnSymbol;
    }

    /**
     * f0 -> "false"
     */
    @Override
    public Symbol visit(FalseLiteral n, String key) {
        Symbol returnSymbol = new Symbol(new MyType("boolean"));
        return returnSymbol;
    }

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     */
    @Override
    public Symbol visit(ArrayAllocationExpression n, String key) {
        Symbol expressionType = n.f3.accept(this, key);
        if(!expressionType.isSameType(new Symbol(new MyType("int")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        Symbol returnSymbol = new Symbol(new MyType("int", "[]"));
        return returnSymbol;
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    @Override
    public Symbol visit(NotExpression n, String key) {
        Symbol returnSymbol = n.f1.accept(this, key);
        
        if(!returnSymbol.isSameType(new Symbol(new MyType("boolean")))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        
        return returnSymbol;
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    @Override
    public Symbol visit(BracketExpression n, String key) {
        Symbol returnSymbol = n.f1.accept(this, key);
        return returnSymbol;
    }

    /**
    * f0 -> AndExpression()
    *       | CompareExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
    @Override
    public Symbol visit(Expression n, String key) {
        Symbol returnSymbol = n.f0.accept(this, key);
        return returnSymbol;
    }

    /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
    @Override
    public Symbol visit(PrintStatement n, String key){
        Symbol typeToPrint = n.f2.accept(this, key);
        if(!typeToPrint.isSameType(new Symbol(new MyType("int")))){
            System.out.println("Type Error: Cannot print types other than int");
            System.exit(1);
        }
        System.out.println("# " + n.getClass().getSimpleName());
        return typeToPrint;
    }
}
