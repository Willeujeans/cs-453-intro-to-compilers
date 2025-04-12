class mainClass{
    public static void main(String[] a){
      childClass child;
      child = new parentClass();
    }
}

class childClass extends parentClass{
  public int methodA(){
    return 20;
  }
}

class parentClass extends SuperClass {
  public int methodAB(){
    return 10;
  }
}

class SuperClass extends UltraClass{
  int x;
  public int methodABB(){
    return 10;
  }
}

class UltraClass {
  public int methodABBA(){
    return 10;
  }
}

