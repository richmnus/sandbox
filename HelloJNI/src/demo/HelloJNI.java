package demo;

public class HelloJNI {
	static {
		// hello.dll (Windows) or libhello.so (Unix)
		System.loadLibrary("hello");
	}

	// A native method that receives nothing and returns void
	private native void sayHello();

	public static void main(String[] args) {
		new HelloJNI().sayHello();
	}
}
