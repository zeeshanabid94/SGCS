import java.awt.Color;
import java.awt.image.BufferedImage;

import DCT.DCT;
import DCT.FastDCT;
import Encoder.MacroBlock;
import Encoder.Vector2D;

public class EncodedMacroBlock {

	int[][] _rBlock = new int[16][16];
	int[][] _gBlock = new int[16][16];
	int[][] _bBlock = new int[16][16];
	int _type;
	int _x;
	int _y;
	
	public EncodedMacroBlock(String input, int bx, int by) {
		_x = bx;
		_y = by;
		String[] rCoeff;
		String[] gCoeff;
		String[] bCoeff;
		String[] lines = input.split("\n");
		int type = Integer.parseInt(lines[0].split(" ")[0]);
		if (type == 1) {
			_type = 1;
		} else {
			_type = 0;
		}
		int dx = 0, dy =0;
		for(int x =0; x < lines.length; x+=3) {
			rCoeff = lines[x].split(" ");
			for(int i = 1; i < 65; i++) {
				_rBlock[((i-1)/8) +dy][((i-1)%8) + dx] = Integer.parseInt(rCoeff[i]);
			}
			
			gCoeff = lines[x + 1].split(" ");
			for(int i = 1; i < 65; i++) {
				_gBlock[((i-1)/8) +dy][((i-1)%8) + dx] = Integer.parseInt(gCoeff[i]);
			}
			
			bCoeff = lines[x + 2].split(" ");
			for(int i = 1; i < 65; i++) {
				_bBlock[((i-1)/8) +dy][((i-1)%8) + dx] = Integer.parseInt(bCoeff[i]);
			}
			dx +=8;
			if(dx >= 16) {
				dx =0;
				dy+=8;
			}
		}		
		
		}
	
	public void decodeMacroBlock(BufferedImage Frame, Vector2D gazeXY,int gazeWindowSize, int quantizer) {
		int dx = 0, dy = 0;
		int[][] rDCT = new int[8][8];
		int[][] gDCT = new int[8][8];
		int[][] bDCT = new int[8][8];
		FastDCT iDCT = new FastDCT();
//		System.out.println(gazeXY.getX() + "," + gazeXY.getY());
		for(int y =0; y < 16; y+=8) {
			for(int x =0; x< 16; x+=8) {
				if (_x >= gazeXY.getX() && _y >= gazeXY.getY() && _x < (gazeXY.getX() + gazeWindowSize) && _y < (gazeXY.getY() + gazeWindowSize)) {
					for (int i = 0; i < 64; i++ ) {
						rDCT[i/8][i%8] = _rBlock[(i/8) + y][(i%8) + x];
						gDCT[i/8][i%8] = _gBlock[(i/8) + y][(i%8) + x];
						bDCT[i/8][i%8] = _bBlock[(i/8) + y][(i%8) + x];
					}
	//				rDCT = iDCT.quantizeBlock(rDCT, _type);
	//				gDCT = iDCT.quantizeBlock(gDCT, _type);
	//				bDCT = iDCT.quantizeBlock(bDCT, _type);
					int[][] rchannel = iDCT.fast_idct(rDCT);
					int[][] gchannel = iDCT.fast_idct(gDCT);
					int[][] bchannel = iDCT.fast_idct(bDCT);
					for (int i = 0; i < 64; i++ ) {
						Color rgb = new Color(rchannel[i%8][i/8],gchannel[i%8][i/8],bchannel[i%8][i/8]);
						Frame.setRGB(_x + x + (i%8),_y +  y + (i/8), rgb.getRGB());
					}
				} else {
					for (int i = 0; i < 64; i++ ) {
						rDCT[i/8][i%8] = (_rBlock[(i/8) + y][(i%8) + x]/quantizer) * quantizer;
						gDCT[i/8][i%8] = (_gBlock[(i/8) + y][(i%8) + x]/quantizer) * quantizer;
						bDCT[i/8][i%8] = (_bBlock[(i/8) + y][(i%8) + x]/quantizer) * quantizer;
					}

	//				rDCT = iDCT.quantizeBlock(rDCT, _type);
	//				gDCT = iDCT.quantizeBlock(gDCT, _type);
	//				bDCT = iDCT.quantizeBlock(bDCT, _type);
					int[][] rchannel = iDCT.fast_idct(rDCT);
					int[][] gchannel = iDCT.fast_idct(gDCT);
					int[][] bchannel = iDCT.fast_idct(bDCT);
					for (int i = 0; i < 64; i++ ) {
						Color rgb = new Color(rchannel[i%8][i/8],gchannel[i%8][i/8],bchannel[i%8][i/8]);
						Frame.setRGB(_x + x + (i%8),_y +  y + (i/8), rgb.getRGB());
					}
				}
			}
		}
	}

	public int getType() {
		// TODO Auto-generated method stub
		return _type;
	}
	
}
