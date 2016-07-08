package uk.martinus;

public class PrivateConstructor {

    private int someInt;
    
    private PrivateConstructor() {
        
    }

    public int getSomeInt() {
        return someInt;
    }

    public void setSomeInt(int someInt) {
        this.someInt = someInt;
    }
    
}
