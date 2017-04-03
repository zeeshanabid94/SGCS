import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TestMethods {
	
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

	public void showIms(String filename, int width, int height, int framePerSec){
		File file = new File(filename);
		//TYPE_INT_RGB Represents an image with 8-bit RGB color components packed into integer pixels 
		BufferedImage img = null;
		long len = file.length(); 
		long totalFrames = len/(width*height*3);
		long sleepTime = 1000/framePerSec;
		byte[] bytes = null;
		sleepTime = 1000/framePerSec;
		long startTime = 0; //used to find compute time
		long computeTime = 0;

		bytes = RGBFile2Bytes(file, width, height);
	//	BufferedImage[] allFrames = bytes2IMG(width, height, totalFrames, bytes);

		// Use labels to display the images
		JFrame frame = new JFrame();
		//when click x button frame closes
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//GridBagLayout places components in a grid of rows and columns
		GridBagLayout gLayout = new GridBagLayout();
		frame.getContentPane().setLayout(gLayout);
		String result = String.format("Video height: %d, width: %d", height, width);
		JLabel lbText1 = new JLabel(result);
		lbText1.setHorizontalAlignment(SwingConstants.CENTER);
		JLabel lbIm1 = new JLabel();
		
		GridBagConstraints c = new GridBagConstraints();
		//Stretches frame horizontally
		c.fill = GridBagConstraints.HORIZONTAL; //Resize the component horizontally but not vertically
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 0.5; //Specifies how to distribute extra horizontal space
		c.gridx = 0;
		c.gridy = 0;
		frame.getContentPane().add(lbText1, c);
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);		
	
		for(int frameIndex = 0; frameIndex < totalFrames; frameIndex++){
				//ind contains where frameNumber is located in bytes array	
			int ind = width*height*frameIndex*3;
			startTime = System.currentTimeMillis();
			for(int y = 0; y < height; y++){
				for(int x = 0; x < width; x++){			
					byte a = 0;
					byte r = bytes[ind];
					byte g = bytes[ind+height*width];
					byte b = bytes[ind+height*width*2]; 
					int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
					//int pix = ((a << 24) + (r << 16) + (g << 8) + b); bit shifting
					img.setRGB(x,y,pix);
					ind++;
				}
			}

			computeTime = System.currentTimeMillis() - startTime;
			lbIm1.setIcon(new ImageIcon(img));
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			c.gridy = 1;
			frame.getContentPane().add(lbIm1, c);
				
			frame.pack();

			frame.setVisible(true);
						
			try {
				Thread.sleep(sleepTime - computeTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				

			img.flush();
			if (frameIndex + 1 >= totalFrames) {
				frameIndex = 0;
			}
		}
	}
	public static void main(String[] args) {
		String testFile = "/Users/shane/Documents/workspace/MyCompression/images/image1.rgb";
		Video testVideo = new Video(testFile);
		System.out.println("Done");
	}
	
	
}
