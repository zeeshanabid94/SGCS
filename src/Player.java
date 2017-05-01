import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.LayoutStyle;
import java.awt.*;


public class Player {
	
	Video _video;
	JFrame _mainWindow;
	JLabel _videoWindow;
	boolean _play,_pause,_stop;
	
	
	public Player() {
		_play = false;
		_pause = false;
		_stop = false;
		_mainWindow = new JFrame("Video Player");
		_mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_mainWindow.setSize(960, 700);
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
			  }
			});

		GridBagConstraints cVideo = new GridBagConstraints();
		_videoWindow = new JLabel();
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
//		String fileoutpath = "/Users/shane/Desktop/compressed.cmp";
//		Encoder encoded = new Encoder(_video, fileoutpath);
//		encoded.WriteOutputFile();
//		FrameBuffer buffer = new FrameBuffer(30);
//		Decoder decoded = new Decoder(fileoutpath, buffer);
//		Thread decoderThread = new Thread(decoded);
//		decoderThread.start();
//		decoded.DecodeFrames();
//		_video = decoded.getVideo();
		BGS BFSeparation = new BGS(_video);
		BFSeparation.CalculateMotionVectors();

//		String fileoutpath = "C:/Users/zabid/Desktop/compressed.cmp";
//		Encoder encoded = new Encoder(_video, fileoutpath);
//		encoded.WriteOutputFile();
		
//		BGS BFSeparation = new BGS(_video);
//		BFSeparation.CalculateMotionVectors();

//		
		
		_play = true;
		while(true) {
			while(_play == true) {
				TimeUnit.MILLISECONDS.sleep(33);
				Frame newFrame = _video.getNextFrame();
				_videoWindow.setIcon(new ImageIcon(newFrame.constructImageFromMacroblocks()));
			}
			if(_stop == true) {
				_video.resetFrames();
			}
			TimeUnit.MILLISECONDS.sleep(1); //sleep so thread can see updated values
		}
	}
	
	public static void main(String args[]) throws Exception {
		Player player = new Player();
		//"/Users/shane/Documents/workspace/SGCS_570_Final_Proj/Final/oneperson_960_540.rgb"
		String file = args[0];
		int width, height;
		width = 960;
		height = 540;
		if(args.length > 1) {
			width = Integer.parseInt(args[1]);
			height = Integer.parseInt(args[2]);
		}
		Video video = new Video(file, width, height);
		player.setVideo(video);
		player.Play();
	}

}
