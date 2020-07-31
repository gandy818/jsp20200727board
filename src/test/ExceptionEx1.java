package test;

public class ExceptionEx1 {

	public void main() {
		A a = new B();
		a.method();
	}
}

class B implements A {
	public void method() {
		
	}
}

interface A { //추상메소드에 익셉션이 있는 경우
	
	void method() throws Exception;
	
}