package ch12;

class A {
	void m() {
		System.out.println("outer");
	}
}

public class InnerDemo {

	public static void main(String[] args) {
		new InnerDemo().go();
	}

	void go() {
		new A().m();
		class A {
			void m() {
				System.out.println("inner");
			}
		}
	}

	class A {
		void m() {
			System.out.println("middle");
		}
	}

}
