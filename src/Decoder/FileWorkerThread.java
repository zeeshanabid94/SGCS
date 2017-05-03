package Decoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileWorkerThread implements Runnable {
	int _toRead;
	byte[] data;
	int _toSkip;
	File _file;
	
	public FileWorkerThread(File file, int toSkip, int toRead) {
		_toRead = toRead;
		_toSkip = toSkip;
		data = new byte[_toRead];
		_file = file;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		FileInputStream fin;
		try {
			fin = new FileInputStream(_file);
			fin.skip((long) _toSkip);
			fin.read(data, 0, data.length);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] getData() {
		return data;
	}
	
	
}
