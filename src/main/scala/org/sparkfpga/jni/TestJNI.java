package org.sparkfpga.jni;

public class TestJNI {
    static {
        System.loadLibrary("TestJNI");
    }
    public native String passString(String text);

    public static void main(String[] args) {
    TestJNI jni = new TestJNI();
    String text = jni.passString("Hello");
    }
}
