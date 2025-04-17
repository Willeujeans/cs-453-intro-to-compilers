#!/bin/bash

# Text colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NOCOLOR='\033[0m'

# Compiling
find . -name "*.class" -delete
if ! javac Typecheck.java; then
    echo -e "${RED}!Compilation failed!${NOCOLOR}"
    exit 1
fi

correct_run=0 correct_passed=0
incorrect_run=0 incorrect_failed=0
correct_failures=() incorrect_passed=()

# Correct-tests
echo -e "${CYAN}_ Processing CORRECT tests _${NOCOLOR}"
for file in tests/minijava-symboltable-tests/correct-tests/*.java; do
    ((correct_run++))
    filename=$(basename "$file")
    echo -n "[Correct Test] $filename... "
    
    java Typecheck < "$file" &>/dev/null
    exit_code=$?
    
    if [ $exit_code -eq 0 ]; then
        echo -e "${GREEN}SUCCESS (Exit code: 0)${NOCOLOR}"
        ((correct_passed++))
    else
        echo -e "${RED}FAILURE (Exit code: $exit_code)${NOCOLOR}"
        correct_failures+=("$filename")
    fi
done

# Incorrect-tests
echo -e "\n${CYAN}_ Processing INCORRECT tests _${NOCOLOR}"
for file in tests/minijava-symboltable-tests/incorrect-tests/*.java; do
    ((incorrect_run++))
    filename=$(basename "$file")
    echo -n "[Incorrect Test] $filename... "
    
    java Typecheck < "$file" &>/dev/null
    exit_code=$?
    
    if [ $exit_code -eq 9 ]; then
        echo -e "${GREEN}SUCCESS (Exit code: 9)${NOCOLOR}"
        ((incorrect_failed++))
    else
        echo -e "${RED}FAILURE (Exit code: $exit_code)${NOCOLOR}"
        incorrect_passed+=("$filename")
    fi
done

# Conclusion
echo -e "\n${CYAN}_ Summary _${NOCOLOR}"
result="Correct: ${GREEN}${correct_passed}/${correct_run}${NOCOLOR} passed, Incorrect: ${GREEN}${incorrect_failed}/${incorrect_run}${NOCOLOR} failed"
problems=("${correct_failures[@]}" "${incorrect_passed[@]}")

if [ ${#problems[@]} -gt 0 ]; then
    echo -e "${YELLOW}Files needing review:${NOCOLOR}"
    for file in "${problems[@]}"; do
        echo -e "  ${YELLOW}➔${NOCOLOR} $file"
    done
fi