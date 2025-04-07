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

echo ""
echo "Running Type check Tests"
echo ""

# Process all .java files in testSourceCode
for file in testSourceCode/*.java; do
    echo "Typecheck < $file"
    java Typecheck < "$file"
    echo ""
done