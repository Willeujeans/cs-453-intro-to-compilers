package typechecker;

import java.util.*;
import syntaxtree.*;
import visitor.*;

public class MyType {
    // int[][] would be stored as -> ["int", "[", "]"]
    public Vector<String> type_array;

    public MyType(String... components) {
        this.type_array = new Vector<>(Arrays.asList(components));
    }

    public MyType(MyType other) {
        this.type_array = new Vector<String>(other.type_array);
    }

    public String getType() {
        if(type_array.isEmpty() || type_array == null){
            return null;
        }else{
            return type_array.firstElement();
        }
    }
    
    public boolean checkIdentical(MyType other) {
        if (other == null) {
            System.out.println("checking identical: NULL");
            return false;
        }
        if (type_array.size() != other.type_array.size()) {
            System.out.println("checking identical: Different Sizes");
            return false;
        }
        for (int i = 0; i < type_array.size(); i++) {
            String thisType = type_array.get(i);
            String otherType = other.type_array.get(i);
            if (!thisType.equals(otherType)) {
                System.out.println(thisType + " ==!== " + otherType);
                return false;
            }
        }
        return true;
    }

    public Boolean checkSimilar(MyType other) {
        if(other == null || other.type_array.isEmpty()){
            return false;
        }else{
            for(String each : type_array){
                for(String every : other.type_array){
                    if(each.equals(every)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String toString(){
        String output = "[";
        for(int i = 0; i < this.type_array.size(); ++i){
            output += this.type_array.get(i);
            if(i < this.type_array.size() - 1){
                output += ",";
            }
        }
        output += "]";
        return output;
    }
}
