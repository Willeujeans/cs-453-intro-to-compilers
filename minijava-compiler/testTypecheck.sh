#!/bin/bash

# Compile Java files first
echo "Compiling Java sources..."

current_date_time="`date "+%Y-%m-%d %H:%M:%S"`";

javac Typecheck.java || echo "Compilation failed! Fix errors and try again."

echo ""
echo "Running SymbolTable tests"
echo ""

echo $current_date_time > tests/minijava-symboltable-tests/advanced-tests/test.txt
java_files=(tests/minijava-symboltable-tests/advanced-tests/*.java)
num_files=${#java_files[@]}
more_java_files=(tests/minijava-symboltable-tests/advanced-tests/*.java)
more_num_files=${#more_java_files[@]}
num_files+=#$more_num_files

bar=""
for ((i=0; i<num_files; i++)); do
    bar+="."
done

loading_string=""
for file in tests/minijava-symboltable-tests/advanced-tests/*.java; do
    clear
    echo $bar
    echo $loading_string
    loading_string+="|"

    echo "Typecheck < $file" >> tests/minijava-symboltable-tests/advanced-tests/test.txt
    java Typecheck < "$file" >> tests/minijava-symboltable-tests/advanced-tests/test.txt
    echo "" >> tests/minijava-symboltable-tests/advanced-tests/test.txt
done

echo $current_date_time > tests/minijava-symboltable-tests/simple-tests/test.txt
for file in tests/minijava-symboltable-tests/simple-tests/*.java; do
    clear
    echo $bar
    echo $loading_string
    loading_string+="|"

    echo "Typecheck < $file" >> tests/minijava-symboltable-tests/simple-tests/test.txt
    java Typecheck < "$file" >> tests/minijava-symboltable-tests/simple-tests/test.txt
    echo "" >> tests/minijava-symboltable-tests/simple-tests/test.txt
done

echo $bar