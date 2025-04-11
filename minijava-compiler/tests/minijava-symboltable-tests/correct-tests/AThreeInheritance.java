class superClass{
    public static void main(String[] a){
    }
}

class ParentClass extends superClass{
  public int methodParent(){
    return 20;
  }
}

class ChildClass extends ParentClass {
  public int methodChild(){
    return 10;
  }
}

class BabyClass extends ChildClass {
  public int methodBaby(){
    return 10;
  }
}