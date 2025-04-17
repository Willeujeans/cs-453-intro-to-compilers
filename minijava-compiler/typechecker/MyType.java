package typechecker;

import java.util.*;

import javax.management.RuntimeErrorException;

import syntaxtree.*;
import visitor.*;

public class MyType {
    // int[][] would be stored as -> ["int", "[]"]
    public Vector<String> typeArray = new Vector<String>();

    public MyType() {
        this.typeArray = new Vector<String>();
    }

    public MyType(String... components) {
        if (components == null || Arrays.stream(components).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Components cannot be null or contain null");
        }
        this.typeArray = new Vector<>(Arrays.asList(components));
    }

    public MyType(MyType other) {
        if (other == null)
            throw new IllegalArgumentException("Cannot construct my type from nothing");
        this.typeArray = new Vector<String>(other.typeArray);
    }

    public String getBaseType() {
        if (typeArray.isEmpty()) {
            throw new IllegalStateException("MyType has no base type (empty typeArray)");
        }
        return typeArray.firstElement();
    }

    public boolean isSameType(MyType other) {
        if (other == null)
            return false;

        if (typeArray.size() != other.typeArray.size()) {
            System.out.println(typeArray.size() + " != " + other.typeArray.size());
            System.out.println("checking identical: Different Sizes: ");
            return false;
        }

        for (int i = 0; i < typeArray.size(); i++) {
            String thisType = typeArray.get(i);
            String otherType = other.typeArray.get(i);
            if (!thisType.equals(otherType)) {
                System.out.println(thisType + " ==!== " + otherType);
                return false;
            }
        }
        return true;
    }

    public Boolean isSimilarType(MyType other) {
        if (other == null || other.typeArray.isEmpty()) {
            return false;
        } else {
            for (String each : typeArray) {
                for (String every : other.typeArray) {
                    if (each.equals(every)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String toString() {
        return "MyType{" + String.join(",", typeArray) + "}";
    }
}
