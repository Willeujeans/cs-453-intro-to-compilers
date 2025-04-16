class incorrectAssignments {
    public static void main(String[] a) {
        boolean boolVariable ;
        boolVariable = new BooleanGiver().giveBoolean(1);
        boolVariable = new BooleanGiver().giveBoolean(1, false);
        boolVariable = new BooleanGiver().giveBoolean(1, true);
    }
}

class BooleanGiver{
    public boolean giveBoolean(){
        return false;
    }
}