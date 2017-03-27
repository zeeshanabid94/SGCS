
public class MacroBlock {
	static int Size = 16;
	int _x;
	int _y;
	int[] _pixels;

	public MacroBlock(int[] array, int x, int y) {
		for (int i = 0; i < Size*Size; i++) {
			_pixels[i] = array[x+y*Video.HEIGHT];
		}
		_x = x;
		_y = y;
	}

	public int getX() {
		return _x;
	}

	public int getY() {
		return _y;
	}

	public int[] getPixels() {
		return _pixels;
	}
}