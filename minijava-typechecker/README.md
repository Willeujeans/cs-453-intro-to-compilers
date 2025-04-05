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
javac -cp . picojava/*.java Typecheck.java && java Typecheck < P.java
```

Run Test
``` Bash
javac -cp . *.java picojava/*.java && java TestTypecheck
```

``` Bash
javac -cp . picojava/*.java Typecheck.java &&
for file in testSourceCode/*.java; do
    echo "Processing $file..."
    java Typecheck < "$file"
done
```