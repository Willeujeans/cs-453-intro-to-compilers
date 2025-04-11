class BinaryTree{
    public static void main(String[] a){
		int x;
    ParentClass instance;
    instance = new ChildClass();
    x = instance.parentMethod();
    }
}

class ParentClass {
  public int parentMethod(){
    return 20;
  }
}

class ChildClass extends ParentClass {
  public int method(){
    return 10;
  }
}