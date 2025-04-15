class allAssignments {
    public static void main(String[] a) {
        int intVariable ;
        int[] intArrayVariable ;
        boolean booleanVariable ;
        emptySingleClass classVariable ;

        intVariable = 5;
        intVariable = intVariable;
        intArrayVariable = new int[10];
        intArrayVariable[1] = intVariable;
        // booleanVariable = true;
        // booleanVariable = false;
        // classVariable = new emptySingleClass();
    }
}
class emptySingleClass{
}
class grandparentClass{
}
class parentClass extends grandparentClass{
}
class childClass extends parentClass{
}
