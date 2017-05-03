package Decoder;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;

import Encoder.Encoder;
import ThreadInterface.ThreadInterface;

public class ParsingThread extends ThreadInterface {
	ThreadInterface<String> _readerThread;
	Encoder _eThread;
	ConcurrentLinkedQueue<EncodedFrame> _eFrames;
	volatile boolean _hasNext;
	volatile boolean _running;
	
	public ParsingThread(ThreadInterface<String> RT) {
		_readerThread = RT;
		_eFrames = new ConcurrentLinkedQueue<>();
		_hasNext = true;
		_running = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		_running = true;
		int i = 0;
		while (_readerThread.hasNext() && _running == true) {
			long tNow = System.currentTimeMillis();
			String frame = null;
			try {
				frame = _readerThread.getNextFrame();

			EncodedFrame eFrame = new EncodedFrame(960, 544);
			eFrame.parseFrame(frame);
			addFrame(eFrame);
//			System.out.println(i++);
//			System.out.println("FThread Running = " + _fThread.hasNextFrame());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			long tLater = System.currentTimeMillis();
			
//			System.out.println("Parsing two frame takes " + (tLater - tNow) + " ms");
		}
		System.out.println("Parsing Thread Exiting");
		_hasNext = false;
	}
	
}
