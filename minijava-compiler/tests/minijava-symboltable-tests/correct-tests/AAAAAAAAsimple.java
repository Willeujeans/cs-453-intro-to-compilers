class AAAAAAAAsimple {
    public static void main(String[] a){
        myParentClass x;
        x = new myChildClass().myClassMethod();
        }
}

class myChildClass extends myParentClass{
}

class myParentClass{
    public myParentClass myClassMethod(){
        return new myChildClass();
    }
}
