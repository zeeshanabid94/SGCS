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
		_mainWindow.setSize(960, 600);
		JSplitPane splitPane = new JSplitPane();;
        JPanel topPanel = new JPanel();         // our top component
        JPanel bottomPanel = new JPanel();      // our bottom component
        _mainWindow.getContentPane().add(splitPane);
		GridBagConstraints c = new GridBagConstraints();
		JPanel panel2 = new JPanel(new GridBagLayout());
		 splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);  // we want it to split the window verticaly
		 splitPane.setDividerLocation(540);                    // the initial position of the divider is 200 (our window is 400 pixels high)
		 splitPane.setTopComponent(topPanel);                  // at the top we want our "topPanel"
		 splitPane.setBottomComponent(bottomPanel);            // and at the bottom we want our "bottomPanel"

		JButton play = new JButton("Play");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
//		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 2;       //third row
		bottomPanel.add(play, c);
////		
////		c = new GridBagConstraints();
////		JButton pause = new JButton("Pause");
////		c.gridx = 1;
////		c.gridy = 1;
////		panel.add(pause, c);
////		
////		c = new GridBagConstraints();
////		JButton stop = new JButton("Stop");
////		c.gridx = 2;
////		c.gridy = 1;
////		panel.add(stop, c);
//		
		c = new GridBagConstraints();
		_videoWindow = new JLabel();
		c.fill = GridBagConstraints.HORIZONTAL;

		topPanel.add(_videoWindow, c);
		
		
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
