class P {
    public static void main(String[] a) {
        int x ;
        int y ;

        x = 29;
        System.out.println(new Fac().ComputeFac(10));
    }
}

class Fac {
    int x;
    public int ComputeFac(int num){
        int num_aux ;
        if (num < 1)
            num_aux = 1 ;
        else
            num_aux = num * (this.ComputeFac(num - 1)) ;
        return num_aux ;
    }
}

/**
 * Expected Symbols:
 * Symbols include:
 * Classes: P, Fac.
 * Methods: main, ComputeFac.
 * Variables: x (in P.main and Fac), y, num, num_aux.
 * 
 * We can store a Class as it's name as the type
 * or the type of class is "class"
 * 
 * Expected Symbol table:
 * 
 * (Global:P) : [Type: mainClass]
 * (Global:Fac) : [Type: class]
 * (Global:P:main) : [Type: method]
 * (Global:P:main:x) : [Type: ]
 * 
 */