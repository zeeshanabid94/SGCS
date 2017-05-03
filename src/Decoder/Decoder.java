package Decoder;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import DCT.DCT;
import Encoder.Encoder;
import Encoder.Vector2D;
import Encoder.Video;
import ThreadInterface.ThreadInterface;

public class Decoder {
//	File _file;
//	private BufferedReader fin;
//	DCT _idct;
//	Video _video;
//	int _gazeWindowSize;
//	Vector2D _gazeXY;
	
	ThreadInterface<String> _fThread;
	ThreadInterface<EncodedFrame> _pThread;
	EncodedFrame _currentframe;
	String _filePath;
	Encoder _encoder;
	
	public Decoder(String encodedFilePath) throws FileNotFoundException {
		_filePath = encodedFilePath;
		_fThread = new FileReaderThread(_filePath);
		_pThread = new ParsingThread(_fThread);
	}
	
	public Decoder(Encoder encoder) throws FileNotFoundException {
		_filePath = null;
		_encoder = encoder;
		_fThread = encoder; 
		_pThread = new ParsingThread(_fThread);
	}
	
	public void startThreads() {
		if (_fThread != null)
			new Thread(_fThread).start();
//		TimeUnit.MILLISECONDS.sleep(1000);
		if (!_pThread.isRunning())
			new Thread(_pThread).start();
	}
	
	public EncodedFrame getNextFrame() throws InterruptedException {
		if (!_pThread.hasNext())
			return null;
		_currentframe = _pThread.getNextFrame();
		return _currentframe;
	}

	public EncodedFrame getCurrentFrame() {
		return _currentframe;
	}

	public void reset() throws FileNotFoundException { 
		_fThread.stop();
		_pThread.stop();
		while (_fThread.isRunning() || _pThread.isRunning());
		if (_fThread.getClass().isInstance(Encoder.class)) {
			_fThread = _encoder;
			_encoder.reset();
		} else {
			_fThread = new FileReaderThread(_filePath);
		}

		_pThread = new ParsingThread(_fThread);
	}
	
	
}
