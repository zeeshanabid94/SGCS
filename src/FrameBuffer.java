import java.util.concurrent.Semaphore;

public class FrameBuffer {
	Frame[] _buffer;
	int _producer;
	int _consumer;
	int _size;
	Semaphore _mutex;
	
	public FrameBuffer(int size) {
		_producer = 0;
		_consumer = 0;
		_size = size;
		_buffer = new Frame[_size];
		_mutex = new Semaphore(1);
	}
	
	public synchronized void incrementProducer() {
		_producer = (_producer+1)%_size;
		this.notify();
	}
	
	public synchronized void incrementConsumer() {
		_consumer = (_consumer+1)%_size;
		this.notify();
	}
	
	public synchronized Frame getNextFrame() {
		try {
			_mutex.acquire();
			if (_producer == _consumer) {
				_mutex.release();
				this.wait();
				_mutex.acquire();
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		Frame output = _buffer[_consumer];
		incrementConsumer();
		_mutex.release();
		return output;
	}
	
	public synchronized void addNextFrame(Frame frame) {
		try {
			_mutex.acquire();
			if (_producer-1 == _consumer) {
				_mutex.release();
				this.wait();
				_mutex.acquire();
			}
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		_buffer[_producer] = frame;
		incrementProducer();
		_mutex.release();
	}
}
