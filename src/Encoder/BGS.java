package Encoder;

import java.awt.Color;
import java.util.ArrayList;

public class BGS {
	Video _video;
	ArrayList<Vector2D> _motionVectors;
	int _k;						// search parameter k with default value 3

	public BGS() {
		_motionVectors = new ArrayList<>();
	}

	public BGS(Video video) {
		_video = video;
		_motionVectors = new ArrayList<Vector2D>();
		_k = 4;
	}

	public void setK(int K) {
		_k = K;
	}
	
	public void CalculateMotionVectors() {

		for (int i = 0; i < _video.getTotalFrames()-1; i++) {		// change value back when presenting
			System.out.println("Calculating motion vectors for frame " + i);
			double avg = 0;
			double avgY = 0;
			double avgX = 0;
			Vector2D largestVector = new Vector2D(0,0);
			int nonzeromv = 0;
			Frame currentFrame = _video.getFrame(i);
			Frame nextFrame = _video.getFrame(i+1);

			for (MacroBlock block: currentFrame.getMacroBlocks()) {
				int minSad = Integer.MAX_VALUE;
				int minSadX = 0;
				int minSadY = 0;
				int currentK = _k;
				int searchX = block.getX();
				int searchY = block.getY();
				while (currentK > 1) {
					for (int dx = currentK; dx >= -1*currentK; dx-=currentK) {
						for (int dy = currentK; dy >= -1*currentK; dy-=currentK) {
							int x = searchX + dx;
							int y = searchY + dy;
							
							if (x < 0)
								continue;
							if (y < 0)
								continue;
							if (x + 16 > currentFrame.getWidth())
								continue;
							if (y + 16 > currentFrame.getHeight())
								continue;
							int Sad = CalculateSAD(block, nextFrame, x, y);
							
							if (Sad < minSad) {
								minSad = Sad;
								minSadX = x;
								minSadY = y;
							}
							
						}
					}

					searchX = minSadX;
					searchY = minSadY;
					currentK/=2;
						
				}
				
				Vector2D motionVector = new Vector2D(minSadX-block.getX(), minSadY - block.getY());
				block.setMotionVector(motionVector);

				avg += motionVector.length();
				avgX += motionVector.getX();
				avgY += motionVector.getY();
			}

			avg/=(double)2040;
			avgX/=(double)2040;
			avgY/=(double)2040;
			for (MacroBlock block : currentFrame.getMacroBlocks()) {
				if (block._motionVector.length() >= avg && block._motionVector.length() > 1) {
					nonzeromv += 1;
					if (largestVector.length() < block._motionVector.length()) {
						largestVector = block._motionVector;
					}
				}
			}
			for (MacroBlock block: currentFrame.getMacroBlocks()) {
				if (nonzeromv > currentFrame.getMacroBlocks().size()/2) {
					block._motionVector.add(new Vector2D(-1*avgX, -1*avgY));
				}
//				block.setThreshold(Math.abs(avgX), Math.abs(avgY));
				block.setThreshold(avg*1.5);
				block.isBackGround();
			}
			
			

			
			

		}
	}
	
	public int CalculateSAD(MacroBlock block, Frame nextFrame, int x, int y) {
		int Sad = 0;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				Color currentRGB = new Color(block.getPixel(j, i));
				Color newRGB = new Color(nextFrame.getPixel(x+j, y+i));
				double currentY = 0.299*currentRGB.getRed() + 0.587*currentRGB.getGreen() + 0.114 *currentRGB.getBlue();
				double newY = 0.299*newRGB.getRed() + 0.587*newRGB.getGreen() + 0.114 *newRGB.getBlue();
				Sad += Math.abs(currentY - newY);
			}
		}
		
		return Sad;
	}

}