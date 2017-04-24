import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
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
	boolean _play;
	
	
	public Player() {
		_play = false;
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

		JButton play = new JButton("Play");
		GridBagConstraints cPlay = new GridBagConstraints();
		cPlay.fill = GridBagConstraints.HORIZONTAL;
		cPlay.gridx = 0;       
		cPlay.gridy = 0;       
		bottomPanel.add(play, cPlay);
		
		JButton pause = new JButton("Pause");
		GridBagConstraints cPause = new GridBagConstraints();
		cPause.fill = GridBagConstraints.HORIZONTAL;
		cPause.gridx = 1; 
		cPause.gridy = 0;
		bottomPanel.add(pause, cPause);
		
		JButton stop = new JButton("Stop");
		GridBagConstraints cStop = new GridBagConstraints();
		cStop.fill = GridBagConstraints.HORIZONTAL;
		cStop.gridx = 2; 
		cStop.gridy = 0;
		bottomPanel.add(stop, cStop);

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
		Encoder encoded = new Encoder(_video, "");
//		encoded.WriteOutputFile();
//		FrameBuffer buffer = new FrameBuffer(30);
//		Decoder decoded = new Decoder(fileoutpath, buffer);
//		Thread decoderThread = new Thread(decoded);
//		decoderThread.start();
//		decoded.DecodeFrames();
//		_video = decoded.getVideo();
//		BGS BFSeparation = new BGS(_video);
//		BFSeparation.CalculateMotionVectors();

//		String fileoutpath = "C:/Users/zabid/Desktop/compressed.cmp";
//		Encoder encoded = new Encoder(_video, fileoutpath);
//		encoded.WriteOutputFile();
		
//		BGS BFSeparation = new BGS(_video);
//		BFSeparation.CalculateMotionVectors();

//		TimeUnit.MILLISECONDS.sleep(1000);
		_play = true;
		while(_play == true) {
			Frame newFrame = _video.getNextFrame();
			_videoWindow.setIcon(new ImageIcon(newFrame.getFrameImage()));
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
