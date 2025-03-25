## Files to work on

MyType.java
Class that represents the types in MiniJava

PPrinter.java
A visitor to pretty-print the AST (for debugging and understanding the parser’s output).

SymTableVis.java
Top down visitor that builds the symbol table by traversing the AST
Collects declarations of classes, methods, variables, and their types.

TypeCheckSimp.java
A bottom-up visitor that performs type checking using the symbol table.
Verifies type consistency in expressions, assignments, method calls, etc.

Typecheck.java
 1. Reads a MiniJava program
 2. Parses it into an AST
 3. Runs visitors for symbol table construction
 4. Runs visitors for type checking
 5. outputs the result.

## Compiling
 javac -cp . syntaxtree/*.java visitor/*.java
 javac -cp . picojava/*.java
 javac -cp . Typecheck.java