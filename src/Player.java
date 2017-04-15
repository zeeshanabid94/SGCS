import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

public class Player {
	
	Video _video;
	JFrame _mainWindow;
	JLabel _videoWindow;
	boolean _play;
	
	
	public Player() {
		_play = false;
		_mainWindow = new JFrame("Video Player");
		_mainWindow.setSize(960, 800);
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		JButton play = new JButton("Play");
		c.gridx = 0;
		c.gridy = 1;
		panel.add(play, c);
		
		c = new GridBagConstraints();
		JButton pause = new JButton("Pause");
		c.gridx = 1;
		c.gridy = 1;
		panel.add(pause, c);
		
		c = new GridBagConstraints();
		JButton stop = new JButton("Stop");
		c.gridx = 2;
		c.gridy = 1;
		panel.add(stop, c);
		
		c = new GridBagConstraints();
		_videoWindow = new JLabel("Video");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(_videoWindow, c);
		
		
		_mainWindow.setContentPane(panel);
		_mainWindow.setVisible(true);
	}
	
	public void setVideo(Video video) {
		_video = video;
	}
	
	public void Play() throws Exception {
		if (_video == null) {
			throw new Exception("No video found");
		}
		String fileoutpath = "C:/Users/zabid/Desktop/compressed.cmp";
		Encoder encoded = new Encoder(_video, fileoutpath);
		encoded.WriteOutputFile();
		
//		BGS BFSeparation = new BGS(_video);
//		BFSeparation.CalculateMotionVectors();
//		_play = true;
//		while(_play == true) {
//			Frame newFrame = _video.getNextFrame();
//			TimeUnit.MILLISECONDS.sleep(33);
//			_videoWindow.setIcon(new ImageIcon(newFrame.constructImageFromMacroblocks()));
//		}
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
		while(true);
	}

}
