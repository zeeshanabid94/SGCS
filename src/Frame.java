import java.awt.image.BufferedImage;

public class Frame {
	MacroBlock[] _macroblocks;
	BufferedImage image;
	int width;
	int height;
	int macroblockSize = 16;
	int wPadding;
	int hPadding;
	public Frame() {
		image = null;
		_macroblocks = new MacroBlock[2025];
	}
	  
	public Frame(int w, int h, int wpadding, int hpadding) {
		width = w;
		height = h;
		wPadding = wpadding;
		hPadding = hpadding;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int hMacro = height/16;
		int wMacro = width/16;
		_macroblocks = new MacroBlock[hMacro * wMacro];
//
//		int x = 0;
//		int y = 0;
//		
//		for (int i = 0; i < _macroblocks.length; i++) {
//			int[] pixels = new int[macroblockSize*macroblockSize];
//			int ptrx = x;
//			int ptry = y;
//			for (int j = 0; j < macroblockSize*macroblockSize; j++) {
//				pixels[i] = imageBytes[ptrx+ptry*height];
//				ptrx++;
//				if (ptrx >= x+16) {
//					ptry++;
//					ptrx = x;
//				}
//			}
//			_macroblocks[i] = new MacroBlock(imageBytes, x, y);
//			x+= 16;
//			if (x >= width) {
//				x = 0;
//				y += 16;
//			}
//		}
	}
	
	public void setPixel(int x, int y, int pixel) {
		x += wPadding/2;
		y += hPadding/2;
		image.setRGB(x, y, pixel);
	}

	
	public BufferedImage getFrameImage() {
		return image;
	}
	
	public BufferedImage constructImageFromMacroblocks() {
		BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for (MacroBlock block : _macroblocks) {
			for (int i = 0; i < macroblockSize * macroblockSize; i++) {
				int pixel = 0x00000000 | ( block.getPixels()[i] << 16) | (block.getPixels()[i + height * width] << 8) | (block.getPixels()[i + height * width * 2]);
				frame.setRGB((block.getX()+i)%width, (block.getY()+i)/width, pixel);
			}
		}
		
		return frame;
	}
}