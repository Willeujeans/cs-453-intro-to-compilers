#!/bin/bash

# Configure run parameters
MAX_FILES_PER_DIR=""  # Empty means no limit
MAX_TOTAL_FILES=""    # Empty means no limit
CURRENT_FILE_COUNT=0  # Global counter
TOTAL_FAILURES=0

# Function to run tests in a directory
run_tests() {
    local test_dir="$1"
    echo -e "\nRunning tests in ${test_dir}"

    # Create timestamp and clear previous results
    local current_date_time="$(date "+%Y-%m-%d %H:%M:%S")"

    # Process each Java file
    local dir_file_count=0
    for file in "${test_dir}"/*.java; do
        # Check global total limit
        if [[ -n "$MAX_TOTAL_FILES" && "$CURRENT_FILE_COUNT" -ge "$MAX_TOTAL_FILES" ]]; then
            echo "Reached global file limit ($MAX_TOTAL_FILES), stopping..."
            return
        fi

        # Check per-directory limit
        if [[ -n "$MAX_FILES_PER_DIR" && "$dir_file_count" -ge "$MAX_FILES_PER_DIR" ]]; then
            echo "Reached per-directory limit ($MAX_FILES_PER_DIR) for ${test_dir}, moving to next directory..."
            return
        fi

        ((CURRENT_FILE_COUNT++))
        ((dir_file_count++))
        
        echo -e "\nProcessing ${file} (Global: ${CURRENT_FILE_COUNT}"
        echo "Directory: ${dir_file_count}/$(ls -1 "${test_dir}"/*.java | wc -l))"
        
        # Run type checker and capture output with line numbers for errors
        {
            echo "=== Typecheck < ${file} ==="
            java Typecheck < "${file}" 2>&1 | awk '{print "  " $0}'
            exit_code=${PIPESTATUS[0]}
            
            if [ ${exit_code} == 1 ]; then
                ((TOTAL_FAILURES++))
                echo -e "\nERROR: Type checking failed for ${file} (exit code: ${exit_code})"
            fi
        }
        
        echo "------------------------------------"
    done
}

# Main script execution
{
    # Handle command line arguments
    if [[ "$#" -gt 0 ]]; then
        if [[ "$1" == "--per-dir" ]]; then
            MAX_FILES_PER_DIR="$2"
            echo "Limiting to $MAX_FILES_PER_DIR files per directory"
        else
            MAX_TOTAL_FILES="$1"
            echo "Limiting to total $MAX_TOTAL_FILES files"
        fi
    fi

    # Compile Java files first
    echo "Compiling Java sources..."
    if ! javac Typecheck.java; then
        echo "Compilation failed! Fix errors and try again."
        exit 1
    fi

    # Run tests for both directories
    run_tests "tests/minijava-symboltable-tests/incorrect-tests"
    
    # run_tests "tests/minijava-symboltable-tests/correct-tests"

}  # Save complete output to log file

echo -e "\nTotal Failures: ${TOTAL_FAILURES}"