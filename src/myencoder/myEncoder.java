package myencoder;

import Encoder.*;
import Player.Player;

public class myEncoder {
	public static void main(String args[]) throws Exception {
//	Player player = new Player();
	if(args.length < 2) {
		long tNow = System.currentTimeMillis();
		String file = args[0];
		int width, height;
		width = 960;
		height = 540;
		Video video = new Video(file, width, height);
//		player.setVideo(video);
		String fileoutpath = "/Users/shane/Desktop/finaltest_video2.cmp";
		Encoder encoded = new Encoder(video, fileoutpath);
		encoded.WriteOutputFile(); 
		long tLater = System.currentTimeMillis();
		System.out.println("The program took "+ (tLater - tNow)/1000 + " secs");
		}
		
//		player.Play();
	}
}
