
public class Frame {
	MacroBlock[] _macroblocks;
	int[] imageBytes;
	public Frame() {
		imageBytes = null;
		_macroblocks = new MacroBlock[2025];
	}
	  
	public Frame(int width, int height) {
		imageBytes = new int[width*height*3];
		_macroblocks = new MacroBlock[2025];

		int x = 0;
		int y = 0;

		for (int i = 0; i < 2025; i++) {
			_macroblocks[i] = new MacroBlock(imageBytes, x, y);
			x+= 16;
			if (x >= Video.WIDTH) {
				x = 0;
				y += 16;
			}
		}
	}
}