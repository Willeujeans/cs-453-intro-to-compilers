class allAssignments {
    public static void main(String[] a) {
        emptySingleClass classVariable ;
        classGiver newClassGiver ;
        classVariable = newClassGiver.giveClass(1);
        System.out.println(5);
    }
}
class classGiver{
    public emptySingleClass giveClass(int x){
        return new emptySingleClass();
    }
}
class emptySingleClass{
}

