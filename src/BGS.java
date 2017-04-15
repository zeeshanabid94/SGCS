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
		_k = 3;
	}

	public void setK(int K) {
		_k = K;
	}
	
	public void CalculateMotionVectors() {

		for (int i = 0; i < _video.getTotalFrames()-1; i++) {		// make getTotalFrames method in Video
			System.out.println("Calculating motion vectors for frame " + i);
			double avg = 0;
			Frame currentFrame = _video.getFrame(i);
			Frame nextFrame = _video.getFrame(i+1);
			
			for (MacroBlock block: currentFrame.getMacroBlocks()) {
				int minSad = Integer.MAX_VALUE;
				int minSadX = 0;
				int minSadY = 0;
				for(int dx = _k; dx >= -1*_k; dx--) {
					for (int dy = _k; dy >= -1*_k; dy--) {
						int x = block.getX() + dx;
						int y = block.getY() + dy;
						
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
				
				Vector2D motionVector = new Vector2D(minSadX-block.getX(), minSadY - block.getY());
				block.setMotionVector(motionVector);
				avg += motionVector.length();
			}
			avg/=(double)currentFrame.getMacroBlocks().size();
			for (MacroBlock block: currentFrame.getMacroBlocks()) {
				block.setThreshold(avg*15);

				block.isBackGround();
			}
			
			System.out.println(avg*15);
			

			
			

		}
	}
	
	public int CalculateSAD(MacroBlock block, Frame nextFrame, int x, int y) {
		int Sad = 0;
		
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				Color currentRGB = new Color(block.getPixel(j, i));
				Color newRGB = new Color(nextFrame.getPixel(x+j, y+i));
				
				double currentIntensity = Math.sqrt(Math.pow(currentRGB.getRed(), 2) + Math.pow(currentRGB.getGreen(), 2) +
						Math.pow(currentRGB.getGreen(), 2));
				double newIntensity = Math.sqrt(Math.pow(newRGB.getRed(), 2) + Math.pow(newRGB.getGreen(), 2) +
						Math.pow(newRGB.getGreen(), 2));
//				Sad += Math.abs(currentRGB.getRed() - newRGB.getRed()) + Math.abs(currentRGB.getBlue() - newRGB.getBlue())
//						+ Math.abs(currentRGB.getGreen() - newRGB.getGreen());
				Sad += Math.abs(currentIntensity - newIntensity);
			}
		}
		
		return Sad;
	}

}