class mainClass{
    public static void main(String[] a){
      childClass child;
      child = new parentClass();
    }
}

class childClass extends parentClass{
  public int method(){
    return 20;
  }
}

class parentClass extends SuperClass {
  public int method(){
    return 10;
  }
}

class SuperClass extends UltraClass{
  int x;
  public int method(){
    return 10;
  }
}

class UltraClass {
  public int method(){
    return 10;
  }
}

