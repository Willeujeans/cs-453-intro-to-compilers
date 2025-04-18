#!/bin/bash

# Text colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NOCOLOR='\033[0m'

# Directory setup
CONVERSIONS_DIR="tests/minijava-to-vapor-tests/conversion-input"
OUTPUT_DIR="tests/minijava-to-vapor-tests/conversion-output"
CORRECT_DIR="tests/minijava-to-vapor-tests/correct-conversions"

# Create output directory if it doesn't exist
mkdir -p "$OUTPUT_DIR"

# Compiling
find . -name "*.class" -delete
if ! javac -classpath vapor-parser.jar:. J2V.java; then
    echo -e "${RED}!Compilation failed!${NOCOLOR}"
    exit 1
fi

echo -e "${GREEN}Compilation successful.${NOCOLOR}"

# Variables for tracking results
total_tests=0
passed_comparison=0
comparison_failures=()

# Process tests
echo -e "${CYAN}_Processing tests_${NOCOLOR}"
for java_file in "$CONVERSIONS_DIR"/*.java; do
    ((total_tests++))
    filename=$(basename "$java_file" .java)
    vapor_file="$OUTPUT_DIR/$filename.vapor"
    correct_file="$CORRECT_DIR/$filename.vapor"
    
    # Convert Java to Vapor

    java -classpath vapor-parser.jar:. J2V < "$java_file" > "$vapor_file"
    exit_code=$?
    echo -n "[$filename.java]>$exit_code converted. "
    
    if [ $exit_code -eq 0 ]; then
        # Compare with correct version if it exists
        if [ -f "$correct_file" ]; then
            echo -n "== comparing $filename.vapor -> "
            # Use diff to compare files, ignoring whitespace differences
            if diff -q -w -B "$vapor_file" "$correct_file" >/dev/null; then
                echo -e "${GREEN}[ MATCH ]${NOCOLOR}"
                ((passed_comparison++))
            else
                echo -e "${RED}[ DIFF ]${NOCOLOR}"
                comparison_failures+=("$filename.vapor")
            fi
        else
            echo -e "${YELLOW}   No correct version found for comparison${NOCOLOR}"
        fi
    else
        echo -e "${RED}CONVERSION FAILED (Exit code: $exit_code)${NOCOLOR}"
    fi
done

# Summary
echo -e "\n${CYAN}_Summary_${NOCOLOR}"
echo -e "Comparison matches: ${GREEN}$passed_comparison/${total_tests}${NOCOLOR}"

if [ ${#comparison_failures[@]} -gt 0 ]; then
    echo -e "\n${YELLOW}Comparison failures:${NOCOLOR}"
    for file in "${comparison_failures[@]}"; do
        echo -e "  ${YELLOW}➔${NOCOLOR} $file"
        # Show first difference as example
        diff -u -w -B "$OUTPUT_DIR/$file" "$CORRECT_DIR/$file" | head -n 10
    done
fi

echo -e "\nGenerated vapor files are in: ${CYAN}$OUTPUT_DIR${NOCOLOR}"
echo -e "Correct vapor files are in: ${CYAN}$CORRECT_DIR${NOCOLOR}"