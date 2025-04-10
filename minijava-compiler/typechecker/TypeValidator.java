package typechecker;

import syntaxtree.*;
import visitor.*;

import java.util.Random;
import java.util.HashMap;

public class TypeValidator extends GJDepthFirst<MyType, String> {
    SymbolTable symbolTableData;
    private String bufferChar = ":";

    public TypeValidator(SymbolTable symbolTableData){
        this.symbolTableData = symbolTableData;
    }

    public static int randomNumber(){
        int min = 100;
        int max = 999;

        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;
        return randomNumber;
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
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        System.out.println(uuid + "▓ " + n.getClass().getSimpleName());
        
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
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        String currentScope = key + bufferChar + n.f1.f0.toString() + bufferChar + "main";
        
        // f15 -> ( Statement() )*
        n.f15.accept(this, currentScope);
        
        MyType returnType = new MyType(n.f1.f0.toString());
        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + " ----------------- ------------>  " + returnType);

        return returnType;
    }

    /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
    @Override
    public MyType visit(TypeDeclaration n, String key){
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        MyType returnType = n.f0.accept(this, key);
        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + " ----------------- ------------>  " + returnType);
        
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
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        
        MyType returnType = n.f1.accept(this, key);
        String currentScope = key + bufferChar + returnType.getType();
        
        // n.f3.accept(this, currentScope);
        n.f4.accept(this, currentScope);

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + " ----------------- ------------>  " + returnType);

        return returnType;
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
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        MyType returnType = n.f1.accept(this, key);
        String currentScope = key + bufferChar + returnType.getType();

        // n.f5.accept(this, currentScope);
        n.f6.accept(this, currentScope);
        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + " ----------------- ------------>  " + returnType);
        
        return returnType;
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

        // verify that actual return type matches method's return type
        String currentScope = key + bufferChar + n.f2.f0.toString();

        // unsure yet
        // n.f4.accept(this, currentScope);

        n.f8.accept(this, currentScope);

        // compare with type of method in symbol table
        n.f10.accept(this, currentScope);

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
     * f0 -> Block()
     * | AssignmentStatement()
     * | ArrayAssignmentStatement()
     * | IfStatement()
     * | WhileStatement()
     * | PrintStatement()
     */
    @Override
    public MyType visit(Statement n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        n.f0.accept(this, key);
        System.out.println(uuid + "▓ " + n.getClass().getSimpleName());
        
        return null;
    }

    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     */
    @Override
    public MyType visit(AssignmentStatement n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        System.out.println("AS name: " + key);
        MyType identifierType = n.f0.accept(this, key);
        MyType expressionType = n.f2.accept(this, key);

        System.out.println(identifierType + " == " + expressionType);

        if(!identifierType.checkIdentical(expressionType)){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        System.out.println(uuid + "▓ " + n.getClass().getSimpleName());
        
        return null;
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
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());

        MyType returnType = n.f0.accept(this, key);

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        
        return returnType;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(AndExpression n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        
        // Can only accept booleans
        
        MyType typeA = n.f0.accept(this, key);
        MyType typeB = n.f2.accept(this, key);

        MyType returnType = new MyType("boolean");

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        
        return returnType;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(CompareExpression n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        
        // Can only accept ints
        
        MyType typeA = n.f0.accept(this, key);
        MyType typeB = n.f2.accept(this, key);

        MyType returnType = new MyType("boolean");

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }


    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(PlusExpression n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        
        // Can only accept ints
        
        MyType typeA = n.f0.accept(this, key);
        MyType typeB = n.f2.accept(this, key);

        MyType returnType = typeA;
        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        
        return returnType;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(MinusExpression n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        
        // Can only accept ints
        
        MyType typeA = n.f0.accept(this, key);
        MyType typeB = n.f2.accept(this, key);

        MyType returnType = typeA;

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(TimesExpression n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        
        // Can only accept ints
        
        MyType typeA = n.f0.accept(this, key);
        MyType typeB = n.f2.accept(this, key);

        MyType returnType = typeA;

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    @Override
    public MyType visit(ArrayLookup n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        // f0 needs to return an array type
        // f2 needs to return a int type
        MyType primaryExpressionZero = n.f0.accept(this, key);
        MyType primaryExpressionTwo = n.f2.accept(this, key);

        if(primaryExpressionZero.checkIdentical(new MyType("int", "[", "]"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        if(primaryExpressionTwo.checkIdentical(new MyType("int"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }
        MyType returnType = new MyType(primaryExpressionZero.getType());

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    @Override
    public MyType visit(ArrayLength n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        
        MyType returnType = n.f0.accept(this, key);
        
        if(!returnType.checkIdentical(new MyType("int", "[", "]"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
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
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        // Validate that PrimaryExpression is a class
        // Validate that ID exists in the class
        // Validate that the method with that ID uses the ExpressionList types in arguments
        MyType className = n.f0.accept(this, key);
        String classKey = "global" + bufferChar + className.getType();
        MyType returnType = n.f2.accept(this, classKey);

        n.f4.accept(this, key);
        
        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    @Override
    public MyType visit(ExpressionList n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        // Unsure of what to do here, combine the expressions?
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        
        System.out.println(uuid + "▓ " + n.getClass().getSimpleName());
        return null;
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    @Override
    public MyType visit(ExpressionRest n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        System.out.println(uuid + "▓ " + n.getClass().getSimpleName());
        return n.f1.accept(this, key);
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
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        
        MyType returnType = n.f0.accept(this, key);
        
        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    @Override
    public MyType visit(IntegerLiteral n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());

        MyType returnType = new MyType("int");

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    /**
     * f0 -> "true"
     */
    @Override
    public MyType visit(TrueLiteral n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());

        MyType returnType = new MyType("boolean");

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    /**
     * f0 -> "false"
     */
    @Override
    public MyType visit(FalseLiteral n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());

        MyType returnType = new MyType("boolean");

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public MyType visit(Identifier n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());
        String searchKey = key + bufferChar + n.f0.toString();
        System.out.println("  >Searchkey: [" + searchKey + "]");

        MyType returnType = symbolTableData.find(searchKey).type;

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    /**
     * f0 -> "this"
     */
    @Override
    public MyType visit(ThisExpression n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());

        // return the closest class we are inside of
        Symbol mySymbol = symbolTableData.getNearestClass(key);
        MyType returnType = mySymbol.type;

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
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
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());

        if(new MyType("int").checkIdentical(n.f3.accept(this, key)) == false){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        MyType returnType = new MyType("int", "[", "]");

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    @Override
    public MyType visit(AllocationExpression n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());

        // if the identifier is a class type we globally search
        // Problem: when searching for `global:MyVisitor` we actually want to search for `global:Visitor:MyVisitor`
        Symbol mySymbol = symbolTableData.find(key);
        MyType returnType = n.f1.accept(this, "global");

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    @Override
    public MyType visit(NotExpression n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());

        MyType returnType = n.f1.accept(this, key);
        
        if(!returnType.checkIdentical(new MyType("boolean"))){
            System.out.println(n.getClass().getSimpleName() + ": Type Error");
            System.exit(1);
        }

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    @Override
    public MyType visit(BracketExpression n, String key) {
        //debug
        int uuid = randomNumber();
        System.out.println(uuid + "░ " + n.getClass().getSimpleName());

        MyType returnType = n.f1.accept(this, key);

        System.out.println(uuid + "▓ " + n.getClass().getSimpleName() + "  ------------>  " + returnType);
        return returnType;
    }

    @Override
    public MyType visit(VarDeclaration n, String key) {
        return null;
    }
}
