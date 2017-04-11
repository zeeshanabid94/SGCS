
public class BGS {
	Frame _previousFrame;
	Frame _currentFrame;
	ArrayList<Vector2D> _motionVectors;

	public BGS() {
		_previousFrame = NULL;
		_currentFrame = NULL;
		_motionVectors = new ArrayList<>();
	}

	public void setPreviousFrame(Frame frame) {
		_previousFrame = frame;
	}

	public void setCurrentFrame(Frame frame) {
		_currentFrame = frame;
	}

	public void CalculateMotionVectors() {

		for (Macroblock block: _previousFrame.getMacroblocks()) {
			Macroblock match = NULL;
			int minSad = Integer.MAX_VALUE;

			if (match == NULL)
				match = block;

			for (Macroblock nextBlock : _currentFrame.getMacroblocks()) {
				int SAD = CalculateSAD(nextBlock, block);

				minSad = Math.Min(SAD,minSad);
			}

			int motionX = match.getX() - block.getX();
			int motionY = match.getY() - block.getY();

			Vector2D motionVector = new Vector2D(motionX, motionY);

			_motionVectors.add(motionVector);
		}
	}

	private int CalculateSAD(Macroblock one, Macroblock two) {
		// calculate SAD here
	}
}