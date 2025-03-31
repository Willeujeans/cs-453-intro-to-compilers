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

        String result_type = myType.getType();
        if(result_type.equals(typeName)){
            output += " ✅";
        }else{
            output += " ❌";
        }
        System.out.println(output);
    }

    private void TestCheckIdenticalSameType(){
        String output = "[TestMyType] TEST 'TestCheckIdenticalSameType' ";
        String typeName = "int";
        MyType myType = new MyType(typeName);
        myType.addType("[]");
        myType.addType("[]");

        MyType otherType = new MyType(typeName);

        output += " " + myType.toString() + " != " + otherType.toString() + " ";

        if(myType.checkIdentical(otherType) == false){
            output += " ✅";
        }else{
            output += " ❌";
        }
        System.out.println(output);
    }

    private void TestCheckIdenticalSame(){
        String output = "[TestMyType] TEST 'TestCheckIdenticalSame' ";
        String typeName = "int";
        MyType myType = new MyType(typeName);
        myType.addType("[]");
        myType.addType("[]");

        MyType myTypeOther = new MyType(typeName);
        myTypeOther.addType("[]");
        myTypeOther.addType("[]");

        output += " " + myType.toString() + " == " + myTypeOther.toString() + " ";

        if(myType.checkIdentical(myTypeOther) == true){
            output += " ✅";
        }else{
            output += " ❌";
        }
        System.out.println(output);
    }
}
