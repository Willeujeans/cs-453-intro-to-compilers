package typechecker;

import syntaxtree.*;
import visitor.*;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;

public class TypeValidator extends GJDepthFirst<MyType, String> {
    SymbolTable symbolTable;

    @Override
    public MyType visit(NodeListOptional n, String key) {
      if (n.present()){
        MyType stackedType = new MyType();
        int _count = 0;

        for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            stackedType.addToType(e.nextElement().accept(this, key));
            _count++;
         }
         return stackedType;
        }else{
            return new MyType("void");
        }
    }

    public TypeValidator(SymbolTable symbolTable){
        this.symbolTable = symbolTable;
    }

    public void checkForOverload(){
        HashMap<String, MethodSymbol> methods = symbolTable.getMethods();
        if(!methods.isEmpty()){
            for(String methodKey : methods.keySet()){
                String[] keyFragments = methodKey.split(symbolTable.bufferChar);
                String[] keyFragmentsTrimmed = Arrays.copyOf(keyFragments, keyFragments.length - 2);
                String methodName = keyFragments[keyFragments.length - 1];
    
                int i = keyFragmentsTrimmed.length;
                String currentKey = keyFragmentsTrimmed + symbolTable.bufferChar + methodName;
                
                while(i > 2){
                    currentKey = String.join(symbolTable.bufferChar, Arrays.copyOf(keyFragmentsTrimmed, i)) + symbolTable.bufferChar + methodName;
                    if(symbolTable.getMethods().containsKey(currentKey)){
                        System.out.println("Overload check: Type Error");
                        System.exit(1);
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
    public MyType visit(Goal n, String key) {
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
    public MyType visit(MainClass n, String key) {
        String currentScope = key + symbolTable.bufferChar + n.f1.f0.toString() + symbolTable.bufferChar + "main";
        
        // f15 -> ( Statement() )*
        n.f15.accept(this, currentScope);
        
        MyType returnType = new MyType(n.f1.f0.toString());

        System.out.println("# " + n.getClass().getSimpleName());
        return returnType;
    }

    /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    @Override
    public MyType visit(TypeDeclaration n, String key){
        MyType returnType = n.f0.accept(this, key);
        System.out.println("# " + n.getClass().getSimpleName());
        return returnType;
    }

    /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
    public MyType visit(ClassDeclaration n, String key){
        String classKey = n.f1.f0.toString();
        ClassSymbol classSymbol = symbolTable.findClass(classKey);
        String currentScope = classSymbol.getKeyWithInheritance();

        n.f4.accept(this, currentScope);

        System.out.println("# " + n.getClass().getSimpleName());
        return classSymbol.type;
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
    public MyType visit(ClassExtendsDeclaration n, String key){
        String classKey = n.f1.f0.toString();
        ClassSymbol classSymbol = symbolTable.findClass(classKey);
        String currentScope = classSymbol.getKeyWithInheritance();

        n.f6.accept(this, currentScope);

        System.out.println("# " + n.getClass().getSimpleName());
        return classSymbol.type;
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
    public MyType visit(MethodDeclaration n, String key) {
        String currentScope = key + symbolTable.bufferChar + n.f2.f0.toString();
        n.f8.accept(this, currentScope);
        MyType expectedReturnType = n.f1.accept(this, key);
        MyType actualReturnType = n.f10.accept(this, currentScope);
        String className = expectedReturnType.getType();
        
        String otherClassName = actualReturnType.getType();
        boolean firstClass = symbolTable.getClasses().containsKey(className);
        boolean secondClass = symbolTable.getClasses().containsKey(otherClassName);

        if(firstClass && secondClass){
            if(!expectedReturnType.checkSimilar(actualReturnType)){
                System.out.println("Method return type mismatch: Type Error");
                System.exit(1);
            }
        }else{
            if(!expectedReturnType.checkIdentical(actualReturnType)){
                System.out.println("Method return type mismatch: Type Error");
                System.exit(1);
            }
        }
        System.out.println("# " + n.getClass().getSimpleName());
        return expectedReturnType;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    @Override
    public MyType visit(AllocationExpression n, String key) {
        String classKey = n.f1.f0.toString();
        MyType returnType = symbolTable.findClass(classKey).type;
        return returnType;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    @Override
    public MyType visit(AssignmentStatement n, String key) {
        MyType idType = n.f0.accept(this, key);
        MyType expressionType = n.f2.accept(this, key);

        boolean isBothClasses = (symbolTable.classes.containsKey(idType.getType()) && symbolTable.classes.containsKey(expressionType.getType()));

        if(isBothClasses){
            // Less strict check
            if(!idType.checkSimilar(expressionType)){
                System.out.println(n.getClass().getSimpleName() + ": Type Error");
                System.exit(1);
            }
        }else{
            // More strict check
            if(!idType.checkIdentical(expressionType)){
                System.out.println(n.getClass().getSimpleName() + ": Type Error");
                System.exit(1);
            }
        }
        return expressionType;
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
    public MyType visit(ArrayAssignmentStatement n, String key){
        MyType arrayType = n.f0.accept(this, key);
        MyType arrayIndexType = n.f2.accept(this, key);
        MyType typeToAssignTo = n.f5.accept(this, key);

        if(!arrayIndexType.checkIdentical(new MyType("int"))){
            System.out.println("Type Error");
            System.out.println("!Array index must be an int");
            System.exit(1);
        }
        if(arrayType.getType() != typeToAssignTo.getType()){
            System.out.println("Type Error");
            System.out.println("!Incorrect type assignment to array");
            System.exit(1);
        }
        return arrayType;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    @Override
    public MyType visit(ArrayLookup n, String key) {
        MyType primaryExpressionZero = n.f0.accept(this, key);
        MyType primaryExpressionTwo = n.f2.accept(this, key);

        if(!primaryExpressionZero.checkIdentical(new MyType("int", "[]"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error  primaryExpressionZero" + key);
            System.exit(1);
        }

        if(!primaryExpressionTwo.checkIdentical(new MyType("int"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error  primaryExpressionTwo");
            System.exit(1);
        }

        MyType returnType = new MyType(primaryExpressionZero.getType());
        return returnType;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    @Override
    public MyType visit(ArrayLength n, String key) {
        MyType returnType = n.f0.accept(this, key);
        
        if(!returnType.checkIdentical(new MyType("int", "[", "]"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        System.out.println("# " + n.getClass().getSimpleName());
        return returnType;
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
    public MyType visit(MessageSend n, String key) {
        MyType classType = n.f0.accept(this, key);
        String className = classType.getType();
        String methodName = n.f2.f0.toString();

        // now we need to get the class key with inheritance
        String classkeyWithInheritance = symbolTable.findClass(className).declarationKey;
        String classMethodKey = classkeyWithInheritance + symbolTable.bufferChar + methodName;
        MethodSymbol methodSymbol = symbolTable.findMethodWithShadowing(classMethodKey);
        Symbol methodVarDeclaration = symbolTable.findVariableWithShadowing(classMethodKey);
        // we expect a MyType() with many type names
        // eg. method(x, y, z) -> MyType("x", "y", "z")
        MyType passedArguments = n.f4.accept(this, key);
        if(passedArguments == null){
            passedArguments = new MyType("void");
        }
        if(!methodSymbol.getArgumentTypes().checkIdentical(passedArguments)){
            System.out.println(methodSymbol.argumentTypes + " != " + passedArguments);
            System.out.println("Type Error: Calling method with incorrect arguments");
            System.exit(1);
        }

        System.out.println(methodSymbol);
        MyType returnType = new MyType(methodVarDeclaration.type);
        System.out.println("# " + n.getClass().getSimpleName());
        return returnType;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public MyType visit(Identifier n, String key) {
        String searchKey = key + symbolTable.bufferChar + n.f0.toString();
        Symbol foundSymbol = symbolTable.findVariableWithShadowing(searchKey);
        if (foundSymbol == null) {
            System.out.println("Undeclared variable: " + n.f0.toString());
            System.exit(1);
        }
        return foundSymbol.type;
    }

    /**
     * f0 -> "this"
     */
    @Override
    public MyType visit(ThisExpression n, String key) {
        // return the closest class we are inside of
        Symbol mySymbol = symbolTable.getNearestClass(key);
        MyType returnType = mySymbol.type;

        System.out.println("# " + n.getClass().getSimpleName());
        return returnType;
    }

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    @Override
    public MyType visit(ExpressionList n, String key) {
        MyType returnType = n.f0.accept(this, key);
        returnType.addToType(n.f1.accept(this, key));
        return returnType;
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    @Override
    public MyType visit(ExpressionRest n, String key) {
        MyType returnType = n.f1.accept(this, key);
        return returnType;
    }

    /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
    public MyType visit(Type n, String key){
        MyType returnType = n.f0.accept(this, key);
        return returnType;
    }

       /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
    public MyType visit(ArrayType n, String key){
        MyType returnType = new MyType("int", "[]");
        return returnType;
    }

   /**
    * f0 -> "boolean"
    */
    public MyType visit(BooleanType n, String key){
        MyType returnType = new MyType("boolean");
        return returnType;
    }

   /**
    * f0 -> "int"
    */
    public MyType visit(IntegerType n, String key){
        MyType returnType = new MyType("int");
        return returnType;
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
    public MyType visit(Statement n, String key) {
        MyType returnType = n.f0.accept(this, key);
        System.out.println("# " + n.getClass().getSimpleName());
        return returnType;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(AndExpression n, String key) {
        // Can only accept booleans
        MyType typeA = n.f0.accept(this, key);
        MyType typeB = n.f2.accept(this, key);
        if(!typeA.checkIdentical(new MyType("boolean"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        if(!typeB.checkIdentical(new MyType("boolean"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        MyType returnType = new MyType("boolean");
        return returnType;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(CompareExpression n, String key) {
        MyType typeA = n.f0.accept(this, key);
        MyType typeB = n.f2.accept(this, key);

        if(!typeA.checkIdentical(new MyType("int"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        if(!typeB.checkIdentical(new MyType("int"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        MyType returnType = new MyType("boolean");
        return returnType;
    }


    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(PlusExpression n, String key) {
        // Can only accept ints
        
        MyType typeA = n.f0.accept(this, key);
        MyType typeB = n.f2.accept(this, key);

        if(!typeA.checkIdentical(new MyType("int"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        if(!typeB.checkIdentical(new MyType("int"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        MyType returnType = new MyType("int");
        return returnType;
    }

    /** 
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(MinusExpression n, String key) {
        // Can only accept ints
        MyType typeA = n.f0.accept(this, key);
        MyType typeB = n.f2.accept(this, key);

        if(!typeA.checkIdentical(new MyType("int"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        if(!typeB.checkIdentical(new MyType("int"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        MyType returnType = new MyType("int");
        return returnType;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(TimesExpression n, String key) {
        // Can only accept ints
        MyType typeA = n.f0.accept(this, key);
        MyType typeB = n.f2.accept(this, key);

        if(!typeA.checkIdentical(new MyType("int"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        if(!typeB.checkIdentical(new MyType("int"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        MyType returnType = new MyType("int");
        return returnType;
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
    public MyType visit(PrimaryExpression n, String key) {
        MyType returnType = n.f0.accept(this, key);
        return returnType;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    @Override
    public MyType visit(IntegerLiteral n, String key) {
        MyType returnType = new MyType("int");
        return returnType;
    }

    /**
     * f0 -> "true"
     */
    @Override
    public MyType visit(TrueLiteral n, String key) {
        MyType returnType = new MyType("boolean");
        return returnType;
    }

    /**
     * f0 -> "false"
     */
    @Override
    public MyType visit(FalseLiteral n, String key) {
        MyType returnType = new MyType("boolean");
        return returnType;
    }

    /**
     * f0 -> "new"
     * f1 -> "int"
     * f2 -> "["
     * f3 -> Expression()
     * f4 -> "]"
     */
    @Override
    public MyType visit(ArrayAllocationExpression n, String key) {
        MyType expressionType = n.f3.accept(this, key);
        if(!expressionType.checkIdentical(new MyType("int"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        MyType returnType = new MyType("int", "[]");
        return returnType;
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    @Override
    public MyType visit(NotExpression n, String key) {
        MyType returnType = n.f1.accept(this, key);
        
        if(!returnType.checkIdentical(new MyType("boolean"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        
        return returnType;
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    @Override
    public MyType visit(BracketExpression n, String key) {
        MyType returnType = n.f1.accept(this, key);
        return returnType;
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
    public MyType visit(Expression n, String key) {
        MyType returnType = n.f0.accept(this, key);
        return returnType;
    }

    @Override
    public MyType visit(VarDeclaration n, String key) {
        return null;
    }
}
