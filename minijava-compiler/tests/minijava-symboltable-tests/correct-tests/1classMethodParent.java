class mainClass {
    public static void main(String[] a) {
        boolean booleanValue;
        childClass classInstance ;
        classInstance = new childClass();
        booleanValue = classInstance.grandparentClassMethod();
        
    }
}

class childClass extends parentClass{
}

class parentClass extends grandparentClass{
}

class grandparentClass{
    public boolean grandparentClassMethod(){
        return false;
    }
}
