class incorrectAssignments {
    public static void main(String[] a) {
        int integerVariable ;
        integerVariable = new BooleanGiver().giveBoolean();

    }
}

class BooleanGiver{
    public boolean giveBoolean(){
        return false;
    }
}