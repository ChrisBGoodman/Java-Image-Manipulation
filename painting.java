import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBuffer;
import java.awt.image.Kernel;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class painting {
	
	
	private BufferedImage image;
	private BufferedImage originalImage;
	private JPanel imagePanel;
	private JLabel imageLabel;
	private painting newImage;
	

	// -- Vars for editing the image --
	private Color color;
	private int red;
	private int blue;
	private int green;
	private int alpha;
	private int rgb;
	private int imageWidth;
	private int imageHeight;
	private boolean heatMapped;
	private int randomSwapper;


	// -- Constructor, Grabs original image to be painted -- 
	painting()
	{
		
		//Hardcoded image URL I plan to make into uploadable image 
		try {
			URL url = new URL("http://cdn.skim.gs/images/e765fc8d931f3d736f0b/meet-the-pug-pug-6");
			image = ImageIO.read(url);			
		} catch (IOException e) {
			e.printStackTrace();}
		
		originalImage = image;
		imageWidth = image.getWidth();
		imageHeight = image.getHeight();
				
		System.out.println(image);
		drawOriginal();
		heatMapped = true;
		randomSwapper = 0;
		
	}
	
	public JPanel getImagePanel() {
		return imagePanel;
	}
	
	public BufferedImage getOriginalImage(){
		return originalImage;
	}

	public BufferedImage getImage(){
		return image;
	}
	
	public void setImage(BufferedImage i){
		image = i;
	}
	
	// -- Called first to draw the original image --
	public void drawOriginal()
	{
		imagePanel = new JPanel() {
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        Graphics2D g2d = (Graphics2D) g;
				g2d.drawImage(originalImage, 0, 0, originalImage.getWidth()-1, originalImage.getHeight()-1 ,null);
				repaint();
		    }};
		   
		    System.out.println("Drew orginial");
	}
	
//------------------------  Manipulations Below  ------------------------------------
	// -- The logic for most of the functions is very similar,
	// -- grab data from the image such as the RGB and manipulate it in various ways 
	// -- The first function will be heavily commented to explain each line so that 
	// -- the following functions may be better understood using the same logic.
	
	
	
	// -- 'Normalizes' the colors so that the image is drawn in grayscale --
	public void drawGrayscale(int value, BufferedImage image)
	{
		for (int i = 0; i < image.getWidth(); i++) { 				// loop each pixel horizontally 
	        for (int j = 0; j < image.getHeight(); j++) {			// loop each pixel vertically 
	 
	        	int rgb = image.getRGB(i, j);						// get the RGB of a given x/y coordinate of the image
	            
	        	// To better understand that this is doing look up what OxFF does
	        	red = (rgb >> 16) & 0xFF;							//grab the red part of the RGB data
	            green = (rgb >> 8) & 0xFF;							//grab the green 
	            blue = (rgb & 0xFF);								//grab the blue
	        	
	            
	            // -- Divide the sum of the RGB by 3 to 'normalize' them 
	            // -- value var  is the amount of gray applied passed in thru the slider
	            int grayLevel = (red + green + blue) / value; 
	            
	            //Set a new int with the 'normalized' RGB values 
	            int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
	        
	            //Change the original RGB of a pixel to the new RGB created, repeat
	            image.setRGB(i, j, gray);
	        }
		}
	}
	
	// -- Does the same thing as grayscale above but instead of / by the value it multiplies for interesting results
	public void drawGrayscaleInverted(int value, BufferedImage image)
	{
		// -- loop each pixel and modify the RGB --
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {	
	 
	        	int rgb = image.getRGB(i, j);
	            red = (rgb >> 16) & 0xFF;
	            green = (rgb >> 8) & 0xFF;
	            blue = (rgb & 0xFF);
	        	
	            int grayLevel = (red + green + blue) * value;
	            int gray = (grayLevel << 16) + (grayLevel << 8) + grayLevel;
	        
	            image.setRGB(i, j, gray);
	            setImage(image);
	        }
		}
	}
	
	// -- Modifies only the R of the RGB by a value from the slider.
	public void drawRedShifted(int value, BufferedImage image)
	{
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {	
	        	color = new Color(image.getRGB(i, j));
	        	red = color.getRed();
	        	
	        	if (red + value < 255)
	        		red = red + value - 1;
	        	
	        	
	        	
	        	Color newColor = new Color(red, color.getGreen(), color.getBlue(), color.getAlpha());
	        	rgb = newColor.getRGB();
	            image.setRGB(i, j, rgb);
	        }
		}
	}
	
	// -- Modifies only the G of the RGB by a value from the slider.
	public void drawGreenShifted(int value, BufferedImage image)
	{
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {	
	        	color = new Color(image.getRGB(i, j));
	        	green = color.getGreen();
	        	
	        	if (green + value < 255)
	        		green = green + value - 1;
	        	
	        	Color newColor = new Color(color.getRed(), green, color.getBlue(), color.getAlpha());
	        	rgb = newColor.getRGB();
	            image.setRGB(i, j, rgb);
	        }
		}
	}
	
	// -- Modifies only the B of the RGB by a value from the slider.
	public void drawBlueShifted(int value, BufferedImage image)
	{
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {	
	        	color = new Color(image.getRGB(i, j));
	        	blue = color.getBlue();
	        	
	        	if (blue + value < 255)
	        		blue = blue + value - 1;
	        	
	        	Color newColor = new Color(color.getRed(), color.getGreen(), blue, color.getAlpha());
	        	rgb = newColor.getRGB();
	            image.setRGB(i, j, rgb);
	        }
		}
	}

	
	// -- draws a psuedo heatmap of color by summing the RGB and if greater then 150, set the color to white
	// -- if less then 150 set the color to black 
	public void drawHeatMap(BufferedImage image)
	{
		for (int i = 0; i < image.getWidth(); i++) {

	        for (int j = 0; j < image.getHeight(); j++) {	
	        	color = new Color(image.getRGB(i, j));
	        	red = color.getRed();
	        	green = color.getGreen();
	        	blue = color.getBlue();

	        	
	        	if (red+green+blue > 150)
	        	{
	        		if (heatMapped == true)
	        		{
	        			red = 255;
	        			blue = 255;
	        			green = 255;
	        		}
	        		
	        		if (heatMapped == false)
	        		{
	        			red = 0;
	        			blue = 0;
	        			green = 0;
	        		}
	        	}
	        	else
	        	{
	        		if(heatMapped == true)
	        		{
	        			red = 0;
	        			blue = 0;
	        			green = 0;
	        		}
	        		
	        		if (heatMapped == false)
	        		{
	        			red = 255;
	        			blue = 255;
	        			green = 255;
	        		}
	        		
	        		
	        	}
	        	
	        	Color newColor = new Color(red, green, blue, color.getAlpha());
	        	rgb = newColor.getRGB();
	            image.setRGB(i, j, rgb);
	        }
		}
		heatMapped = !heatMapped;
	}

	//-- Uses the same concept at the normal heatmap, but by inversing the map color (white/black)
	//-- At each 'hot' (RGB > 150) found
	public void drawHeatMap2(BufferedImage image)
	{
		for (int i = 0; i < image.getWidth(); i++) {

	        for (int j = 0; j < image.getHeight(); j++) {	
	        	color = new Color(image.getRGB(i, j));
	        	red = color.getRed();
	        	green = color.getGreen();
	        	blue = color.getBlue();

	        	//-- "Hot Points"
	        	if (red+green+blue > 150)
	        	{
	        		heatMapped = !heatMapped;

	        		if (heatMapped == true)
	        		{
	        			red = 255;
	        			blue = 255;
	        			green = 255;
	        		}
	        		
	        		if (heatMapped == false)
	        		{
	        			red = 0;
	        			blue = 0;
	        			green = 0;
	        		}
	        	}
	        	else
	        	{
	        		if(heatMapped == true)
	        		{
	        			red = 0;
	        			blue = 0;
	        			green = 0;
	        		}
	        		
	        		if (heatMapped == false)
	        		{
	        			red = 255;
	        			blue = 255;
	        			green = 255;
	        		}
	        		
	        		
	        	}
	        	
	        	Color newColor = new Color(red, green, blue, color.getAlpha());
	        	rgb = newColor.getRGB();
	            image.setRGB(i, j, rgb);
	        }
		}
	}
	
	//-- Uses the same concept at the normal heatmap, but by inversing the map color (white/black)
	//-- for each column so that edges standout against each other
	public void drawHeatMapOutline(BufferedImage image)
	{
		for (int i = 0; i < image.getWidth(); i++) {
    		heatMapped = !heatMapped;
	        for (int j = 0; j < image.getHeight(); j++) {	
	        	color = new Color(image.getRGB(i, j));
	        	red = color.getRed();
	        	green = color.getGreen();
	        	blue = color.getBlue();

	        	
	        	if (red+green+blue > 150)
	        	{

	        		if (heatMapped == true)
	        		{
	        			red = 255;
	        			blue = 255;
	        			green = 255;
	        		}
	        		
	        		if (heatMapped == false)
	        		{
	        			red = 0;
	        			blue = 0;
	        			green = 0;
	        		}
	        	}
	        	else
	        	{
	        		if(heatMapped == true)
	        		{
	        			red = 0;
	        			blue = 0;
	        			green = 0;
	        		}
	        		
	        		if (heatMapped == false)
	        		{
	        			red = 255;
	        			blue = 255;
	        			green = 255;
	        		}
	        		
	        		
	        	}
	        	
	        	Color newColor = new Color(red, green, blue, color.getAlpha());
	        	rgb = newColor.getRGB();
	            image.setRGB(i, j, rgb);
	        }
		}
	}
	
	//-- loops each pixel and assigns it a random Red, green, blue in between 0-255 --
	public void drawRandomMap(BufferedImage image)
	{
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {	
	    		Random rand = new Random();
	        	color = new Color(image.getRGB(i, j));

	        	red = rand.nextInt((255 - 0) + 1) + 0;
	        	blue = rand.nextInt((255 - 0) + 1) + 0;
	        	green = rand.nextInt((255 - 0) + 1) + 0;
	        		        	
	        	Color newColor = new Color(red, green, blue, color.getAlpha());
	        	rgb = newColor.getRGB();
	            image.setRGB(i, j, rgb);
	        }
		}
	}

	//-- loops each pixel and assigns it a random Red and blue in between 0-255 --
		public void drawRandomMapSwap(BufferedImage image)
		{
			Color newColor = new Color(red,green,blue);
			
			for (int i = 0; i < image.getWidth(); i++) {
		        for (int j = 0; j < image.getHeight(); j++) {	
		    		Random rand = new Random();
		        	color = new Color(image.getRGB(i, j));

		       		red = rand.nextInt((255 - 0) + 1) + 0;		   
		       		blue = rand.nextInt((255 - 0) + 1) + 0;
		       		green = rand.nextInt((255 - 0) + 1) + 0;
		       		
		       		if (randomSwapper == 0)
		       			newColor = new Color(red, color.getGreen(), blue, color.getAlpha());
		       		if (randomSwapper == 1)
		       			newColor = new Color(color.getRed(), green, blue, color.getAlpha());
		       		if (randomSwapper == 2)
		       			newColor = new Color(red, green, color.getBlue(), color.getAlpha());

		        	rgb = newColor.getRGB();
		            image.setRGB(i, j, rgb);
		        }
			}
			randomSwapper = randomSwapper + 1;
			if(randomSwapper == 3)
				randomSwapper = 0;
			
		}
	
	//-- Mirrors the image horizontally -- 
	public void drawHorizontalReflect(BufferedImage image)
	{
		int w = image.getWidth() -1;
		int h = image.getHeight() -1;
		
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {	
	        	color = new Color(image.getRGB(i, j));

	        	red = color.getRed();
	        	green = color.getGreen();
	        	blue = color.getBlue();
	        
	        	Color newColor = new Color(red, green, blue, color.getAlpha());
	        	rgb = newColor.getRGB();
	            image.setRGB(w-i, j, rgb); //Mirroring done here
	            image.setRGB(i, j, rgb);
	        }
		}
	}
	
	//-- Mirrors the image vertically --
	public void drawVerticalReflect(BufferedImage image)
	{
		int w = image.getWidth() -1;
		int h = image.getHeight() -1;
		
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {	
	        	color = new Color(image.getRGB(i, j));

	        	red = color.getRed();
	        	green = color.getGreen();
	        	blue = color.getBlue();
	        
	        	Color newColor = new Color(red, green, blue, color.getAlpha());
	        	rgb = newColor.getRGB();
	            image.setRGB(i, h-j, rgb); //Mirroring done here 
	            image.setRGB(i, j, rgb);
	                 
	        }
		}
	}
	
	//Swaps the RGB values so that
	//red = blue, green = red, blue = green
	public void drawColorSwap(BufferedImage image)
	{
		int w = image.getWidth() -1;
		int h = image.getHeight() -1;
		
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {	
	        	color = new Color(image.getRGB(i, j));

	        	red = color.getRed();
	        	green = color.getGreen();
	        	blue = color.getBlue();
	        

	        	Color newColor = new Color(blue, red, green, color.getAlpha());
	        	rgb = newColor.getRGB();
	            image.setRGB(i, j, rgb);
	            
	        }
		}
	}
	
	// -- Swaps each pixel so that 
	// red = blue, green = red, blue = red
	public void drawColorSwap2(BufferedImage image)
	{
		// -- loop each pixel and modify the RGB --
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {	
	        	color = new Color(image.getRGB(i, j));

	        	blue = color.getRed();
	        	red = color.getGreen();
	        	green = color.getRed();
	        

	        	Color newColor = new Color(red, green, blue, color.getAlpha());
	        	rgb = newColor.getRGB();
	            image.setRGB(i, j, rgb);
	        }
		}
	}
	
	//-- To be honest I can't explain exactly what this function is doing to the image, but I'm 
	//-- manipulating the bitmap of the image ever so slightly to create new effects
	public void drawRasterManip(BufferedImage image)
	{
		int x = 0;
		Raster raster = image.getRaster();
    	int[] data = raster.getPixels(0, 0, raster.getWidth(), raster.getHeight(), (int[]) null);
    	
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {	
	        	image.setRGB(i, j, new Color(data[i],data[x],data[j],data[i]).getRGB());
	        }
	        x++;
		}
	}
	
	//Same logic as the above function except Im changing just another small piece to get a different effect
	public void drawRasterManip2(BufferedImage image)
	{
		Raster raster = image.getRaster();
    	int[] data = raster.getPixels(0, 0, raster.getWidth(), raster.getHeight(), (int[]) null);
    	
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {
	        	
	        	image.setRGB(i, j, new Color(data[j],data[i],data[i],data[i]).getRGB());
	        }
		}
	}
	
	public void drawNew(BufferedImage image)
	{
		System.out.println("drew heatmap");
		for (int i = 0; i < image.getWidth(); i++) {
	        for (int j = 0; j < image.getHeight(); j++) {	
	    		Random rand = new Random();
	        	color = new Color(image.getRGB(i, j));

	        	red = rand.nextInt((255 - 0) + 1) + 0;
	        	blue = rand.nextInt((255 - 0) + 1) + 0;
	        	green = rand.nextInt((255 - 0) + 1) + 0;
	        		        	
	        	Color newColor = new Color(red, green, blue, color.getAlpha());
	        	rgb = newColor.getRGB();
	            image.setRGB(i, j, rgb);
	            
	            j = j+10;
	        }
            i=i+5;

		}
	}

	
	//-- Supposed to reset/redraw the original image, but not currently being used --
		public void drawReset()
		{
			for (int i = 0; i < originalImage.getWidth(); i++) {
		        for (int j = 0; j < originalImage.getHeight(); j++) {	
		 
		        	int rgb = originalImage.getRGB(i, j);
		            image.setRGB(i, j, rgb);
		        }
			}
		}
}		
  
