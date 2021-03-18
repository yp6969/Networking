package il.ac.telhai.cn.chapter2_io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Scanner;

public class ReaderWriter {
	static ByteArrayOutputStream bo = null;
	static PipedOutputStream po = new PipedOutputStream();
	static PipedInputStream pi;
	private static final int MAX_VALID_INT = 1000;
	private static Scanner scanner = new Scanner(System.in);

	static {
		try {
			pi = new PipedInputStream(po);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void writeToFile(String fileName, String arg) throws IOException {
		Writer writer = new OutputStreamWriter(new FileOutputStream(new File(fileName)), "UTF-8");
		writer.write(arg);
		writer.close();	   	
	}

	public static void writeToPipe(String arg) throws IOException {
		Writer writer = new OutputStreamWriter(po, "UTF-8");
		writer.write(arg);
		writer.flush();
		return;
	}

	public static void writeToByteArray(String arg) throws IOException {
		bo = new ByteArrayOutputStream(2*arg.length());
		Writer writer = new OutputStreamWriter(bo, "UTF-8");
		writer.write(arg);
		writer.flush();
		return;
	}


	public static String getBytesDecimalFromFile(String fileName) throws IOException {	
		return getBytesDecimalFromStream(new FileInputStream(fileName));
	}


	public static String getBytesDecimalFromByteArray() throws IOException {
		byte [] bytes = bo.toByteArray();
		return getBytesDecimalFromStream(new ByteArrayInputStream(bytes));
	}

	public static String getBytesDecimalFromPipe() throws IOException {
		return getBytesDecimalFromStream(pi);
	}

	private static String getBytesDecimalFromStream(InputStream stream) throws IOException {
		StringBuilder sb = new StringBuilder();
		int i;

		while (stream.available() > 0) {
			i = stream.read();
			if (sb.length() != 0) sb.append(",");
			sb.append(i);
		}
		return sb.toString();
	}

	private static void readIntoFile(String fileName) throws IOException {
		PrintStream ps = new PrintStream(fileName);
		int i;
		while (scanner.hasNext() && (i = scanner.nextInt()) >= 0 ) {
			if (i <= 1000) {
				ps.println(i);
			}
		}
		ps.close();
	}
	
	public static void main (String [] args) throws IOException {
		for (String fileName : args) {
			readIntoFile(fileName);
		}
		int [] buckets = new int [MAX_VALID_INT+1];
		for (String fileName : args) {
			Scanner sc = new Scanner(new FileInputStream(fileName));
			while (sc.hasNext()) {
				int i = sc.nextInt();
				buckets[i]++;
			}
			sc.close();
		}
		for (int i=0; i<= MAX_VALID_INT; i++) {
			for (int j=1; j<=buckets[i]; j++) {
				System.out.println(i);
			}
		}
	}

}
