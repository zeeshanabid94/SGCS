package Encoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import DCT.DCT;
import Decoder.FileReaderThread;
import ThreadInterface.ThreadInterface;
import file.CompressedFileHeader;

public class Encoder extends ThreadInterface<String> {
	Video _video;
	BGS _bgs;
	DCT _dct;
	File _outputFile;
	
	public Encoder(Video video, String outputFilePath) {
		_video = video;
		_bgs = new BGS(_video);
		_dct = new DCT(0,1,1);
		if (outputFilePath != null)
			_outputFile = new File(outputFilePath);
		else
			_outputFile = null;
	}	
	public void WriteOutputFile() throws IOException {
		 _bgs.CalculateMotionVectors();
		
		FileOutputStream fout = new FileOutputStream(_outputFile);
		CompressedFileHeader header = new CompressedFileHeader(_video.getTotalFrames());
		int currentByteIndex = 0;
		
		for (int i = 0; i < _video.getTotalFrames(); i++) {
			long tNow = System.currentTimeMillis();
			for (MacroBlock block: _video.getFrame(i).getMacroBlocks()) {
				for (int y = 0; y < 16; y+=8) {
					for (int x = 0; x < 16; x+=8) {
						StringBuilder encodedLine = new StringBuilder();
							
						if (block.getType() == MacroBlock.Type.FOREGROUND) {
							encodedLine.append("1 ");
						} else {
							encodedLine.append("0 ");
						}
						

						int[][] redDCTinfo = _dct.forwardDCT(block.getDCTBlock(x,y), "red");
					
						for (int a = 0; a < 8; a++) {
							for (int b = 0; b < 8; b++) {
								encodedLine.append(redDCTinfo[a][b]);
								encodedLine.append(" ");
							}
						}
						
						encodedLine.append("\n");
						
						if (block.getType() == MacroBlock.Type.BACKGROUND) {
							encodedLine.append("1 ");
						} else {
							encodedLine.append("0 ");
						}
						
						
						
						int[][] greenDCTinfo = _dct.forwardDCT(block.getDCTBlock(x,y), "green");

						for (int a = 0; a < 8; a++) {
							for (int b = 0; b < 8; b++) {
								encodedLine.append(greenDCTinfo[a][b]);
								encodedLine.append(" ") ;
							}
						}
						encodedLine.append("\n");
						
						if (block.getType() == MacroBlock.Type.BACKGROUND) {
							encodedLine.append("1 ");
						} else {
							encodedLine.append("0 ");
						}
						
						int[][] blueDCTinfo = _dct.forwardDCT(block.getDCTBlock(x,y), "blue");
						
						for (int a = 0; a < 8; a++) {
							for (int b = 0; b < 8; b++) {
								encodedLine.append(blueDCTinfo[a][b]);
								encodedLine.append(" ");
							}
						}
						
						encodedLine.append("\n");
						currentByteIndex += encodedLine.toString().getBytes().length;
						fout.write(encodedLine.toString().getBytes());
					}
				}
				fout.write("/".getBytes());
				currentByteIndex += 1;
			}
			long tLater = System.currentTimeMillis();
			System.out.println("Frame " + i+ " compressed in " + (tLater - tNow) + " ms");
			fout.write("&".getBytes());
			currentByteIndex += 1;
			header.addByteIndex(currentByteIndex);
		}
		
		fout.close();
		header.createHeaderFile("/Users/shane/Desktop/HeaderFile.hdr");
		header.writeHeaderFile();
	}

	
	public String EncodeFrame(Frame frame) {

		StringBuilder encodedLine = new StringBuilder();
		for (MacroBlock block: frame.getMacroBlocks()) {
			for (int y = 0; y < 16; y+=8) {
				for (int x = 0; x < 16; x+=8) {
						
					if (block.getType() == MacroBlock.Type.FOREGROUND) {
						encodedLine.append("1 ");
					} else {
						encodedLine.append("0 ");
					}
					

					int[][] redDCTinfo = _dct.forwardDCT(block.getDCTBlock(x,y), "red");
				
					for (int a = 0; a < 8; a++) {
						for (int b = 0; b < 8; b++) {
							encodedLine.append(redDCTinfo[a][b]);
							encodedLine.append(" ");
						}
					}
					
					encodedLine.append("\n");
					
					if (block.getType() == MacroBlock.Type.BACKGROUND) {
						encodedLine.append("1 ");
					} else {
						encodedLine.append("0 ");
					}
					
					
					
					int[][] greenDCTinfo = _dct.forwardDCT(block.getDCTBlock(x,y), "green");

					for (int a = 0; a < 8; a++) {
						for (int b = 0; b < 8; b++) {
							encodedLine.append(greenDCTinfo[a][b]);
							encodedLine.append(" ") ;
						}
					}
					encodedLine.append("\n");
					
					if (block.getType() == MacroBlock.Type.BACKGROUND) {
						encodedLine.append("1 ");
					} else {
						encodedLine.append("0 ");
					}
					
					int[][] blueDCTinfo = _dct.forwardDCT(block.getDCTBlock(x,y), "blue");
					
					for (int a = 0; a < 8; a++) {
						for (int b = 0; b < 8; b++) {
							encodedLine.append(blueDCTinfo[a][b]);
							encodedLine.append(" ");
						}
					}
					
					encodedLine.append("\n");
				}
			}
		}
		return encodedLine.toString();
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		_running = true;
		
		while (_running == true && _video.hasNext()) {
			Frame frame = _video.getNextFrame();
			_items.add(EncodeFrame(frame));
		}
	}
	
	public void reset() {
		_video.resetFrames();
	}
}