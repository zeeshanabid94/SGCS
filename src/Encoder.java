import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Encoder {
	Video _video;
	BGS _bgs;
	DCT _dct;
	File _outputFile;
	
	public Encoder(Video video, String outputFilePath) {
		_video = video;
		_bgs = new BGS(_video);
		_dct = new DCT(0,1,1);
		_outputFile = new File(outputFilePath);
	}
	
	public void WriteOutputFile() throws IOException {
		_bgs.CalculateMotionVectors();
		
		FileOutputStream fout = new FileOutputStream(_outputFile);
		
		for (int i = 0; i < _video.getTotalFrames(); i++) {
			for (MacroBlock block: _video.getFrame(i).getMacroBlocks()) {
				for (int y = 0; y < 16; y+=8) {
					for (int x = 0; x < 16; x+=8) {
						String encodedLine = "";
							
						if (block.getType() == MacroBlock.Type.FOREGROUND) {
							encodedLine += "1 ";
						} else {
							encodedLine += "0 ";
						}
						

						int[][] redDCTinfo = _dct.forwardDCT(block.getDCTBlock(x,y), "red");
					
						for (int a = 0; a < 8; a++) {
							for (int b = 0; b < 8; b++) {
								encodedLine += redDCTinfo[a][b] + " ";
							}
						}
						
						encodedLine+= "\n";
						
						if (block.getType() == MacroBlock.Type.BACKGROUND) {
							encodedLine += "1 ";
						} else {
							encodedLine += "0 ";
						}
						
						
						
						int[][] greenDCTinfo = _dct.forwardDCT(block.getDCTBlock(x,y), "green");

						for (int a = 0; a < 8; a++) {
							for (int b = 0; b < 8; b++) {
								encodedLine += greenDCTinfo[a][b] + " ";
							}
						}
						encodedLine+= "\n";
						
						if (block.getType() == MacroBlock.Type.BACKGROUND) {
							encodedLine += "1 ";
						} else {
							encodedLine += "0 ";
						}
						
						int[][] blueDCTinfo = _dct.forwardDCT(block.getDCTBlock(x,y), "blue");
						
						for (int a = 0; a < 8; a++) {
							for (int b = 0; b < 8; b++) {
								encodedLine += blueDCTinfo[a][b] + " ";
							}
						}
						
						encodedLine += "\n";
						
						fout.write(encodedLine.getBytes(), 0, encodedLine.getBytes().length);
						
					}
				}
				fout.write("&\n".getBytes());
			}
			System.out.println("Frame " + i+ " compressed");
		}
		
		fout.close();
	}
}
