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

import DCT.DCT;
import Encoder.Vector2D;
import Encoder.Video;

public class Decoder {
	File _file;
	private BufferedReader fin;
	DCT _idct;
	Video _video;
	int _gazeWindowSize;
	Vector2D _gazeXY;
	
	public Decoder(String encodedFilePath, int gazeX, int gazeY) {
		_video = new Video(960, 544);
		_file = new File(encodedFilePath);
		_idct = new DCT(0,16,256);
		_gazeWindowSize = 64;
		_gazeXY = new Vector2D(gazeX, gazeY);
	}
	
	public void setGaze(int gazeX, int gazeY) {
		_gazeXY.setX(gazeX);
		_gazeXY.setY(gazeY);
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

		}
	}
	
	public Video getVideo() {
		return _video;
	}
}
