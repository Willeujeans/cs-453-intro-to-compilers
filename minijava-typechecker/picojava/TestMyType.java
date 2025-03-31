package picojava;
import java.util.*;
import syntaxtree.*;
import visitor.*;

public class TestMyType {
    public TestMyType(){
        this.TestConstructorEmptyString();
        this.TestGetTypeSingle();
        this.TestGetTypeMultiple();
    }

    private void TestConstructorEmptyString(){
        String output = "[TestMyType] TEST 'TestConstructorEmptyString' ";
        try {
            MyType myTypeStringConstruct = new MyType("");
        } catch (Exception e) {
            output += " ✅";
            System.out.println(output);
        }
        
    }

    private void TestGetTypeSingle(){
        String output = "[TestMyType] TEST 'TestGetTypeSingle' ";
        String typeName = "int";
        MyType myType = new MyType(typeName);

        String result_type = myType.getType();
        if(result_type.equals(typeName)){
            output += " ✅";
        }else{
            output += " ❌";
        }
        System.out.println(output);
    }

    private void TestGetTypeMultiple(){
        String output = "[TestMyType] TEST 'TestGetTypeMultiple' ";
        String typeName = "int";
        MyType myType = new MyType(typeName);
        myType.addType("[]");
        myType.addType("[]");
        // int[][] would be stored as -> ["int", "[]", "[]"]
        // Which means the type would be int
        String result_type = myType.getType();
        if(result_type.equals(typeName)){
            output += " ✅";
        }else{
            output += " ❌";
        }
        System.out.println(output);
    }

    private void TestCheckIdentical(MyType other){

    }



}
