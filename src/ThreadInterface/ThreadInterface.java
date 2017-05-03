package ThreadInterface;

import java.util.concurrent.ConcurrentLinkedQueue;

import Decoder.EncodedFrame;

public abstract class ThreadInterface<E> implements Runnable {
	protected ConcurrentLinkedQueue<E> _items = new ConcurrentLinkedQueue<>();
	protected volatile boolean _hasNext = true;
	protected volatile boolean _running = false;
	protected volatile int _size = 50;
	
	public boolean hasNext() {
		return _hasNext || !_items.isEmpty();
	}
	
	public synchronized E getNextFrame() throws InterruptedException {
		if (_items.isEmpty())
			this.wait();
		this.notifyAll();
		return _items.remove();
	}
	
	public synchronized void addFrame(E eFrame) throws InterruptedException {
		if (_items.size() > _size)
			this.wait();
		_items.add(eFrame);

		this.notifyAll();
	}
	
	public void stop() {
		_running = false;
	}
	
	public boolean isRunning() {
		return _running;
	}
	

}
