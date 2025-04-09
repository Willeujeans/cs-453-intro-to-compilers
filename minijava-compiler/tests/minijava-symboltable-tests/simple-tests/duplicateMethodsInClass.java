class duplicateMethodsInClass {
    public static void main(String[] a) {
        int x;
    }
}

class otherClass{
    public int duplicateMethod(){
        return 0;
    }
    public int duplicateMethod(){
        return 0;
    }
}
