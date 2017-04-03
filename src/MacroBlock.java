
public class MacroBlock {
	static int Size = 16;
	int _x;
	int _y;
	int[] _pixels;

	public MacroBlock(int[] array, int x, int y) {
		_pixels = new int[Size*Size];
		int startHorizLocation = x;
		int crntHorizLocation = x;
		int crntVertLocation = y;
		for (int i = 0; i < Size*Size; i++) {
			_pixels[i] = array[crntHorizLocation+crntVertLocation*Video.HEIGHT];
			if(i!=0 && i%Size == 0) {
				crntHorizLocation = startHorizLocation;
				crntVertLocation++;
			} else{
				crntHorizLocation++;
			}
		}
		_x = x;
		_y = y;
	}

	public
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