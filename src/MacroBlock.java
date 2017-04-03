
public class MacroBlock {
	static int SIZE = 16;
	int _x;
	int _y;
	int[] _pixels;

	public MacroBlock(int[] array, int x, int y) {
		_pixels = new int[SIZE*SIZE];
		for (int i = 0; i < SIZE*SIZE; i++) {
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