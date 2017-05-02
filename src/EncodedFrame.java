import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Scanner;

import DCT.DCT;
import DCT.FastDCT;
import Encoder.Frame;
import Encoder.Vector2D;

public class EncodedFrame {
	// Each Frame gets 24480 lines
	Vector2D _gazeXY;
	int _gazeWindowSize;
	BufferedImage _frame;
	int _width ;
	int _height;
	ArrayList<EncodedMacroBlock> _eBlocks;
	public EncodedFrame(int w, int h) {
		_width = w;
		_height = h;
		_gazeWindowSize = 128;
		_gazeXY = new Vector2D(100,100);
		_frame = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		_eBlocks = new ArrayList<>();
	}
	
	public void setGaze(int gazeX, int gazeY) {
		_gazeXY.setX(gazeX);
		_gazeXY.setY(gazeY);
	}
	
	public void parseFrame(String frame) {
		int _x = 0;
		int _y = 0;
//		Scanner framein = new Scanner(frame);
		String[] framein = frame.split("/");
		for (int i = 0; i < 2040; i++) {
			_eBlocks.add(new EncodedMacroBlock(framein[i], _x, _y));
			_x += 16;
			if (_x >= _width) {
				_x = 0;
				_y+= 16;
			}
		}
	}
	
	public BufferedImage getImage(int fn, int bn) {
		for (EncodedMacroBlock eblock : _eBlocks) {
			int quantizer = -1;
			
			if (eblock.getType() == 1){
				quantizer = fn;
				eblock.decodeMacroBlock(_frame, _gazeXY, _gazeWindowSize, quantizer);	
			} else {
				quantizer = bn;
				eblock.decodeMacroBlock(_frame, _gazeXY, _gazeWindowSize, quantizer);
			}
		}	
		return _frame;
	}
	
	public void addMacroBlock(StringBuilder macroblock, DCT iDCT) {

//		EncodedMacroBlock eMacro = new EncodedMacroBlock(macroblock.toString(), _x, _y, iDCT);
//		eMacro.decodeMacroBlock(_frame, iDCT);
		
//		_x+=16;
		
//		if (_x >= _width) {
//			_x=0;
//			_y+=16;
//		}
	}
}
