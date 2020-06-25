package main;

import java.io.OutputStream;
import java.io.PrintStream;

public class IOManager {
	private final static PrintStream originalStdOut = System.out;
	
	private static PrintStream previous;
	
	public static void restorePrevious() {
		previous = System.out;
		System.setOut(previous);
	}
	
	public static void enableStandardOutput() {
		previous = System.out;
		System.setOut(originalStdOut);
	}
	public static void disableStandardOutput() {
		previous = System.out;
		System.setOut(new PrintStream(new OutputStream() {
			public void close() {}
			public void flush() {}
			public void write(byte[] b) {}
			public void write(byte[] b, int off, int len) {}
			public void write(int b) {
			}
		}));
	}
}
