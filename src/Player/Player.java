package Player;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import Encoder.Encoder;
import Encoder.Frame;
import Encoder.Vector2D;
import Encoder.Video;

public class Player {
	
	Video _video;
	JFrame _mainWindow;
	JLabel _videoWindow;
	boolean _play;
	Vector2D _mouseXY;
	
	
	public Player() {
		_mouseXY = new Vector2D(400,200);
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
		
		_videoWindow.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				_mouseXY.setX(e.getX());
				_mouseXY.setY(e.getY());
				System.out.println(e.getX() + " , " + e.getY());
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		_mainWindow.setContentPane(panel);
		_mainWindow.setVisible(true);
	}
	
	public int getMouseX() {
		return (int)_mouseXY.getX();
	}
	
	public int getMouseY() {
		return (int)_mouseXY.getY();
	}
	
	public void setVideo(Video video) {
		_video = video;
	}
	
	public void viewFrame(BufferedImage Frame) {
		_videoWindow.setIcon(new ImageIcon(Frame));
	}
	public void Play() throws Exception {
		if (_video == null) {
			throw new Exception("No video found");
		}
		String fileoutpath = "E:/file_markafterblocks_&afterFrame_withHeader.cmp";
		Encoder encoded = new Encoder(_video, fileoutpath);
		encoded.WriteOutputFile();
////		
//		Decoder decoded = new Decoder(fileoutpath);
//		decoded.DecodeFrames();
//		_video = decoded.getVideo();
////		BGS BFSeparation = new BGS(_video);
////		BFSeparation.CalculateMotionVectors();
//
////		String fileoutpath = "C:/Users/zabid/Desktop/compressed.cmp";
////		Encoder encoded = new Encoder(_video, fileoutpath);
////		encoded.WriteOutputFile();
//		
////		BGS BFSeparation = new BGS(_video);
////		BFSeparation.CalculateMotionVectors();
//		_play = true;
//		while(_play == true) {
//			Frame newFrame = _video.getNextFrame();
////			TimeUnit.MILLISECONDS.sleep(33);
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
