# MiniJava
MiniJava is a subset of Java, this means it can only to very restricted things that Java can do.
- Overloading is not allowed in MiniJava
``` Java
static int plusMethod(int x, int y) {
  return x + y;
}

static double plusMethod(double x, double y) {
  return x + y;
}
```
_Example of overloading in Java, when calling the method name the compiler will choose which option fits with the arguments._
- `System.out.println()` can only print integers
- .length will only apply to `int[]` (arrays of ints)

>"A MiniJava program will type check with the MiniJava type system, if and only if it will type check with the Java type system."

This means if the program fails in Java it should fail in miniJava. But if it fails in miniJava it does not necessarily fail in Java.

Meaning we follow the Java Syntax rules, but with a few more limitations.

## Identifiers
Identifiers are sequences of characters that uniquely identify entities within their scope.
**Example:**
`int x = 5;` identifier: `x`, type: `int`, value `5`

## MiniJava Syntax
This is everything, this is our key to quickly type check the programs that are given. Because we will be reading tokens with these names and like this, all we need to do is parse a derivation tree and look for invalid paths. Like lets say (Goal) has some tokens, we then check those tokens and we can see there is no `mc` in the (Goal) we can immediately throw an error.
This is the rigorous definitions that we can slowly step through to find where the tree is branching into places it shouldn't, and when it does we throw an error. Super simple. Understand the syntax and you can find the mistakes.

| Rule Number | Production Rule                                                                                                                 | Meaning                                                                                                                                                                                                     |
| ----------- | ------------------------------------------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| r1          | (Goal) g ::= mc d_1 ... d_n                                                                                                     | Defines the entire program (`Goal`).<br>A MiniJava program is a `mc` (MainClass).<br>Followed by optional class/method declarations (`d_1 ... d_n`).                                                        |
| r2          | (MainClass) mc ::= class id { public static void main (String [] id^S ){ t_1 id_1; . . . ; t_r id_r ; s_1 . . . s_q }}          | Defines the `mc` (MainClass), which must contain a main method with:<br>- A parameter String[] id^S (e.g., String[] args).<br>- Local variable declarations (t_1 id_1; ...).<br>- Statements (s_1 ... s_q). |
| r3          | (TypeDeclaration) d ::= class id { t_1 id_1; ...; t_f id_f ; m_1 ... m_k }                                                      | A basic class with fields (t_1 id_1; ...) and methods (m_1 ... m_k).                                                                                                                                        |
| r4          | \| class id extends id^P { t_1 id_1; ...; t_f id_f ; m_1 ... m_k }                                                              | A class that inherits from a parent class (`id^P`).                                                                                                                                                         |
| r5          | (MethodDeclaration) m ::= public t id^M (t^F_1 id^F_1 , ..., t^F_n id^F_n ) { t_1 id_1; ...; t_r id_r ; s_1 ... s_q return e; } | Defines a method with:  <br>- Return type<br>- Method name<br>- Parameters<br>- Local variables and statements  <br>- A return statement with expression `e`                                                |
| r6          | (Type) t ::= int[]                                                                                                              | An array of integers.                                                                                                                                                                                       |
| r7          | \| boolean                                                                                                                      | The boolean type (true/false).                                                                                                                                                                              |
| r8          | \| int                                                                                                                          | The int type.                                                                                                                                                                                               |
| r9          | \| id                                                                                                                           | A class type (e.g., String)                                                                                                                                                                                 |
| r10         | (Statement) s ::= { s_1 ... s_q }                                                                                               | A block of statements                                                                                                                                                                                       |
| r11         | \| id = e;                                                                                                                      | Assign a value to a variable                                                                                                                                                                                |
| r12         | \| id [ e_1 ] = e_2;                                                                                                            | Assign a value to an array index                                                                                                                                                                            |
| r13         | \| if ( e ) s_1 else s_2                                                                                                        | An if-else statement                                                                                                                                                                                        |
| r14         | \| while ( e ) s                                                                                                                | A while loop                                                                                                                                                                                                |
| r15         | \| System.out.println( e );                                                                                                     | Print an integer                                                                                                                                                                                            |
| r16         | (Expression) e ::= p_1 && p_2                                                                                                   | Logical AND                                                                                                                                                                                                 |
| r17         | \| p_1 < p_2                                                                                                                    | Less-than comparison                                                                                                                                                                                        |
| r18         | \| p_1 + p_2                                                                                                                    | Addition                                                                                                                                                                                                    |
| r19         | \| p_1 - p_2                                                                                                                    | Subtraction                                                                                                                                                                                                 |
| r20         | \| p_1 * p_2                                                                                                                    | Multiplication                                                                                                                                                                                              |
| r21         | \| p_1 [ p_2 ]                                                                                                                  | Array access                                                                                                                                                                                                |
| r22         | \| p .length                                                                                                                    | Array length                                                                                                                                                                                                |
| r23         | \| p .id (e_1, ..., e_n)                                                                                                        | Method call                                                                                                                                                                                                 |
| r24         | \| p                                                                                                                            | Primary expression                                                                                                                                                                                          |
| r25         | (PrimaryExpression) p ::= c                                                                                                     | Integer literal                                                                                                                                                                                             |
| r26         | \| true                                                                                                                         | Boolean true                                                                                                                                                                                                |
| r27         | \| false                                                                                                                        | Boolean false                                                                                                                                                                                               |
| r28         | \| id                                                                                                                           | Variable name                                                                                                                                                                                               |
| r29         | \| this                                                                                                                         | Reference to the current object                                                                                                                                                                             |
| r30         | \| new int[e]                                                                                                                   | Create an `int[]` array                                                                                                                                                                                     |
| r31         | \| new id()                                                                                                                     | Create an object                                                                                                                                                                                            |
| r32         | \| !e                                                                                                                           | Logical NOT                                                                                                                                                                                                 |
| r33         | \| (e)                                                                                                                          | Parenthesized expression                                                                                                                                                                                    |
| r34         | (IntegerLiteral ) c ::= 〈INTEGER LITERAL〉                                                                                       | Integer literals (`0`, `25`)                                                                                                                                                                                |
| r35         | (Identifier ) id ::= 〈IDENTIFIER〉                                                                                               | Valid identifiers (`x`, `Main`, `ComputeFac`)                                                                                                                                                               |

## Notation For Rules
A rule that says if we can derive all of the hypothesis then we can derive the conclusion.  
A rule with no hypotheses is called an axiom.  
Derivation: Starts with one or more axioms, apply some rules, then arrive at a conclusion.
$$
\dfrac
{hypothesis_1 \space \space hypothesis_2 \space \space ... \space \space hypothesis_3}
{conclusion}
$$

``` Java
class Main {
    public static void main(String[] a) {
        System.out.println(5);
    }
}
```

### Derivation Tree
Root: conclusion
Leaves: axioms
**Example:**
``` C
                                      (Goal) g (r1)
                                           |
                                      (MainClass) mc (r2)
                                           |
                     +---------------------+-----------------------+
                     |                     |                       |
          "Main" (id via r35)    "String[] a" (r2)        (Statement) s (r15)
                                                                   |
                                                                   |
                                                 +-----------------+
                                                 |
                                  (Expression) e (r24 → p via r25)
                                                 |
                                                 |
                                   .  (IntegerLiteral) c (r34)
                                                 |
                                               〈5〉
```

## Subtyping
**Mathematical Definition:**  
1.  
$t \leq t$
_t can be a subtype of t_

2.
$$
\dfrac
{t_1 \leq t_2 \space \space t_2 \leq t_3}
{t_1 \leq t_3}
$$
_Hypotheses: {$t_1$ is a subtype of $t_2$ , $t_2$ is a subtype of $t_3$}_  
_Conclusion: $t_1$ is a subtype of $t_3$_  

3. 
$$
\dfrac
{\text{class} \space C \space \text{extends} \space D \space ... \text{is in the program}}
{C \leq D}
$$

_In mini java we can subtype by using the key word 'extends' in a class description._

## Type Environments
A type environment is a finite dictionary that maps identifiers to their types.
All variable names within a Type Environment must be unique.
$A$: Denotes a type environment
$dom(A)$: Denotes domain of $A$

**Mathematical Definition:**
IF: $id_1, ... , id_r$ are pairwise distinct identifiers
_all variable names in the list must be unique_
THEN: $[id_1 : t_1,...,id_r : t_r]$ is a Type Environment that maps $id_i$ to $t_i$ , for $i \in 1..r$
_variable `id₁` has type `t₁`, variable `id₂` has type `t₂`,..._

**Example:**
``` Java
int x = 5;
boolean flag = true;
```
$A=[x: \text{int} , flag: \text{boolean} ]$
$dom(A)=$ {$x,flag$}

### Key Type Environment Layers
A variable should have its type determined by checking it in the current environment.
- Global: Class/field types (`class Fac { int x; ... }`).
- Method: Method parameters and local variables.
- Block: Variables in nested blocks (inside of `if`, `while`).
``` Java
class Main {
  int x;                      // Field (Global environment)
  public void foo(int x) {     // Parameter x (Method environment)
    boolean y = true;          // Local variable (Method environment)
    { 
      int z = 5;              // Local to block (Block environment)
    }
  }
}
```
Entering a method: (Global Type Environment) $\cdot$ (Method Type Environment)

### Shadowing
When a variable declared in an inner scope has the same identifier as a variable in an outer scope.
Within the inner scope, the inner variable "shadows" or overrides the outer variable, making the outer variable temporarily inaccessible.

### Combining Type Environments
Operation to combine two type environments $A_1, A_2$ = $A_1 \cdot A_2$
Smaller scope has higher precedence: $A_2$ will take precedence over $A_1$.

**Mathematical Definition:**
$$
(A_1 \cdot A_2)(id)=
\begin{cases}
A_2(id) & \text{if} \space id \in dom(A_2) \\
A_1(id) & \text{otherwise}
\end{cases}
$$

**Example:**
$A_1​=[x: \text{int}, y: \text{boolean}],$
$A_2​=[x: \text{String}, z: \text{int}]$
$A_1​ \cdot A_2​$:
1. `x` is present in both $A_1$​ and $A_2$ : Use $A_2$ type $x$ = String
2. `y` is only in $A_1$ : keep $y$ = boolean
3. `z` is only in $A_2$ : add $z$ = int
$A_1​ \cdot A_2​=[x: \text{String} , y: \text{boolean}, z: \text{int}]$

### Example: Type Checker Using Type Environments
```
class Main {
  int x;                      // Global: x : int
  public void foo(int x) {     // Method: x : int (shadows global x)
    boolean y = true;          // Method: y : boolean
    if (y) {
      int z = 5;              // Block: z : int
      x = z;                  // x refers to method parameter (int), z is int
    }
  }
}
```
1. Build the Global Environment $A_{global​} = [x: \text{int}]$
2. $A_{method​} = [x: \text{int} ,y: \text{boolean}]$
3. $A_{combined​} = A_{global​} \cdot A_{method​} = [x: \text{int},y: \text{boolean}]$
4. $A_{block}​=[z: \text{int}]$
5. $A_{current}​ = A_{combined​} \cdot A_{block} = [x: \text{int}, y: \text{boolean}, z: \text{int}]$
Checking if `x = z;` is valid:
- From method environment: `x`  is type`int`
- From block environment: `z` it type `int`
- Assignment `int x = int z` is valid

## Helper Functions
### classname
Returns the name of a class from any of the 3 ways to define a class.
**Mathematical Definition:**
	$classname($class $id$ { public static void main (String [] $id^S$) {...}}) $=id$
	_Class containing the main method._
	$classname($class $id$ { $t_1 \space id_1;...; \space t_f;m_1...m_k$}) $=id$
	_Class with fields & methods._
	$classname($class $id$ extends $id^P$ {$t_1 \space id_1; \space ...; \space t_f id_f; \space m_1 ... m_k$}) $=id$
	_Class inheriting from a parent class $id^P$ with fields & methods_

### methodname
Returns the name of a public method _(all methods are public in mini java)_

**Mathematical Definition:**
$methodname$( public $t \space id^M$ (...) ... ) $= id^M$
_'= $id^M$' is where the id = name of class M = method name is being returned_

### distinct
Checks that the identifiers in a list are pairwise distinct.
**Mathematical Definition:**
$$
\dfrac
{ \forall i \in 1..n: \forall j \in 1..n : id_i = id_j \Rightarrow i = j }
{ distinct(id_1,...,id_n) }
$$

$$
\dfrac
{\text{For every pair of identifiers in list: } (id_i, id_j)
\text{ If } (id_i = id_j)
\text{ Then: } i = j
}
{\text{conclusion: "The identifiers are distinct."}}
$$

The only way two identifiers in the list can be equal is if they are the same element (i.e., no duplicates exist).
If this condition holds for all pairs, the identifiers are distinct.

**Example:**
hypotheses: `x != y` and `x != z` and `y!= z`
conclusion: `distinct` returns `true`

### fields
Input: a class $C$
Output:
a type environment $A$ containing:
- all fields declared in $C$
- all fields from $C$'s superclasses, except those overridden by $C$
Fields in $C$ take precedence over fields with the same name in super-classes
**Mathematical Definition:**
$$
\dfrac
{ \text{class } id \set {t_1 \space id_1;...; t_f \space id_f; \space m_1 ... m_k}}
{ feilds(id) = [id_1 : t_1, ..., id_f : t_f] }
$$
_If $C$ is a class without extension then $fields(C)$ contains only the fields declared in $C$._

$$
\dfrac
{\text{class } id \text{ extends } id^P \space \set{ t_1 \space id_1; ...; t_f; m_1 ... m_k} \text{ is in the program}}
{fields(id)=fields(id^P) \cdot [id_1 : t_1, ..., id_f : t_f]}
$$
_If $C$ is a class with an extension: then $fields(C)$ combines $fields(P)$ ($P$ being the super-class) with $fields(C)$ ($fields(C)$ takes precedence and overrides any duplicates)._
### methodtype
Determines the type signature of a method in a class, accounting for inheritance.
Inputs:
	id: The class name where the method is being looked up.
	id^M: The method name to check.
Returns:
	Type signature of a method if it exists in the given class (or its ancestors), or `⊥` (error) if not found.  

**Mathematical Definition:**  
1.  
$\text{class } id \set{ ... m_1 ... m_k } \text{ is in the program}$
$\text{for some } j \in 1..k : methodname(m_j)=id^M$
$m_j \text{ is of the form}$
$$
\dfrac
{\text{ public } t \space id^M (t^F_1 , id^F_1 , ... , t^F_n \space id^F_n) \set{t_1 \space id_1 ; ... ; t_r \space id_r; s_1 ... s_q \text{ return } e;}}
{methodtype(id, id^M) = (id^F_1 : id^F_1, ..., id^F_n : t^F_n) \rightarrow t} 
$$
_Standalone or a Subclass contains the method._

2.  
$\text{class } id \set{... m_1 ... m_k} \text{ is in the program}$
$$
\dfrac
{\text{for all } j \in 1..k : methodname(m_j) \neq id^M}
{methodtype(id, id^M) = \bot}
$$
_Class does not extend another class and lacks the method._

3.  
$\text{class } id \text{ extends } id^P \set{...m_1...m_k} \text{ is in the program}$
$\text{for some } j \in 1..k : methodname(m_j) = id^M$
$m_j \text{ is of the form}$
$$
\dfrac
{\text{public } t \space id^M (t^F_1 \space id^F_1, ..., t^F_n \space id^F_n) \set{t_1 \space id_1; ...; t_r \space id_r; s_1 ... s_q \text{ return } e;}}
{methodtype(id, id^M) = (id^F_1 : t^F_1 , ...  , id^F_n, : t^F_n) \rightarrow t}
$$
_Classes that extend a parent and contains the method._

4.  
$\text{class } id \text{ extends } id^P \set{... m_1 ... m_k} \text{ is in the program}$
$$
\dfrac
{\text{for all } j \in 1..k : methodname(m_j) \neq id^M}
{methodtype(id, id^M) = methodtype(id^P, id^M)}
$$
_Class extends a parent class but does not define the method._

### noOverloading
Enforces no method overloading in MiniJava.  
Ensures that subclasses cannot redefine a method with the same name but a different signature than its parent class.

**Mathematical Definition:**
$$
\dfrac
{methodtype(id^P, id^M) \neq \bot \Rightarrow methodtype(id^P, id^M) = methodtype(id, id^M)}
{noOverloading(id, id^P, id^M)}
$$

Hypotheses: 
- if the parent class `id^P` has a method `id^M` (i.e., `methodtype(id^P, id^M) ≠ ⊥`),
- Then the method’s type signature in the parent **must match** the method’s signature in the subclass `id`.
Conclusion:
- If the premise holds, then `noOverloading` is satisfied (no overloading occurs).

## Type Rules
### Type Judgements
Seven forms of type judgements:
$\vdash \space g$
_Validates the entire program (all classes, methods, statements, etc.) as type-safe._
$\vdash \space mc$
_Ensures the main class (entry point of the program) is valid._
$\vdash \space d$
_Validates declarations (variables, classes, methods)._
$C \space \vdash \space m$
_Validates a method within its class context C._
$A,C \space \vdash \space s$
_Validates statements (e.g., assignments, conditionals, loops)._
$A,C \space \vdash \space e:t$
_Infers the type of expressions (e.g., arithmetic, method calls)._
$A,C \space \vdash \space p:t$
_Validates primary expressions (atomic expressions like variables, literals, this)._

### Goal
$distinct(classname(mc) , classname(d_1) , ... , classname(d_n))$
$\vdash mc$
$$
\dfrac
{\vdash d_i \space \space i \in 1..n}
{\vdash mc \space d_1 ... d_n}
$$

### Main Class

### Type Declarations

### Method Declarations

### Statements

### Expressions and Primary Expressions
