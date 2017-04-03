import java.awt.image.BufferedImage;

public class Frame {
//	MacroBlock[] _macroblocks;
	int[] imageBytes;
	public Frame() {
		imageBytes = null;
//		_macroblocks = new MacroBlock[2025];
	}
	  
	public Frame(int width, int height) {
		imageBytes = new int[width*height*3];
//		_macroblocks = new MacroBlock[2025];

		int x = 0;
		int y = 0;

		for (int i = 0; i < 2025; i++) {
//			_macroblocks[i] = new MacroBlock(imageBytes, x, y);
			x+= 16;
			if (x >= Video.WIDTH) {
				x = 0;
				y += 16;
			}
		}
	}
	
	public int[] getBytes() {
		return imageBytes;
	}
	
	public BufferedImage getFrameImage() {
		BufferedImage frame = new BufferedImage(960, 540, BufferedImage.TYPE_INT_RGB);
		
		for (int i = 0; i < Video.HEIGHT * Video.WIDTH; i++) {
			int pixel = 0x00000000 | (0xff & imageBytes[i] << 16) | (0xff & imageBytes[i + Video.HEIGHT * Video.WIDTH] << 8) | (0xff & imageBytes[i + Video.HEIGHT * Video.WIDTH * 2]);
			frame.setRGB(i%(Video.WIDTH), i%Video.HEIGHT, pixel);
		}
		
		return frame;
	}
}