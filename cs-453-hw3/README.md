# Homework 3: MiniJava → Vapor
Use JTB and JavaCC and write in Java one or more visitors that compile a MiniJava program to Vapor.

Your main file should be called J2V.java, and if P.java contains a syntactically correct MiniJava program, then

`java J2V < P.java > P.vapor`

creates a Vapor program P.vapor with the same behavior as P.java.


## Vapor Interpreter
This interpreter handles both Vapor and Vapor-M programs. Download: vapor.jar

To run on Vapor programs:

`java -jar vapor.jar run <input-file>`
To run on Vapor-M programs, pass in the -mips option:

`java -jar vapor.jar run -mips <input-file>`

## Files to Edit
`J2V.java`

`P.java`