package il.ac.telhai.cn.chapter2_io;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class ReaderWriterTest {

	@Test
	public void testWriteReadFile() {
		try {
			ReaderWriter.writeToFile("data1", "abc");
			ReaderWriter.writeToFile("data2", "אבג");
			assertEquals("215,144,215,145,215,146", ReaderWriter.getBytesDecimalFromFile("data2"));
			assertEquals("97,98,99", ReaderWriter.getBytesDecimalFromFile("data1"));
		} catch (IOException e) {
			fail(e.toString());
		}
	}

	@Test
	public void testWriteReadByteArray() {
		try {
			ReaderWriter.writeToByteArray("def");
			assertEquals("100,101,102", ReaderWriter.getBytesDecimalFromByteArray());
			ReaderWriter.writeToByteArray("אבג");
			assertEquals("215,144,215,145,215,146", ReaderWriter.getBytesDecimalFromByteArray());
		} catch (IOException e) {
			fail(e.toString());
		}
	}


	@Test
	public void testWriteReadPipe() {
		try {
			ReaderWriter.writeToPipe("ghi");
			ReaderWriter.writeToPipe("אבג");
			assertEquals("103,104,105,215,144,215,145,215,146", ReaderWriter.getBytesDecimalFromPipe());
		} catch (IOException e) {
			fail(e.toString());
		}
	}
	
}
