#!/bin/bash

# Compile Java files first
echo "Compiling Java sources..."
javac -cp . picojava/*.java Typecheck.java || {
    echo "Compilation failed! Fix errors and try again."
    exit 1
}

# Check if directory exists
if [ ! -d "testSourceCode" ]; then
    echo "Error: Directory 'testSourceCode' not found!"
    exit 1
fi

# Process all .java files in testSourceCode
for file in testSourceCode/*.java; do
    echo "Type checking: $file"
    java Typecheck < "$file"
    echo "--------------------------------"
done