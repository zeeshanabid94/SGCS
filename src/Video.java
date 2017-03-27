import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Video {
	  static int WIDTH = 960;
	  static int HEIGHT = 540;
	  long totalFrames;
	  Frame[] frames;
	  public Video(String filename) {
		  File inputFile = new File(filename);
		  long inputLength = inputFile.length();
		  totalFrames = inputLength/(WIDTH*HEIGHT*3);
		  frames = new Frame[(int) totalFrames];
		  byte[] videoAsBytes = RGBFile2Bytes(inputFile, WIDTH, HEIGHT);
		  for(int frameIndex = 0; frameIndex < totalFrames; frameIndex++){
			  int index = WIDTH*HEIGHT*frameIndex;
			  Frame singleFrame = new Frame(WIDTH, HEIGHT);
			  for(int row = 1; row <= HEIGHT; row++){
				  for(int col = 1; col <= WIDTH; col++){ 
						byte red = videoAsBytes[index];
						byte green = videoAsBytes[index+HEIGHT*WIDTH];
						byte blue = videoAsBytes[index+HEIGHT*WIDTH*2]; 
						singleFrame.imageBytes[index] = red & 0xff;
						singleFrame.imageBytes[index+HEIGHT*WIDTH] = green & 0xff;
						singleFrame.imageBytes[index+HEIGHT*WIDTH*2] = blue & 0xff;
				  }
			  }
			  frames[frameIndex] = singleFrame;
		  }
	  }
	  public Frame getFrame(int frameNumber) {
		  return frames[frameNumber];
	  }
	  

		public byte[] RGBFile2Bytes(File file, int width, int height) {
			byte[] bytes = null;
			try {
				//file only contains RGB no alpha
				InputStream is = new FileInputStream(file);
				long len = file.length();
				bytes = new byte[(int)len];

				//read the whole file into  to temp buffer called bytes
				int offset = 0;
				int numRead = 0;
				while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
					//is(byte[], off, len) reads up to len bytes from is, attempt to read len bytes but smaller amount may be read
					//return number of bytes read as int, offset tells b[off] through b[off+k-1] where k is amount read
					offset += numRead;
				}
				is.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bytes;
		}
		public static void main(String[] args) {
			String testFile = "/Users/shane/Documents/workspace/MyCompression/images/image1.rgb";
			Video testVideo = new Video(testFile);
			System.out.println("Done");
		}

  }