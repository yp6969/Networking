package il.ac.telhai.cn.chapter3_threads;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.concurrent.Callable;

public class CallableNumberGuesser implements Callable<Integer>{

	
	private static final int MAX_TRIALS = 100;
	private PipedInputStream pis;
	private PipedOutputStream pos;
	private Writer guessWriter;
	private Reader feedbackScanner;
	private int max = Integer.MAX_VALUE -1 , min = 0;
	
	
	public CallableNumberGuesser( PipedInputStream pis, PipedOutputStream pos) throws IOException {
		this.pis = new PipedInputStream(pos);
		this.pos = new PipedOutputStream(pis);
		guessWriter = new OutputStreamWriter(this.pos);
		feedbackScanner = new InputStreamReader(this.pis);
	}
	
	
	private int randNum() {
		return (int) (Math.random()*(max - min + 1)) + min;
	}
	
	
	private String intToString(int num){
		return String.valueOf(num) + "\n";
	}
	
	
	@Override
	public Integer call() throws Exception{
//		String feedBack;
		char feedBack;
		int chosen;
		int trails = 0;
		while(trails < MAX_TRIALS) {
			trails++;
			try {
				chosen = randNum();
				guessWriter.write(intToString(chosen));
				guessWriter.flush();
				feedBack = (char)(feedbackScanner.read());
				
				switch(feedBack) {
				case '<':
					min = chosen + 1;
					break;
				case '>':
					max = chosen - 1;
					break;
				case '=':
					return chosen;
				default:
					break;
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			
			guessWriter.close();
			feedbackScanner.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return trails;
	}
}
