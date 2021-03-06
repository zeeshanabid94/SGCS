import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Video {
	  int WIDTH;
	  int HEIGHT;
	  int totalFrames;
	  Frame[] frames;
	  int _currentFrame;
	  
	  public Video(String filename, int width, int height) {
		  if(width > 0 && height > 0) {
			  WIDTH = width;
			  HEIGHT = height;
		  } else {
			  WIDTH = 960;
			  HEIGHT = 540;
		  }
		  File inputFile = new File(filename);
		  long inputLength = inputFile.length();
		  totalFrames = (int) inputLength/(WIDTH*HEIGHT*3);
		  frames = new Frame[(int) totalFrames];
		  
		  // create an empty frame of size whatever we need with padding
		  int widthPadding = (16 - (WIDTH - (WIDTH / MacroBlock.SIZE) * 16)) % 16;
		  int heightPadding =(16 - (HEIGHT - (HEIGHT/ MacroBlock.SIZE)* 16)) % 16;
		  
		  int unpaddedWidth = WIDTH;
		  int unpaddedHeight = HEIGHT;
		  
		  WIDTH += widthPadding;
		  HEIGHT += heightPadding;
		  
		  
		  // read a frame from file including rgb
		  
		  for(int frameIndex = 0; frameIndex < totalFrames; frameIndex++){
			  int index = 3*unpaddedWidth*unpaddedHeight*frameIndex;
			  byte[] readFrame = ReadFrameFromFile(inputFile, index, 3*unpaddedWidth*unpaddedHeight);
			  if (readFrame == null)
				  break;
			   
			  Frame paddedFrame = new Frame(WIDTH, HEIGHT, widthPadding, heightPadding);
			  
			  for (int i = 0; i < unpaddedWidth*unpaddedHeight;i++) {
					byte red = readFrame[i];
					byte green = readFrame[i+unpaddedHeight*unpaddedWidth];
					byte blue = readFrame[i+unpaddedHeight*unpaddedWidth*2];
					int pixel = 0x00000000 | ((red & 0xff) << 16) | ((green & 0xff) << 8) | (blue & 0xff);
					paddedFrame.setPixel(i%WIDTH, i/WIDTH, pixel);
					index++;
		  	}
			  frames[frameIndex] = paddedFrame;
			  
		  }
		  
		  // copy over the frame into the empty frame
		  // save the frame
		  
//			  for(int frameIndex = 0; frameIndex < totalFrames; frameIndex++){
//				  int index = 3*WIDTH*HEIGHT*frameIndex;
//				  byte[] Frame = ReadFrameFromFile(inputFile, index, 3*HEIGHT*WIDTH);
//				  if (Frame == null)
//					  break;
//				  int widthPadding = 16 - (WIDTH % MacroBlock.SIZE);
//				  int heightPadding = 16 - (HEIGHT % MacroBlock.SIZE);
//				  Frame singleFrame = new Frame(WIDTH+widthPadding, HEIGHT+heightPadding);
//				  int widthPaddingLeft = widthPadding/2;
//				  int widthPaddingRight = widthPadding - widthPaddingLeft;
//				  int heightPaddingTop = heightPadding/2;
//				  int heightPaddingBottom = heightPadding - heightPaddingTop;
//				  int lastPositionTopPadding = 0;
//				  while(heightPaddingTop != 0) {
//					  for(int paddedPxls = 0; paddedPxls < singleFrame.width; paddedPxls++){
//						  singleFrame.imageBytes[lastPositionTopPadding] =0;
//						  singleFrame.imageBytes[lastPositionTopPadding+singleFrame.height*singleFrame.width] =0;
//						  singleFrame.imageBytes[lastPositionTopPadding+singleFrame.height*singleFrame.width*2] =0;  
//						  lastPositionTopPadding++;
//					  }
//					  heightPaddingTop--;
//				  }
//				  while(widthPaddingLeft !=0) {
//					  for(int paddedPxls = 0; paddedPxls < singleFrame.width; paddedPxls++){
//						  singleFrame.imageBytes[lastPositionTopPadding] =0;
//						  singleFrame.imageBytes[lastPositionTopPadding+singleFrame.height*singleFrame.width] =0;
//						  singleFrame.imageBytes[lastPositionTopPadding+singleFrame.height*singleFrame.width*2] =0;  
//						  lastPositionTopPadding++;
//					  }
//					  widthPaddingLeft--;
//				  }
//				  }
//				  for (int i = 0; i < WIDTH*HEIGHT;i++) {
//							byte red = Frame[i];
//							byte green = Frame[i+HEIGHT*WIDTH];
//							byte blue = Frame[i+HEIGHT*WIDTH*2]; 
//							singleFrame.imageBytes[i] = red & 0xff;
//							singleFrame.imageBytes[i+HEIGHT*WIDTH] = green & 0xff;
//							singleFrame.imageBytes[i+HEIGHT*WIDTH*2] = blue & 0xff;
//							index++;
//				  }
//				  frames[frameIndex] = singleFrame;
//			  }
//		  _currentFrame = 0;
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
			int width = 960;
			int height = 540;
			Video testVideo = new Video(testFile, width, height);
			System.out.println("Done");
		}


		
  }