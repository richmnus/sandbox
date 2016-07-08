package uk.martinus;

public class Main {

	public IMsg getMessage() {
		// Return a newly created object of an anonymous inner class that either implements or is a sub-type of IMsg
		return new IMsg() {
			public String getMsg() {
				return "Hello world";
			}
		};
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		
		// Test the anonymous inner class above
		System.out.println(m.getMessage().getMsg());
		
		// Test nested class C

		
	}

}
