package picojava;
import syntaxtree.*;
import visitor.*;

import java.util.HashMap;

public class TypeValidator extends GJDepthFirst<MyType, String> {
    HashMap<String, Symbol> symbolTableData;
    private String bufferChar = ":";

    public TypeValidator(HashMap<String, Symbol> symbolTableData){
        this.symbolTableData = new HashMap<String, Symbol>(symbolTableData);
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
        System.out.println("Validating Goal...");
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
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
        System.out.println("Validating MainClass...");
        String currentScope = key + bufferChar + n.f1.f0.toString() + bufferChar + "main";
        
        // f15 -> ( Statement() )*
        n.f15.accept(this, currentScope);
        
        return null;
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
        System.out.println("Validating Statement...");
        n.f0.accept(this, key);
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
        System.out.println("Validating AssignmentStatement...");
        MyType identifierType = n.f0.accept(this, key);
        MyType expressionType = n.f2.accept(this, key);
        System.out.println("AssignmentStatement: " + "idType: " + identifierType + " ExpressionType: " + expressionType);
        if(!identifierType.checkIdentical(expressionType)){
            System.out.println("❌ Type Error");
            System.exit(1);
        }
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
        System.out.println("Validating Expression...");
        return n.f0.accept(this, key);
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "&&"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(AndExpression n, String key) {
        System.out.println("Validating AndExpression...");
        // Can only accept booleans
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "<"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(CompareExpression n, String key) {
        System.out.println("Validating CompareExpression...");
        // Can only accept ints
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        return null;
    }


    /**
     * f0 -> PrimaryExpression()
     * f1 -> "+"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(PlusExpression n, String key) {
        System.out.println("Validating PlusExpression...");
        // Can only accept ints
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "-"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(MinusExpression n, String key) {
        // Can only accept ints
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "*"
     * f2 -> PrimaryExpression()
     */
    @Override
    public MyType visit(TimesExpression n, String key) {
        // Can only accept ints
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        return null;
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "["
     * f2 -> PrimaryExpression()
     * f3 -> "]"
     */
    @Override
    public MyType visit(ArrayLookup n, String key) {
        // f0 needs to return an array type
        // f2 needs to return a int type
        MyType primaryExpressionZero = n.f0.accept(this, key);
        MyType primaryExpressionTwo = n.f2.accept(this, key);

        if(primaryExpressionZero.checkIdentical(new MyType("int", "[", "]"))){
            System.out.println("❌ Type Error");
            System.exit(1);
        }

        if(primaryExpressionTwo.checkIdentical(new MyType("int"))){
            System.out.println("❌ Type Error");
            System.exit(1);
        }
        return new MyType(primaryExpressionZero.getType());
    }

    /**
     * f0 -> PrimaryExpression()
     * f1 -> "."
     * f2 -> "length"
     */
    @Override
    public MyType visit(ArrayLength n, String key) {
        // Validate that PrimaryExpression is of type int[][]
        MyType primaryExpressionType = n.f0.accept(this, key);
        if(!primaryExpressionType.checkIdentical(new MyType("int", "[", "]"))){
            System.out.println("❌ Type Error");
            System.exit(1);
        }
        return new MyType("int");
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
        // Validate that PrimaryExpression is a class
        // Validate that ID exists in the class
        // Validate that the method with that ID uses the ExpressionList types in arguments
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        n.f2.accept(this, key);
        n.f3.accept(this, key);
        n.f4.accept(this, key);
        n.f5.accept(this, key);
        return null;
    }

    /**
     * f0 -> Expression()
     * f1 -> ( ExpressionRest() )*
     */
    @Override
    public MyType visit(ExpressionList n, String key) {
        // Unsure of what to do here, combine the expressions?
        n.f0.accept(this, key);
        n.f1.accept(this, key);
        return null;
    }

    /**
     * f0 -> ","
     * f1 -> Expression()
     */
    @Override
    public MyType visit(ExpressionRest n, String key) {
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
        MyType primaryExpressionType = n.f0.accept(this, key);
        System.out.println("PrimaryExpression returns: " + primaryExpressionType);
        return primaryExpressionType;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    @Override
    public MyType visit(IntegerLiteral n, String key) {
        return new MyType("int");
    }

    /**
     * f0 -> "true"
     */
    @Override
    public MyType visit(TrueLiteral n, String key) {
        return new MyType("boolean");
    }

    /**
     * f0 -> "false"
     */
    @Override
    public MyType visit(FalseLiteral n, String key) {
        return new MyType("boolean");
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    @Override
    public MyType visit(Identifier n, String key) {
        String searchKey = key + bufferChar + n.f0.toString();
        System.out.println("Getting type for id: " + searchKey);
        MyType identifierType = symbolTableData.get(searchKey).type;
        return identifierType;
    }

    /**
     * f0 -> "this"
     */
    @Override
    public MyType visit(ThisExpression n, String key) {
        // look up vars the class has to see if we are accessing one that exists
        // look up type of var in class
        n.f0.accept(this, key);
        return null;
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
        // Check if Expression() is type int?
        return new MyType("int", "[", "]");
    }

    /**
     * f0 -> "new"
     * f1 -> Identifier()
     * f2 -> "("
     * f3 -> ")"
     */
    @Override
    public MyType visit(AllocationExpression n, String key) {
        // Unsure how to handle yet
        MyType identifierType = n.f1.accept(this, key);
        return identifierType;
    }

    /**
     * f0 -> "!"
     * f1 -> Expression()
     */
    @Override
    public MyType visit(NotExpression n, String key) {
        MyType expressionType = n.f1.accept(this, key);
        if(!expressionType.checkIdentical(new MyType("boolean"))){
            System.out.println("❌ Type Error");
            System.exit(1);
        }
        return expressionType;
    }

    /**
     * f0 -> "("
     * f1 -> Expression()
     * f2 -> ")"
     */
    @Override
    public MyType visit(BracketExpression n, String key) {
        return n.f1.accept(this, key);
    }
}
