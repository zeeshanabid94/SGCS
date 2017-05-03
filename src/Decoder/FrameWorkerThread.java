package Decoder;

public class FrameWorkerThread implements Runnable{
	EncodedFrame _eframe;
	String _frame;
	
	public FrameWorkerThread(String Frame) {
		_frame = Frame;
		_eframe = new EncodedFrame(960, 544);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		_eframe.parseFrame(_frame);
	}
	
	public EncodedFrame getEncodedFrame() {
		return _eframe;
	}
}
