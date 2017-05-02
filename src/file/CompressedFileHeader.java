package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.plaf.FileChooserUI;

public class CompressedFileHeader {
	int[] _frameByteIndex;
	int _size;
	int _ptr;
	File _file;
	
	public CompressedFileHeader(int size) {
		_size = size;
		_frameByteIndex = new int[_size];
		_ptr = 0;
		_file = null;
	}
	
	public byte[] getBytes() {
		String header = _size + "\n";
		for (int i = 0; i < _frameByteIndex.length; i++) {
			header += _frameByteIndex[i] + " ";
		}
		header += "\n";
		
		return header.getBytes();
	}
	
	public void addByteIndex(int newIndex) {
		_frameByteIndex[_ptr++] = newIndex;
	}
	
	public void createHeaderFile(String name) {
		_file = new File(name);
	}
	
	public void writeHeaderFile() throws IOException {
		FileOutputStream fout = new FileOutputStream(_file);
		fout.write(getBytes());
		fout.close();
	}
	
	public static CompressedFileHeader parseHeaderFile(String filePath) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileInputStream(new File(filePath)));
		int size = scanner.nextInt();
		CompressedFileHeader header = new CompressedFileHeader(size);
		for (int i = 0; i < size; i++) {
			header.addByteIndex(scanner.nextInt());
		}
		return header;
	}
	
	public int getByteIndex(int i) {
		if (i < 0)
			return 0;
		return _frameByteIndex[i];
	}
}
