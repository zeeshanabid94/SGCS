import java.awt.Color;
import java.awt.image.BufferedImage;

public class EncodedMacroBlock extends MacroBlock {
	
	
	public EncodedMacroBlock(String input, int bx, int by, DCT iDCT) {
		super(bx, by);
		int[][] _rBlock = new int[16][16];
		int[][] _gBlock = new int[16][16];
		int[][] _bBlock = new int[16][16];
		String[] rCoeff;
		String[] gCoeff;
		String[] bCoeff;
		int[][] rDCT = new int[8][8];
		int[][] gDCT = new int[8][8];
		int[][] bDCT = new int[8][8];
		String[] lines = input.split("\n");
		int type = Integer.parseInt(lines[0].split(" ")[0]);
		if (type == 1) {
			_type = Type.FOREGROUND;
		} else {
			_type = Type.BACKGROUND;
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
		block = new BufferedImage(16,16,BufferedImage.TYPE_INT_RGB);
		for(int y =0; y < 16; y+=8) {
			for(int x =0; x< 16; x+=8) {
				for (int i = 0; i < 64; i++ ) {
					rDCT[i/8][i%8] = _rBlock[(i/8) + y][(i%8) + x];
					gDCT[i/8][i%8] = _gBlock[(i/8) + y][(i%8) + x];
					bDCT[i/8][i%8] = _bBlock[(i/8) + y][(i%8) + x];
				}
				rDCT = iDCT.quantizeBlock(rDCT, _type);
				gDCT = iDCT.quantizeBlock(gDCT, _type);
				bDCT = iDCT.quantizeBlock(bDCT, _type);
				int[][] rchannel = iDCT.inverseDCT(rDCT);
				int[][] gchannel = iDCT.inverseDCT(gDCT);
				int[][] bchannel = iDCT.inverseDCT(bDCT);
				for (int i = 0; i < 64; i++ ) {
					Color rgb = new Color(rchannel[i%8][i/8],gchannel[i%8][i/8],bchannel[i%8][i/8]);
					block.setRGB(x + (i%8), y + (i/8), rgb.getRGB());
				}
			}
		}
		}
	
	public void decodeMacroBlock() {
		int dx = 0, dy = 0;
		
	}
	
}
