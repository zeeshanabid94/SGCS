import java.lang.*;
import java.util.*;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;


/**
 * <h3><b>DCT - A Java implementation of the Discreet Cosine Transform</b></h3><br><br>
 * <hr>
 * The discreet cosine transform converts spatial information to "frequency" or
 * spectral information, with the X and Y axes representing frequencies of the
 * signal in different dimensions. This allows for "lossy" compression of image
 * data by determining which information can be thrown away without compromising
 * the image.<br><br>
 * The DCT is used in many compression and transmission codecs, such as JPEG, MPEG
 * and others. The pixels when transformed are arraged from the most signifigant pixel
 * to the least signifigant pixel. The DCT functions themselves are lossless.
 * Pixel loss occurs when the least signifigant pixels are quantitized to 0.
 * <br><br>
 * This is NOT a JPEG or JFIF compliant implementation however it could
 * be with very little extra work. (i.e. A huffman encoding stage needs
 * to be added.) I am making this source availible in the hopes that
 * someone will add this functionality to the class, if you do, please
 * email me! As always, if you have any problems feel free to contact
 * me. (Or comments, or praise, etc..)
 * <br><br>
 * <b>Keep in mind</b> that when compressing color images with this, you will
 * need to break the image up into it's R G B components and preform
 * the calculations three times!!
 * <br><br>
 * <b>A general algorithim for DCT compression with this class:</b> <br><br>
 * 1) Create a DCT Object.<br>
 * 2) Set up your program to read pixel information in 8*8 blocks. See example.<br>
 * 3) Run the forwardDCT() on all blocks.<br>
 * 4) Run the quantitizeImage() on all blocks.<br>
 * 5) If you want, send the information to the imageCompressor().<br><br>
 * <b>A general algorithim for DCT decompression with this class:</b> <br><br>
 * 1) Create a DCT Object. <br>
 * 2) Set up the program to convert compressed data in 8*8 blocks. (if compressed)<br>
 * 3) Run the data through dequantitizeImage(). <br>
 * 4) Run the data through inverseDCT().<br>
 * <br><br>
 * A complete implementation of an image compressor which compares
 * the quality of the two images is also availible. See the
 * JEncode/Decode <a href="JEncode.java">source code.</a> The
 * <a href="DCT.java">DCT.java source code</a> is also availible.
 * The implementation is also handy for seeing how to break the image
 * down, read in 8x8 blocks, and reconstruct it. The <a href="steve.jpg">
 * sample graphic</a> is handy to have too. (Bad pic of me)
 * The best way to get ahold of me is through my
 * <a href="http://eagle.uccb.ns.ca/steve/home.html">homepage</a>. There's
 * lots of goodies there too.
 * @version 1.0.1 August 22nd 1996
 * @author <a href="http://eagle.uccb.ns.ca/steve/home.html">Stephen Manley</a> - smanley@eagle.uccb.ns.ca
 */

// <pre> 
public class DCT
{
    /**
     * DCT Block Size - default 8
     */
    public int N        = 8;

    /**
     * Image Quality (0-25) - default 25 (worst image / best compression)
     */
    public int QUALITY  = 25;

    /**
     * Image width - must correspond to imageArray bounds - default 320
     */
    public int ROWS     = 320;

    /**
     * Image height - must correspond to imageArray bounds - default 200
     */
    public int COLS     = 240;

    /**
     * Cosine matrix. N * N.
     */
    public double c[][]        = new double[N][N];

    /**
     * Transformed cosine matrix, N*N.
     */
    public double cT[][]       = new double[N][N];

    /**
     * Quantitization Matrix.
     */
    public int quantum[][]     = new int[N][N];

    /**
     * DCT Result Matrix
     */
    public int resultDCT[][] = new int[ROWS][COLS];

    /**
     * Constructs a new DCT object. Initializes the cosine transform matrix
     * these are used when computing the DCT and it's inverse. This also
     * initializes the run length counters and the ZigZag sequence. Note that
     * the image quality can be worse than 25 however the image will be
     * extemely pixelated, usually to a block size of N.
     *
     * @param QUALITY The quality of the image (0 best - 25 worst)
     *
     */
    public DCT(int QUALITY)
    {
   
        initMatrix(QUALITY);
    }


    /**
     * This method sets up the quantization matrix using the Quality parameter
     * and then sets up the Cosine Transform Matrix and the Transposed CT.
     * These are used by the forward and inverse DCT. The RLE encoding
     * variables are set up to track the number of consecutive zero values
     * that have output or will be input.
     * @param quality The quality scaling factor
     */
    private void initMatrix(int quality)
    {
        int i;
        int j;

        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
            {
                quantum[i][j] = (1 + ((1 + i + j) * quality));
            }
        }

        for (j = 0; j < N; j++)
        {
            double nn = (double)(N);
            c[0][j]  = 1.0 / Math.sqrt(nn);
            cT[j][0] = c[0][j];
        }

        for (i = 1; i < 8; i++)
        {
            for (j = 0; j < 8; j++)
            {
                double jj = (double)j;
                double ii = (double)i;
                c[i][j]  = Math.sqrt(2.0/8.0) * Math.cos(((2.0 * jj + 1.0) * ii * Math.PI) / (2.0 * 8.0));
                cT[j][i] = c[i][j];
            }
        }
    }

    /**
     * This method preforms a matrix multiplication of the input pixel data matrix
     * by the transposed cosine matrix and store the result in a temporary
     * N * N matrix. This N * N matrix is then multiplied by the cosine matrix
     * and the result is stored in the output matrix.
     *
     * @param input The Input Pixel Matrix
     * @returns output The DCT Result Matrix
     */
    //test with char and integers
    public int[][] forwardDCT(BufferedImage input, String color)
    {
        int output[][] = new int[N][N];
        double temp[][] = new double[N][N];
        double temp1;
        int i;
        int j;
        int k;
        int pxl;

        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
            {
                temp[i][j] = 0.0;
                for (k = 0; k < N; k++)
                {
                	Color currentRGB = new Color(input.getRGB(i, k));
                	if (color.equals("red")) {
                		pxl = currentRGB.getRed();
                	} else if(color.equals("green")) {
                		pxl = currentRGB.getGreen();
                	} else {
                		pxl = currentRGB.getBlue();
                	}
                	
                    temp[i][j] += (((int)(pxl - 128)) * cT[k][j]);
                }
            }
        }

        for (i = 0; i < N; i++)
        {
            for (j = 0; j < N; j++)
            {
                temp1 = 0.0;

                for (k = 0; k < N; k++)
                {
                    temp1 += (c[i][k] * temp[k][j]);
                }

                output[i][j] = (int)Math.round(temp1);
            }
        }

        return output;
    }


    /**
     * This method is preformed using the reverse of the operations preformed in
     * the DCT. This restores a N * N input block to the corresponding output
     * block with values scaled to 0 to 255 and then stored in the input block
     * of pixels.
     *
     * @param input N * N input block
     * @returns output The pixel array output
     */
    public int[][] inverseDCT(int input[][])
    {
        int output[][] = new int[N][N];
        double temp[][] = new double[N][N];
        double temp1;
        int i;
        int j;
        int k;

        for (i=0; i<N; i++)
        {
            for (j=0; j<N; j++)
            {
                temp[i][j] = 0.0;

                for (k=0; k<N; k++)
                {
                    temp[i][j] += input[i][k] * c[k][j];
                }
            }
        }

        for (i=0; i<N; i++)
        {
            for (j=0; j<N; j++)
            {
                temp1 = 0.0;

                for (k=0; k<N; k++)
                {
                    temp1 += cT[i][k] * temp[k][j];
                }

                temp1 += 128.0;

                if (temp1 < 0)
                {
                    output[i][j] = 0;
                }
                else if (temp1 > 255)
                {
                    output[i][j] = 255;
                }
                else
                {
                     output[i][j] = (int)Math.round(temp1);
                }
            }
        }

        return output;
    }

    /**
     * This method uses bit shifting to convert an array of two bytes
     * to a integer (16 bits max). Byte 0 is the most signifigant byte
     * and Byte 1 is the least signifigant byte.
     * @param bitSet Two bytes to convert
     * @returns The constructed integer
     */
    private int bytetoInt(byte bitSet[])
    {
        int returnInt = 0;

        byte MSB = bitSet[0];
        byte LSB = bitSet[1];

        returnInt = ((MSB+128) << 8) | (LSB+128);

        return returnInt;
    }

    /**
     * This method uses bit shifting to convert an integer to an array
     * of two bytes, byte 0, the most signifigant and byte 1, the least
     * signifigant.
     * @param count The integer to convert. (16 bit max)
     * @returns The array of two bytes.
     */
    private byte[] inttoByte(int count)
    {
        int LSB = 0;
        int MSB = 0;
        byte bitSet[] = new byte[2];

        if (!(count > 65535))
        {
  	        LSB = ((count & 0x000000ff));
            MSB = ((count & 0x0000ff00) >> 8);
        }
        else
        {
            System.out.println("Integer > than 16 bit. Exiting..");
            System.exit(count);
        }

        bitSet[0] = (byte)(MSB-128);
        bitSet[1] = (byte)(LSB-128);

        return bitSet;
    }
    public static void main(String[] args) {
    	DCT test = new DCT(0);
    }
}

