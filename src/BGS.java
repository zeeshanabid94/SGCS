
public class BGS {
	Video _video;
	Frame _IFrame;
	Frame _currentFrame;				// this is current Frame.
	ArrayList<Vector2D> _motionVectors;

	public BGS() {
		_currentFrame = NULL;
		_IFrame = NULL;
		_motionVectors = new ArrayList<>();
	}

	public BGS(Video video) {
		_video = video;
		_IFrame = _video.getFrame(0);
		_currentFrame = _video.getFrame(1);
		_motionVectors = new ArrayList<Vector2D>();
	}

	// public void setPreviousFrame(Frame frame) {
	// 	_currentFrame = frame;
	// }

	// public void setCurrentFrame(Frame frame) {
	// 	_IFrame = frame;
	// }

	public void CalculateMotionVectors() {

		for (int i = 1; i < _video.getTotalFrames(); i++) {			// make getTotalFrames method in Video
			_currentFrame = _video.getFrame(i);
			Frame PFrame = new Frame(_currentFrame.getWidth(), _currentFrame.getHeight())

		}


		// for (Macroblock block: _currentFrame.getMacroblocks()) {
		// 	Macroblock match = NULL;
		// 	int minSad = Integer.MAX_VALUE;

		// 	if (match == NULL)
		// 		match = block;

		// 	for (Macroblock nextBlock : _IFrame.getMacroblocks()) {
		// 		int SAD = CalculateSAD(nextBlock, block);

		// 		minSad = Math.Min(SAD,minSad);
		// 	}

		// 	int motionX = match.getX() - block.getX();
		// 	int motionY = match.getY() - block.getY();

		// 	Vector2D motionVector = new Vector2D(motionX, motionY);

		// 	_motionVectors.add(motionVector);
		// }
	}

	private int CalculateSAD(Macroblock one, Macroblock two) {
		// calculate SAD here
	}
}