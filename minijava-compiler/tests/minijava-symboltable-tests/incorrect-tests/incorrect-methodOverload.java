class incorrectAssignments {
    public static void main(String[] a) {
    }
}

class childClass extends parentClass{
    public boolean giveBoolean(){
        return false;
    }
}

class parentClass extends grandparentClass{
}

class grandparentClass {
    public boolean giveBoolean(){
        return false;
    }
}