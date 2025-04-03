package picojava;
import java.util.*;
import syntaxtree.*;
import visitor.*;

public class TestMyType {
    public TestMyType(){
        this.TestConstructorEmptyString();
        this.TestGetTypeSingle();
        this.TestGetTypeMultiple();
        this.TestCheckIdenticalSameType();
        this.TestCheckIdenticalSame();
    }

    private void TestConstructorEmptyString(){
        StringBuilder output = new StringBuilder("[TestMyType] TEST 'TestConstructorEmptyString' ");
        try {
            new MyType("");
            output.insert(0, " ❌");
        } catch (Exception e) {
            output.insert(0, " ✅");
            System.out.println(output);
        }
    }

    private void TestGetTypeSingle(){
        StringBuilder output = new StringBuilder("[TestMyType] TEST 'TestGetTypeSingle' ");
        String typeName = "int";
        MyType myType = new MyType(typeName);

        String result_type = myType.getType();
        if(result_type.equals(typeName)){
            output.insert(0, " ✅");
        }else{
            output.insert(0, " ❌");
        }
        System.out.println(output);
    }

    private void TestGetTypeMultiple(){
        StringBuilder output = new StringBuilder("[TestMyType] TEST 'TestGetTypeMultiple' ");
        String typeName = "int";
        MyType myType = new MyType(typeName);
        myType.addType("[]");
        myType.addType("[]");

        String result_type = myType.getType();
        if(result_type.equals(typeName)){
            output.insert(0, " ✅");
        }else{
            output.insert(0, " ❌");
        }
        System.out.println(output);
    }

    private void TestCheckIdenticalSameType(){
        StringBuilder output = new StringBuilder("[TestMyType] TEST 'TestCheckIdenticalSameType' ");
        String typeName = "int";
        MyType myType = new MyType(typeName);
        myType.addType("[]");
        myType.addType("[]");

        MyType otherType = new MyType(typeName);

        if(myType.checkIdentical(otherType) == false){
            output.insert(0, " ✅");
        }else{
            output.insert(0, " ❌");
        }
        System.out.println(output);
    }

    private void TestCheckIdenticalSame(){
        StringBuilder output = new StringBuilder("[TestMyType] TEST 'TestCheckIdenticalSame' ");
        String typeName = "int";
        MyType myType = new MyType(typeName);
        myType.addType("[]");
        myType.addType("[]");

        MyType myTypeOther = new MyType(typeName);
        myTypeOther.addType("[]");
        myTypeOther.addType("[]");

        if(myType.checkIdentical(myTypeOther) == true){
            output.insert(0, " ✅");
        }else{
            output.insert(0, " ❌");
        }
        System.out.println(output);
    }
}
