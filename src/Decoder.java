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
import java.util.ArrayList;

public class Decoder {
	File _file;
	private BufferedReader fin;
	DCT _idct;
	Video _video;
	
	public Decoder(String encodedFilePath) {
		_video = new Video(960, 544);
		_file = new File(encodedFilePath);
		_idct = new DCT(0,16,256);
	}
	
	public void DecodeFrames() throws IOException {
		fin = new BufferedReader(new FileReader(_file));
		EncodedFrame eFrame = null;
		int frameNo = 0;
		StringBuilder input = new StringBuilder();
		while(fin.ready()) {
			eFrame	= new EncodedFrame(_video.WIDTH, _video.HEIGHT);
			for (int i = 0; i < 2040; i++) {
				input.delete(0, input.length());
				for (int j = 0; j < 12; j++) {
					input.append(fin.readLine()).append("\n");
				}
				eFrame.addMacroBlock(input, _idct);
			}
			System.out.println(frameNo++);
			_video.frames.add(eFrame);
			_video.totalFrames = _video.frames.size();

		}
	}
	
	public Video getVideo() {
		return _video;
	}
}
