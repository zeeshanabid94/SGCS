import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MacroBlock {
	public enum Type{
		BACKGROUND, FOREGROUND, UNDEFINED
	}
	static int SIZE = 16;
	int _x;
	int _y;
	BufferedImage block;
	Vector2D _motionVector;
	ArrayList<MacroBlock> _neighbours;
	double _threshold;
	Type _type;

	
	public MacroBlock() {
		_x = 0;
		_y = 0;
		block = new BufferedImage(0,0,1);
		_motionVector = null;
		_neighbours = null;
		_threshold = 0;
		_type = Type.UNDEFINED;
		}

	public MacroBlock(int x, int y) {
		block = new BufferedImage(SIZE,SIZE,BufferedImage.TYPE_INT_RGB);
		_x = x;
		_y = y;
		_motionVector = null;
		_neighbours = new ArrayList<>();
		_threshold = 0;
		_type = Type.UNDEFINED;
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
	
	public int getPixel(int x, int y) {
		return block.getRGB(x, y);
	}
	
	public boolean areNeighboursMoving() {
		int numMoving = 0;
		for (MacroBlock block: _neighbours) {
			if (block._motionVector.length() > _threshold) {
				numMoving+=1;
			}
		}
		
		if (numMoving >= _neighbours.size()/2)
			return true;
		
		return false;
	}
	public void setMotionVector(Vector2D MotionVector) {
		_motionVector = MotionVector;
		
	}
	
	public void addNeighbour(MacroBlock neighbour) {
		_neighbours.add(neighbour);
	}
	
	public void setThreshold(double threshold) {
		_threshold = threshold;
	}
	public void isBackGround() {
		if (_motionVector.length() > _threshold) {
			_type = Type.FOREGROUND;
//				block = new BufferedImage(SIZE,SIZE,BufferedImage.TYPE_INT_RGB);
		} else {
			_type = Type.BACKGROUND;

		}
	}
	
	public BufferedImage getDCTBlock(int x, int y) {
		BufferedImage DCTBlock = new BufferedImage(8,8,BufferedImage.TYPE_INT_RGB);
		int DCTx = 0;
		int DCTy = 0;
		for (int i = y; i < y+8; i++) {
			for (int j = x; j < x+8; j++) {
				DCTBlock.setRGB(DCTx, DCTy, block.getRGB(j, i));
				DCTx++;
				if (DCTx >= 8) {
					DCTy++;
					DCTx = 0;
				}
			}
		}
		return DCTBlock;
	}
	
	public Type getType() {
		return _type;
	}
}