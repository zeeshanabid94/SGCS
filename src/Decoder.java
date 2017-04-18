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

public class Decoder extends Video {
	File _file;
	
	public Decoder(String encodedFilePath) {
		super(960, 544);
		_file = new File(encodedFilePath);
	}
	
	public void DecodeFrames() throws IOException {
		BufferedReader fin = new BufferedReader(new FileReader(_file));
		
		while(fin.ready()) {
			StringBuffer input = new StringBuffer();
			for (int i = 0; i < 24480; i++) {
				String readLine = fin.readLine();
				input.append(readLine+ "\n");
			}
			EncodedFrame eFrame = new EncodedFrame(input, WIDTH, HEIGHT);
			this.frames.add(eFrame);
			this.totalFrames = frames.size();
		}

		

	}
}
