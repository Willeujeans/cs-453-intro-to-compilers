class mainClass {
    public static void main(String[] a) {
        int intVariable ;
        int[] intArrayVariable ;
        boolean booleanVariable ;
        singleClassMethod classVariable ;

        intVariable = 2 ;
        intArrayVariable = new int[5] ;
        booleanVariable = true ;
        
        classVariable = new singleClassMethod() ;
        intVariable = classVariable.myMethod(1, false, intArrayVariable, intVariable) ;
    }
}
class singleClassMethod{
    int intVariable ;

    public int myMethod(int argumentInt, boolean argumentBoolean, int[] argumentArray, int argumentIntAgain){
        int methodInt ;
        methodInt = intVariable + argumentInt ; 
        
        return methodInt;
    }
}
