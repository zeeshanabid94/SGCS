
public class EncodedFrame extends Frame {
	// Each Frame gets 24480 lines
	public EncodedFrame(String input, int w, int h) {
		super(w,h,0,0);
		String[] lines = input.split("\n");
		int x = 0, y = 0;
		int mNo = 0;
		for (int i = 0; i < lines.length; i+=12) {
			String macroblock = "";
			for (int j = 0; j < 12; j++) {
				macroblock += lines[i+j] + "\n";
			}
			
			EncodedMacroBlock eMacro = new EncodedMacroBlock(macroblock, x, y);
			
			this._macroblocks.add(eMacro);
			
			x+=16;
			
			if (x >= w) {
				x=0;
				y+=16;
			}
			System.out.println(mNo);
			mNo++;
		}
	}
}
