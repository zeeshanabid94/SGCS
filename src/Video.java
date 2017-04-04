import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Video {
	  static int WIDTH = 960;
	  static int HEIGHT = 540;
	  int totalFrames;
	  Frame[] frames;
	  int _currentFrame;
	  
	  public Video(String filename) {
		  File inputFile = new File(filename);
		  long inputLength = inputFile.length();
		  totalFrames = (int) inputLength/(WIDTH*HEIGHT*3);
		  frames = new Frame[(int) totalFrames];
			  for(int frameIndex = 0; frameIndex < totalFrames; frameIndex++){
				  int index = 3*WIDTH*HEIGHT*frameIndex;
				  byte[] Frame = ReadFrameFromFile(inputFile, index, 3*HEIGHT*WIDTH);
				  if (Frame == null)
					  break;
				  Frame singleFrame = new Frame(WIDTH, HEIGHT);
				  for (int i = 0; i < WIDTH*HEIGHT;i++) {
							byte red = Frame[i];
							byte green = Frame[i+HEIGHT*WIDTH];
							byte blue = Frame[i+HEIGHT*WIDTH*2]; 
							singleFrame.imageBytes[i] = red & 0xff;
							singleFrame.imageBytes[i+HEIGHT*WIDTH] = green & 0xff;
							singleFrame.imageBytes[i+HEIGHT*WIDTH*2] = blue & 0xff;
							index++;
				  }
				  frames[frameIndex] = singleFrame;
			  }
		  _currentFrame = 0;
	  }
	  
	  public Frame getFrame(int frameNumber) {
		  return frames[frameNumber];
	  }
	  
		public byte[] ReadFrameFromFile(File file, int skip, int length) {
			byte[] bytes = null;
			try {
				//file only contains RGB no alpha
				InputStream is = new FileInputStream(file);
				bytes = new byte[length];

				//read the whole file into  to temp buffer called bytes
				int numRead = 0;
				is.skip(skip);
				numRead = is.read(bytes);

				is.close();
				
				if (numRead == 0)
					return null;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bytes;
		}
		
		public Frame getNextFrame() {
			_currentFrame += 1;
			if (_currentFrame >= 363)
				_currentFrame = 1;
			return frames[_currentFrame-1];
		}
		public static void main(String[] args) {
			String testFile = "/Users/student/Desktop/Final/oneperson_960_540.rgb";
			Video testVideo = new Video(testFile);
			System.out.println("Done");
		}


		
  }