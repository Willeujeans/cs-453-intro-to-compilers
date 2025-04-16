class mainClass {
    public static void main(String[] a) {
        int intVariable ;
        int[] intArrayVariable ;
        boolean booleanVariable ;
        childClass classVariable ;
        parentClass pclassVariable ;

        intVariable = 1 ;
        intArrayVariable = new int[5] ;
        booleanVariable = true ;
        
        classVariable = new childClass() ;
        pclassVariable = classVariable.myMethod(new parentClass(), new childClass()) ;
    }
}
class childClass extends parentClass{
    int intVariable ;

    public parentClass myMethod(parentClass par, childClass chil){
        return new childClass();
    }
}

class parentClass{
    int intVariable ;

    public parentClass myMethod(parentClass par, childClass chil){
        return new parentClass();
    }
}
