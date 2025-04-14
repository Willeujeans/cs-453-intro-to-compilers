class AAAAAAAAsimple {
    public static void main(String[] a){
        myParentClass x;
        x = new myChildClass().myClassMethod(false, 1);
        }
}

class myChildClass extends myParentClass{
}

class myParentClass{
    public myParentClass myClassMethod(int x, boolean y){
        return new myChildClass();
    }
}
