
public class EncodedFrame extends Frame {
	// Each Frame gets 24480 lines
	int _x = 0;
	int _y = 0;
	public EncodedFrame(int w, int h) {
		super(w,h,0,0);
		_x = 0;
		_y = 0;
	}
	
	public void addMacroBlock(StringBuilder macroblock, DCT iDCT) {

		EncodedMacroBlock eMacro = new EncodedMacroBlock(macroblock.toString(), _x, _y, iDCT);
		eMacro.decodeMacroBlock();
		this._macroblocks.add(eMacro);
		
		_x+=16;
		
		if (_x >= width) {
			_x=0;
			_y+=16;
		}
	}
}
