import java.awt.image.BufferedImage;

public class MacroBlock {
	static int SIZE = 16;
	int _x;
	int _y;
	BufferedImage block;
	
	public MacroBlock() {
		_x = 0;
		_y = 0;
		block = new BufferedImage(0,0,1);
	}

	public MacroBlock(int x, int y) {
		block = new BufferedImage(SIZE,SIZE,BufferedImage.TYPE_INT_RGB);
		_x = x;
		_y = y;
	}

	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}

	public BufferedImage getPixels() {
		return block;
	}
	
	public void setPixel(int x, int y, int pixel) {
		block.setRGB(x, y, pixel);
	}
}