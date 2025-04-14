class AAAAAAAAsimple {
    public static void main(String[] a){
        int x;
        x = new myChildClass().myClassMethod(1);
        x = new myChildClass().myClassMethod();
        x = new myChildClass().myClassMethod(true);
        x = new myChildClass().myClassMethod(1, 2);
        }
}

class myChildClass{
    public int myClassMethod(int x){
        return x;
    }
}

// Method has no arguments, one is passed in the call
// Method is called with too many or too few arguments
// Method is called with an incorrect argument type
