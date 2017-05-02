import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import Player.Player;

public class TestDecoder {
	Player _player;
	
	public TestDecoder() {
		_player = new Player();
	}

	public void runTest(String filename) throws FileNotFoundException, InterruptedException {
		FileReaderThread fReader = new FileReaderThread(filename);
		ParsingThread parser = new ParsingThread(fReader);
		Thread fThread = new Thread(fReader);
		Thread pThread = new Thread(parser);
		fThread.start();

//		TimeUnit.MILLISECONDS.sleep(1000);
		pThread.start();
		
//		TimeUnit.MILLISECONDS.sleep(5000);
		
		while (parser.hasNext()) {
			long tNow = System.currentTimeMillis();
			System.out.println("Running");
			EncodedFrame eFrame = parser.getNextFrame();
			eFrame.setGaze(_player.getMouseX(), _player.getMouseY());
			_player.viewFrame(eFrame.getImage(16, 256));
			long tLater = System.currentTimeMillis();
			System.out.println("Time between frames "+ (tLater - tNow));
		}
//		System.out.println("Not Running");
	}
	
	public static void main(String args[]) throws FileNotFoundException, InterruptedException {
		TestDecoder test = new TestDecoder();
		test.runTest("E:/finaltest_200frames.cmp");
	}
}
