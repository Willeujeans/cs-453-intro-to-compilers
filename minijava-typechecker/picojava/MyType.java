package picojava;
import java.util.*;
import syntaxtree.*;
import visitor.*;

public class MyType {
    // int[][] would be stored as -> ["int", "[]", "[]"]
    public Vector<String> type_array;

    public MyType(String inputString){
        if(inputString == "" || inputString == null){
            throw new IllegalArgumentException("Failed to construct MyType: empty String argument");
        }else{
            this.type_array = new Vector<String>();
            addType(inputString);
        }
    }

    public MyType(){
        type_array = new Vector<String>();
    }

    public void addType(String inputString){
        if(inputString == "" || inputString == null){
            throw new IllegalArgumentException("Failed to addType: empty String argument");
        }else{
            this.type_array.add(inputString);
        }
    }

    public String getType() {
        if(type_array.isEmpty() || type_array == null){
            return null;
        }else{
            return type_array.firstElement();
        }
    }
    
    public Boolean checkIdentical(MyType other) {
        if(other == null || other.type_array.isEmpty()){
            return false;
        }else{
            if (type_array.size() != other.type_array.size()) {
                return false;
            }
            for (int i = 0; i < type_array.size(); i++) {
                String thisType = type_array.elementAt(i);
                String otherType = other.type_array.elementAt(i);
                if (!thisType.equals(otherType)) {
                    return false;
                }
            }
            return true;
        }
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
