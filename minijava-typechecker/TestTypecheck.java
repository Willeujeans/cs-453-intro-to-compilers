import java.io.*;
import visitor.*;
import syntaxtree.*;
import java.util.*;
// Files are stored in the picojava directory/package.
import picojava.*;

public class TestTypecheck {
    public static void main(String[] args) {
        System.out.println("[TestTypecheck]: Start");
        TestMyType myTypeTester = new TestMyType();
        TestSymbolTable symbolTableTester = new TestSymbolTable();
        System.out.println("[TestTypecheck]: End");
    }
}
