
public class BGS {

	public class CodeWord {
		Vector3D<Byte> _RGB;
		int[] _aux;

		public CodeWord(byte R, byte G, byte B, int minB, int maxB, int f, int nrl, int first, int last) {
			_RGB = new Vector3D<Byte>(R,G,B);
			_aux = new int[6];
			_aux[0] = minB;
			_aux[1] = maxB;
			_aux[2] = f;
			_aux[3] = nrl;
			_aux[4] = first;
			_aux[5] = last;
		}
	}

	//codebook for each pixel
	//each code word consists of vector RGB and a 6 tuple aux (min brightness, max brightness, frequency of codeword, maximum negative run length, first access time, last access time)
	CodeWord[][] _codeBook;
	Video _video;

	//code book construction:
		// initially size of code book is 0 and code book is empty
		//	for t = 1 to some N
		//		xt = (R,G,B)	intensity I = sqrt(R^2+G^2+B^2)
		//		find codeword in code book where
		//		colordist(xt, vm) < some value --> detection threshold value
		//		brightness(I, (Imin, Imax)) == true
		//		if code book is empty or no match found
		//			L = L + 1
		//			vl = xt or RGB of pixel
		//			auxl = (I, I, 1, t-1,t,t)
		//		otherwise update the codeword cm
		//			cm.vector RGB = (fm * Rm + R)/(fm + 1) ---> Similarly for G and B
		//			auxm = (min(I, Imin), max(I, Imax),fm + 1, max(lambdaM,t-qm), pm, t)
		// end for
		// For each codeword ci; i 1⁄4 1;...;L; wrap around li by setting li max(li, (N-qi + pi 􏰁-1)


	//colordist(pixel, codeword i)
	//	xt2 = R^2 + G^2 + B^2
	//	vi2 = Ri^2 + Gi^2 + Bi^2
	// (xt, vi)2 = (Ri*R + Gi*G + Bi*B) ^ 2
	// p2 = (xt, vi)2 / vi2
	//	return sqrt(xt2 - p2)

	public double ColorDist(Vector3D pixel, CodeWord i) {
		int xt2 = Math.pow(pixel.getX(), 2) + Math.pow(pixel.getY(), 2) + Math.pow(pixel.getZ(), 2);
		int vi2 = Math.pow(i.getRGB().getX(), 2) + Math.pow(i.getRGB().getY(), 2) + Math.pow(i.getRGB().getZ(), 2);
		int xtvi2 = Math.pow(i.getRGB().getX() * pixel.getX() + i.getRGB().getY() * pixel.getY() + i.getRGB().getZ() * pixel.getZ());
		int p2 = xtvi2/ vi2;
		return Math.sqrt(xt2 - p2);
	}

	//brightness (I, (Imin, Imax))
	//	Ilow = range(0.4 - 0.7) * I max
	// 	Ihi = min(range(1.1 - 1.5) * Imax, Ilow / range(0.4 - 0.7))
	// if ilow <= I <= ihigh
		return true
		else
		return false
}