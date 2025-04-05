class invalidVarAssignment {
    public static void main(String[] a) {
        int x ;
        x = false;
        // AssignmentStatement() {pull type from Identifier}
        // Expression()
        // PrimaryExpression()
        // Literal, or identifier, ... {something else that will give a type}


        /**
         * f0 -> Identifier()
         * f1 -> "="
         * f2 -> Expression()
         * f3 -> ";"
         * public Void visit(AssignmentStatement n, Integer depth)
        */

        /**
        * f0 -> ...
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
}

