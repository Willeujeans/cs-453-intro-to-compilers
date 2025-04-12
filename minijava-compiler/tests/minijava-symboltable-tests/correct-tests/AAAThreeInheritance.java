class mainClass{
    public static void main(String[] a){
    }
}

class childClass extends parentClass{
  public int childClassMethod(){
    return 20;
  }
}

class parentClass extends SuperClass {
  public int parentClassMethod(){
    return 10;
  }
}

class SuperClass extends UltraClass{
  int x;
  public int superClassMethod(){
    return 10;
  }
}

class UltraClass {
  public int ultraClassMethod(){
    return 10;
  }
}

