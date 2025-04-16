class allAssignments {
    public static void main(String[] a) {
        int intVariable ;
        int[] intArrayVariable ;
        boolean booleanVariable ;
        emptySingleClass classVariable ;
        classGiver newClassGiver ;

        intVariable = 5;
        intVariable = intVariable;
        intArrayVariable = new int[10];
        intArrayVariable[1] = intVariable;
        booleanVariable = true;
        booleanVariable = false;
        classVariable = new emptySingleClass();
        newClassGiver = new classGiver();
        classVariable = newClassGiver.giveClass();
        System.out.println(5);
    }
}
class classGiver{
    public emptySingleClass giveClass(){
        return new emptySingleClass();
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
