Compile:
``` Bash
javac -cp . *.java picojava/*.java
javac Typecheck.java
```

Run File
``` Bash
java Typecheck < P.java
```

Run both
``` Bash
javac Typecheck.java && java Typecheck < P.java
```

Run Test
``` Bash
javac -cp . *.java picojava/*.java && java TestTypecheck
```