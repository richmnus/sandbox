package ch1;

public class Main {

    // Only an inner class can be defined as static
    static class StaticClass {
        private int i;
        
        StaticClass() {
        }
    }
    
    class A {
        private int i;
    }
    
	public static void main(String[] args) {
		System.out.println("Hello World");
		StaticClass myObj = new StaticClass();
		StaticClass myObj2 = new StaticClass();
		System.out.println(myObj==myObj2);
		
		A[] a = new A[10];
		System.out.println(a);
		StaticClass obj =  new Main.StaticClass();
	}

	
	
}
