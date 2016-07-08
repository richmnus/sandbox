package uk.martinus.misc;

public class ObjectInspector {

    Object testObject;
    
    public ObjectInspector(Object testObject) {
        this.testObject = testObject;
    }

    public String inspect() {
        StringBuilder sb = new StringBuilder("");
        Class<? extends Object> clazz = testObject.getClass();
        sb.append(clazz.getName());
        sb.append("\n");
        return "";
    }
    
}
