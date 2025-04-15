class incorrectAssignments {
    public static void main(String[] a) {
        boolean boolVariable ;
        boolVariable = new BooleanGiver().giveBoolean(1);
    }
}

class BooleanGiver{
    public boolean giveBoolean(){
        return false;
    }
}