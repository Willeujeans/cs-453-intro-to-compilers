class myParentClass {
    public static void main(String[] a) {
        int x;
        myChildClass childInstance;
        x = 1;
        childInstance = new myChildClass();
    }
}

class myChildClass extends myParentClass{
    int x ;
}
