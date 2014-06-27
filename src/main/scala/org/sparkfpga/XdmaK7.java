package org.sparkfpga;

public class XdmaK7 {
    static {
        System.loadLibrary("xdmak7");
    }
    public static final int ENABLE_LOOPBACK = 0;
    public static final int CHECKER = 1;
    public static final int GENERATOR = 2;
    public static final int CHECKER_GEN = 3;

    public native void init();
    public native int flush();
    public native int startTest(int engine, int testmode, int maxsize);
    public native int stopTest(int engine, int testmode, int maxsize);
    public native int write(int ch, byte[] b);                      //return bytes written
    public native int write(int ch, byte[] b, int off, int len);    //return bytes written
    public native int read(int ch, byte[] b);                       //return bytes read
    public native int read(int ch, byte[] b, int off, int len);     //return bytes read
    public native int readFully(int ch, byte[] b);                  //return bytes read
    public native int readFully(int ch, byte[] b, int off, int len);//return bytes read
}
