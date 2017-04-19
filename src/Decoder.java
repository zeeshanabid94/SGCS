import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Decoder implements Runnable {
	File _file;
	private BufferedReader fin;
	DCT _idct;
	Video _video;
	FrameBuffer _frames;
	
	public Decoder(String encodedFilePath, FrameBuffer buffer) {
		_video = new Video(960, 544);
		_file = new File(encodedFilePath);
		_idct = new DCT(0,16,256);
		_frames = buffer;
	}
	
	public Frame DecodeFrames() throws IOException {
		return null;
	
	}
	
	public Video getVideo() {
		return _video;
	}

	@Override
	public void run() {
		try {
		fin = new BufferedReader(new FileReader(_file));
		EncodedFrame eFrame	= null; 
		int frameNo = 0;
		StringBuilder input = new StringBuilder();
		while (fin.ready()) {
			eFrame = new EncodedFrame(_video.WIDTH, _video.HEIGHT);
			Time t = new Time(System.currentTimeMillis());

			for (int i = 0; i < 2040; i++) {
				char[] charbuffer = new char[10000];
				while (input.indexOf("&") < 0) {
					fin.read(charbuffer, 0, charbuffer.length);
					input.append(new String(charbuffer));
				}
				if (input.charAt(0) == '\n')
					input = new StringBuilder(input.substring(1));
				String macroblock = input.substring(0, input.indexOf("&"));
				if (input.indexOf("&") == input.length()-1)
					input = new StringBuilder();
				else
					input = new StringBuilder(input.substring(input.indexOf("&")+2));
				eFrame.addMacroBlock(macroblock, _idct);
			}
			_frames.addNextFrame(eFrame);
			Time tLater = new Time(System.currentTimeMillis());
//			System.out.println(tLater.getTime() - t.getTime());
		}
		// add frame to buffer
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		
	}
}
