import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;

public class ParsingThread implements Runnable {
	FileReaderThread _fThread;
	ConcurrentLinkedQueue<EncodedFrame> _eFrames;
	volatile boolean _hasNext;
	
	public ParsingThread(FileReaderThread FT) {
		_fThread = FT;
		_eFrames = new ConcurrentLinkedQueue<>();
		_hasNext = true;
	}

	public boolean hasNext() {
		return _hasNext || !_eFrames.isEmpty();
	}
	
	public synchronized EncodedFrame getNextFrame() throws InterruptedException {
		if (_eFrames.isEmpty())
			this.wait();
		return _eFrames.remove();
	}
	
	public synchronized void addFrame(EncodedFrame eFrame) {
		_eFrames.add(eFrame);

		this.notifyAll();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i = 0;
		while (_fThread.hasNextFrame()) {
			long tNow = System.currentTimeMillis();
			String frame = null;
			try {
				frame = _fThread.getNextFrame();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			EncodedFrame eFrame = new EncodedFrame(960, 544);
			eFrame.parseFrame(frame);
			addFrame(eFrame);
			System.out.println(i++);
			System.out.println("FThread Running = " + _fThread.hasNextFrame());
			long tLater = System.currentTimeMillis();
			
			System.out.println("Parsing two frame takes " + (tLater - tNow) + " ms");
		}
		System.out.println("Parsing Thread Exiting");
		_hasNext = false;
	}
}
