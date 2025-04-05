package picojava;
import syntaxtree.*;
import visitor.*;

import java.beans.Expression;
import java.util.HashMap;

public class TypeValidator<R, A> extends GJDepthFirst<MyType, String> {
    HashMap<String, Symbol> symbolTableData;

    public TypeValidator(HashMap<String, Symbol> symbolTableData){
        this.symbolTableData = new HashMap<String, Symbol>(symbolTableData);
    }

    // We can only pass down the scope
    // Which means, we need to return the type to check it
    // example situation:
    // (Mismatching assignment operator)
    // int x;
    // x = [0, 1]
    //
    // visit(AssignmentStatement, String key){
    //      MyType foundInMap = key + f0.name
    //      if foundInMap == n.f2
    //      return null;
    // }
    // 


    /**
     * f0 -> Identifier()
     * f1 -> "="
     * f2 -> Expression()
     * f3 -> ";"
     * public Void visit(AssignmentStatement n, Integer depth)
     */

    // I think you would grab the type by searching the identifier combined with the key
    // then you can pass the type down
    // Expression(type, key)
    // AndExpression(type, key)
    //

    /**
     * f0 -> AndExpression()
     * | CompareExpression()
     * | PlusExpression()
     * | MinusExpression()
     * | TimesExpression()
     * | ArrayLookup()
     * | ArrayLength()
     * | MessageSend()
     * | PrimaryExpression()
     * public Void visit(Expression n, Integer depth)
     */

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
     * public Void visit(PrimaryExpression n, Integer depth)
     */
}
