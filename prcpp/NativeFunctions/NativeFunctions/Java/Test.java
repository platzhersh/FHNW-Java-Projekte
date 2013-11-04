public class Test {

public static native void display();
public static native int increment(int value);

static {
	System.loadLibrary("NativeFunctions");
}

public static void main(String[] args) {
	display();
	increment(1);
}



}