class emptyMain {
    public static void main(String[] a) {
        int intVariable ;
        int[] intArrayVariable ;
        boolean booleanVariable ;
        emptySingleClass classVariable ;
    }
}

class emptySingleClass{
    int intVariable ;
    int[] intArrayVariable ;
    boolean booleanVariable ;
    emptySingleClass classVariable ;
}
class grandparentClass{
    int intVariable ;
    int[] intArrayVariable ;
    boolean booleanVariable ;
    emptySingleClass classVariable ;
}
class parentClass extends grandparentClass{
    int intVariable ;
    int[] intArrayVariable ;
    boolean booleanVariable ;
    emptySingleClass classVariable ;
}
class childClass extends parentClass{
    int intVariable ;
    int[] intArrayVariable ;
    boolean booleanVariable ;
    emptySingleClass classVariable ;
}
