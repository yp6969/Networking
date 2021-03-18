package il.ac.telhai.cn.chapter3_threads;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Writer;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class CallableNumberGuesserTest {
	private static final int MAX_TRIALS = 100;


	@Test
	public void testCall() throws IOException, InterruptedException, ExecutionException {
		PipedOutputStream pos = new PipedOutputStream();
		PipedInputStream pis = new PipedInputStream();


		Scanner guessScanner = new Scanner(pis);
		Writer feedbackWriter = new OutputStreamWriter(pos);
		int chosen = (int) (Math.random()*Integer.MAX_VALUE);

		ExecutorService service = Executors.newFixedThreadPool(1);
		CallableNumberGuesser child = new CallableNumberGuesser(pis, pos);
		Future<Integer> result = service.submit(child);
        service.shutdown();
		
		int guess;
		int trials = 0;
		do {
			trials++;
			guess = guessScanner.nextInt();
			feedbackWriter.write(guess<chosen ? "<" : guess>chosen ? ">" : "=");
			feedbackWriter.flush();
		} while (guess != chosen && trials < MAX_TRIALS);
		assertTrue(guess == chosen);
		feedbackWriter.close();
		guessScanner.close();
		assertEquals(chosen, (int) result.get());
		System.out.println("Guessed in " + trials + " trials");
	}
}