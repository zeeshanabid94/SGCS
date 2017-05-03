package Player;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.LayoutStyle;

import Decoder.Decoder;
import Decoder.EncodedFrame;
import Encoder.BGS;
import Encoder.Encoder;
import Encoder.Frame;
import Encoder.Video;

import java.awt.*;


public class Player {
	
	Video _video;
	JFrame _mainWindow;
	JLabel _videoWindow;
	boolean _play,_pause,_stop;
	int _mouseX, _mouseY;
	Decoder _decoder;
	int fn, bn;
	
	
	
	public Player(String file, int n1, int n2) throws FileNotFoundException {
		fn = n1;
		bn = n2;
		_decoder = new Decoder(file);
		_play = false;
		_pause = false;
		_stop = false;
		_mainWindow = new JFrame("Video Player");
		_mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_mainWindow.setSize(960, 605);
		JSplitPane splitPane = new JSplitPane();;
        JPanel topPanel = new JPanel();         // our top component
        JPanel bottomPanel = new JPanel();      // our bottom component
        splitPane.setBorder(null);
        _mainWindow.getContentPane().add(splitPane);
		
		JPanel panel2 = new JPanel(new GridBagLayout());
		 splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);  // we want it to split the window verticaly
		 splitPane.setDividerLocation(540);                    // the initial position of the divider is 200 (our window is 540 pixels high)
		 splitPane.setTopComponent(topPanel);                  // at the top we want our "topPanel"
		 splitPane.setBottomComponent(bottomPanel);            // and at the bottom we want our "bottomPanel"
		 

//		 Thread controls = new Thread(new RunningThread());
		 
		JButton play = new JButton("Play");
		GridBagConstraints cPlay = new GridBagConstraints();
		cPlay.fill = GridBagConstraints.HORIZONTAL;
		cPlay.gridx = 0;       
		cPlay.gridy = 0;       
		bottomPanel.add(play, cPlay);
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)   {
				_play = true;
				_pause = false;
				_stop = false;
				System.out.println("Play true");
				_decoder.startThreads();
			  }
			});
		
		JButton pause = new JButton("Pause");
		GridBagConstraints cPause = new GridBagConstraints();
		cPause.fill = GridBagConstraints.HORIZONTAL;
		cPause.gridx = 1; 
		cPause.gridy = 0;
		bottomPanel.add(pause, cPause);
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)   {
				_pause = true;
				_stop = false;
				_play = false;
				
				System.out.println("Pause true");
			  }
			});
		
		JButton stop = new JButton("Stop");
		GridBagConstraints cStop = new GridBagConstraints();
		cStop.fill = GridBagConstraints.HORIZONTAL;
		cStop.gridx = 2; 
		cStop.gridy = 0;
		bottomPanel.add(stop, cStop);
		//when stop button is clicked set stop to true
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)   {
			  _stop = true;
			  _play = false;
			  _pause = false;
			  System.out.println("Stop true");
			  }
			});

		GridBagConstraints cVideo = new GridBagConstraints();
		_videoWindow = new JLabel();
		
		_videoWindow.addMouseMotionListener(new MouseMotionListener() {			// This is the mouse motion listener
																				// which records the values of x and y
			@Override															// whenever the mouse moves and stores
			public void mouseMoved(MouseEvent e) {								// then in variables _mouseX and _mouseY
				// TODO Auto-generated method stub
				_mouseX = e.getX();
				_mouseY = e.getY();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		cVideo.fill = GridBagConstraints.HORIZONTAL;

		topPanel.add(_videoWindow, cVideo);
		
		
		_mainWindow.setVisible(true);
	}
	
	public void setVideo(Video video) {
		_video = video;
	}
	
	public void Play() throws Exception {
//		if (_video == null) {
//			throw new Exception("No video found");
//		}
//		String fileoutpath = "E:/finaltest_200frames.cmp";
//		String fileoutpath = "/Users/shane/Desktop/finaltest_video3.cmp";
//		Encoder encoded = new Encoder(_video, fileoutpath);
//		encoded.WriteOutputFile(); }
			
//		FrameBuffer buffer = new FrameBuffer(30);
//		_decoder = new Decoder(fileoutpath);
		_decoder.startThreads();
//		Thread decoderThread = new Thread(decoded);
//		decoderThread.start();
//		decoded.DecodeFrames();
//		_video = decoded.getVideo();
//		String fileoutpath = "C:/Users/zabid/Desktop/compressed.cmp";
//		Encoder encoded = new Encoder(_video, fileoutpath);
//		encoded.WriteOutputFile();
		
//		BGS BFSeparation = new BGS(_video);
//		BFSeparation.CalculateMotionVectors();

//		
		
		_play = true;
		int frames = 0;
		int secs = 0;
		while(true) {
			long tNow = System.currentTimeMillis();
			if (_play == true) {
				EncodedFrame newFrame = _decoder.getNextFrame();
				_videoWindow.setIcon(new ImageIcon(newFrame.getImage(_mouseX, _mouseY, 128, 16, 256)));
				frames ++;
			}
			if(_stop == true) {
				_decoder.reset();
				_stop = false;
			}
			if (_pause == true) {
				EncodedFrame newFrame = _decoder.getCurrentFrame();
				_videoWindow.setIcon(new ImageIcon(newFrame.getImage(_mouseX, _mouseY, 128, 16, 256)));
			}
//			TimeUnit.MILLISECONDS.sleep(33); //sleep so thread can see updated values
			long tLater = System.currentTimeMillis();
			System.out.println("Time between frames "  + (tLater - tNow));
			secs += (tLater - tNow);
			if (secs > 1000) {
				System.out.println("FPS: " + ((double) frames)/((double) secs) * 1000);
				secs = 0;
				frames = 0;
			}
		}
	}
	
	public static void main(String args[]) throws Exception {

		//"/Users/shane/Documents/workspace/SGCS_570_Final_Proj/Final/oneperson_960_540.rgb"
		String file = args[0];
		int n1 = Integer.parseInt(args[1]);
		int n2 = Integer.parseInt(args[2]);
		Player player = new Player(file, n1, n2);
//		Video video = new Video(file, width, height);
//		player.setVideo(video);
		player.Play();
	}

	public int getMouseX() {
		// TODO Auto-generated method stub
		return _mouseX;
	}

	public int getMouseY() {
		// TODO Auto-generated method stub
		return _mouseY;
	}

	public void viewFrame(BufferedImage image) {
		// TODO Auto-generated method stub
		_videoWindow.setIcon(new ImageIcon(image));
	}

}
