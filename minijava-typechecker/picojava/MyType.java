package picojava;

import java.util.*;
import syntaxtree.*;
import visitor.*;

public class MyType {
    public Vector<String> type_array;

    MyType(String s){
        type_array = new Vector<String>();
    }

    MyType(){
        type_array = new Vector<String>();
    }
    
    Boolean checkIdentical(MyType other) {
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

    String getType() {
        if(type_array.isEmpty()){
            return null;
        }else{
            return type_array.firstElement();
        }
    }

}
