import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.SynchronousQueue;

import file.CompressedFileHeader;

public class FileReaderThread implements Runnable {
	File _file;
	Scanner _fin;
	ConcurrentLinkedQueue<String> _frames;
	volatile boolean _hasNext;
	CompressedFileHeader _header;
	
	public FileReaderThread(String filepath) throws FileNotFoundException {
		_file = new File(filepath);
		_fin = new Scanner(new FileInputStream(_file));
		_frames = new ConcurrentLinkedQueue<>();
		_hasNext = true;
		_header = CompressedFileHeader.parseHeaderFile("E:/HeaderFile.hdr");
	}

	public synchronized String getNextFrame() throws InterruptedException {
		if (_frames.isEmpty())
			wait();
		return _frames.remove();
	}
	
	public synchronized void addFrame(String frame) {
		_frames.add(frame);
		this.notifyAll();
	}
	
	public boolean hasNextFrame() {
		return _hasNext || !_frames.isEmpty();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		long tNow = System.currentTimeMillis();
		int i = 0;
		try {
			while ((_fin.hasNext() == _hasNext)) {
				FileWorkerThread ft1 = new FileWorkerThread(_file, _header.getByteIndex(i-1), _header.getByteIndex(i) - _header.getByteIndex(i-1));
				i++;
				FileWorkerThread ft2 = new FileWorkerThread(_file, _header.getByteIndex(i-1), _header.getByteIndex(i) - _header.getByteIndex(i-1));
				i++;
				Thread t1 = new Thread(ft1);
				Thread t2 = new Thread(ft2);
				t1.start();
				t2.start();
				t1.join();
				t2.join();
				addFrame(new String(ft1.getData()));
				addFrame(new String(ft2.getData()));
			}
			
		} catch (Exception e) {
			
		}
		_hasNext = false;

		long tLater = System.currentTimeMillis();
		
		System.out.println("File Reading took " + (tLater - tNow) + " ms");
	}
}
