import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Frame {
	ArrayList<MacroBlock> _macroblocks;
	BufferedImage image;
	int width;
	int height;
	int macroblockSize = 16;
	int wPadding;
	int hPadding;
	
	public Frame() {
		image = null;
//		_macroblocks = new MacroBlock[2025];
	}
	  
	public Frame(int w, int h, int wpadding, int hpadding) {
		width = w;
		height = h;
		wPadding = wpadding;
		hPadding = hpadding;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int hMacro = height/16;
		int wMacro = width/16;
		_macroblocks = new ArrayList<>(hMacro * wMacro);
//
		int x = 0;
		int y = 0;
		
		for (int i = 0; i < hMacro * wMacro; i++) {
			_macroblocks.add(new MacroBlock(x, y));
			x+= 16;
			if (x >= width) {
				x = 0;
				y += 16;
			}
		}
		
		for (int i = 0; i < _macroblocks.size(); i++) {
			MacroBlock block = _macroblocks.get(i);
			if (i - wMacro - 1 >= 0) {
				block.addNeighbour(_macroblocks.get(i-wMacro-1));
			}
			if (i - wMacro >= 0) {
				block.addNeighbour(_macroblocks.get(i-wMacro));
				block.addNeighbour(_macroblocks.get(i-wMacro+1));
			}
			if (i + wMacro < hMacro*wMacro) {
				block.addNeighbour(_macroblocks.get(i+wMacro));
				block.addNeighbour(_macroblocks.get(i+wMacro-1));
			}
			
			if (i + wMacro + 1 < hMacro*wMacro) {
				block.addNeighbour(_macroblocks.get(i+wMacro+1));
			}
			if (i - 1 >= 0)
				block.addNeighbour(_macroblocks.get(i-1));
			if (i + 1 < hMacro*wMacro)
				block.addNeighbour(_macroblocks.get(i+1));
		}
	}
	
	public void setPixel(int x, int y, int pixel) {
		x += wPadding/2;
		y += hPadding/2;
		image.setRGB(x, y, pixel);
		int macroBlockX = x / 16;
		int macroBlockY = y / 16;
		int wMacro = width/16;
		int macroIndex = macroBlockY * wMacro + macroBlockX;
		MacroBlock macroblock = _macroblocks.get(macroIndex);
		macroblock.setPixel(x-macroblock.getX(), y - macroblock.getY(), pixel);
	}
	
	public void setImage(int[] Frame) {
		
	}

	
	public BufferedImage getFrameImage() {
		return image;
	}
	
	public BufferedImage constructImageFromMacroblocks() {
		BufferedImage frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for (MacroBlock block : _macroblocks) {
				BufferedImage blockImage = block.getPixels();
				for (int y = 0; y < 16; y++) {
					for (int x = 0; x < 16; x++) {
						int pixel = blockImage.getRGB(x, y);
						frame.setRGB(block.getX() + x, block.getY() + y, pixel);
					}
			}
		}
		
		return frame;
	}
	
	public int getPixel(int x, int y) {
		return image.getRGB(x, y);
	}
	
	
	
	public ArrayList<MacroBlock> getMacroBlocks() {
		return _macroblocks;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}