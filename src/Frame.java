  
public class Frame {
	  int[] imageBytes;
	  public Frame() {
		  imageBytes = null;
	  }
	  public Frame(int width, int height) {
		  imageBytes = new int[width*height*3];
	  }
  }