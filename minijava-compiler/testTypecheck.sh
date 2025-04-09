#!/bin/bash

# Compile Java files first
echo "Compiling Java sources..."
javac -cp . typechecker/*.java Typecheck.java || {
    echo "Compilation failed! Fix errors and try again."
    exit 1
}

# Check if directory exists
if [ ! -d "tests/minijava-symboltable-tests" ]; then
    echo "Error: Directory 'minijava-symboltable-tests' not found!"
    exit 1
fi

echo ""
echo "Running Type check Tests"
echo ""
# Process all .java files in testSourceCode
for file in tests/minijava-symboltable-tests/*.java; do
    echo "Typecheck < $file"
    java Typecheck < "$file"
    echo ""
    echo "======================================================================================="
done