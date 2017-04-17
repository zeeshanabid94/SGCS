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

public class Decoder {
	File _file;
	
	public Decoder(String encodedFilePath) {
		_file = new File(encodedFilePath);
	}
	
	public BufferedImage DecodeFrame() throws IOException {
		BufferedReader fin = new BufferedReader(new FileReader(_file));
		DCT iDCT = new DCT(0);
		Frame frame = new Frame(960, 544, 0, 0);
		BufferedImage image = null;
		
		while(fin.ready()) {
			String line = fin.readLine();
			String[] coeffs = line.split(" ");
			
			int[][] DCTcoeffs = new int[8][8];
			int ptr = 1;
			for (int i = 0; i < 64; i++) {
				DCTcoeffs[i%8][i/8] = Integer.parseInt(coeffs[ptr]);
				ptr++;
			}
			int[][] rBlock = iDCT.inverseDCT(DCTcoeffs);
			
			line = fin.readLine();
			coeffs = line.split(" ");
			
			DCTcoeffs = new int[8][8];
			for (int i = 0; i < 64; i++) {
				DCTcoeffs[i%8][i/8] = Integer.parseInt(coeffs[ptr]);
				ptr++;
			}
			int[][] gBlock = iDCT.inverseDCT(DCTcoeffs);
			
			line = fin.readLine();
			coeffs = line.split(" ");
			
			DCTcoeffs = new int[8][8];
			for (int i = 0; i < 64; i++) {
				DCTcoeffs[i%8][i/8] = Integer.parseInt(coeffs[ptr]);
				ptr++;
			}
			int[][] bBlock = iDCT.inverseDCT(DCTcoeffs);
		
			image = new BufferedImage(8,8, BufferedImage.TYPE_INT_RGB);

			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					Color color = new Color(rBlock[j][i], gBlock[j][i], bBlock[j][i]);
					image.setRGB(j, i, color.getRGB());
				}
			}
		}
		
		return image;

		

	}
}
