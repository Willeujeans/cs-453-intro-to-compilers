class P {
    public static void main(String[] a) {
        int x;
        int y;

        x = 29;
        System.out.println(new Fac().ComputeFac(10, "hello"));
    }
}

class Fac {
    int x;
    public int ComputeFac(int num, String phrase){
        String myPhrase = phrase;
        int num_aux ;
        if (num < 1)
            num_aux = 1 ;
        else
            num_aux = num * (this.ComputeFac(num - 1, myPhrase)) ;
        return num_aux ;
    }
}