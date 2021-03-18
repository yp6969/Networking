package il.ac.telhai.cn.chapter3_threads;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Writer;
import java.util.Scanner;

import org.junit.Test;

public class NumberGuesserTest {
	private static final int MAX_TRIALS = 100;

	@Test
	public void test() throws IOException {
		PipedOutputStream pos = new PipedOutputStream();
		PipedInputStream pis = new PipedInputStream();
		

		Scanner guessScanner = new Scanner(pis);
        Writer feedbackWriter = new OutputStreamWriter(pos);
        int chosen = (int) (Math.random()*Integer.MAX_VALUE);

		Thread child = new Thread(new NumberGuesser(pis, pos));
        child.start();
        
        int guess;
        int trials = 0;
        do {
        	trials++;
            guess = guessScanner.nextInt();
        	feedbackWriter.write(guess<chosen ? "<" : guess>chosen ? ">" : "=");
        	feedbackWriter.flush();
        } while (guess != chosen && trials < MAX_TRIALS);
        feedbackWriter.close();
        guessScanner.close();
        assertTrue(guess == chosen);
        System.out.println("Guessed in " + trials + " trials");
	}

}
