Generate Files
``` Bash
java -jar jtb132.jar minijava.jj
```

Generate Files
``` Bash
java -cp javacc-6.0/bin/lib/javacc.jar javacc jtb.out.jj
```

Put all filenames in txt file
``` Bash
find src -name "*.java" > sources.txt
javac -d build -cp "generated" @sources.txt
```

Run File
``` Bash
java -cp build Typecheck < P.java
```