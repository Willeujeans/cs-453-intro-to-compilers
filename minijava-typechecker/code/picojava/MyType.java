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
    
    Boolean checkIdentical(MyType other_type_object) {
        return this.type_array.equals(other_type_object.type_array);
    }

    String getArrayBaseType() {
        if(type_array.size() > 0){
            return type_array.firstElement();
        }else{
            return null;
        }
    }

}
