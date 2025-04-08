import java.io.*;
import visitor.*;
import syntaxtree.*;
import java.util.*;
// Files are stored in the picojava directory/package.
import picojava.*;

public class TestTypecheck {
    public static void main(String[] args) {
        System.out.println("v--- [TestTypecheck] ---v");

        new TestMyType();
        new TestSymbolTable();
        new TestTypeValidator();

        System.out.println("^--- [TestTypecheck] ---^");
    }
}
